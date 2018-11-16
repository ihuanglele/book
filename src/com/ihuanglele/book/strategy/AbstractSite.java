package com.ihuanglele.book.strategy;

import com.ihuanglele.book.exception.PageErrorException;
import com.ihuanglele.book.exception.StopException;
import com.ihuanglele.book.page.Article;
import com.ihuanglele.book.page.Book;
import com.ihuanglele.book.page.Chapter;
import com.ihuanglele.book.util.GetHtmlPage;
import com.ihuanglele.book.util.Tool;
import okhttp3.Response;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

/**
 * Created by ihuanglele on 2018/11/8.
 */
@SuppressWarnings("ALL")
public abstract class AbstractSite {

    private final static String sitePackage = "com.ihuanglele.book.strategy";

    private Book book;
    protected String bookId;
    protected Boolean isMobile = false;

    protected static final ThreadPoolExecutor excutor = new ThreadPoolExecutor(100,Integer.MAX_VALUE,5, TimeUnit.SECONDS,new LinkedBlockingDeque<Runnable>());

    public void clean() {
        book = null;
    }

    /**
     * 获取下一本书的地址ID
     * @return 地址ID
     */
    public static String getNextPageId(String bookId) {
        Integer id = Integer.valueOf(bookId) + 1;
        return ""+id;
    }

    /**
     * 抓取入口
     * @param bookId 起始ID
     * @throws StopException
     */
    public static final AbstractSite start(String siteName,String bookId) throws PageErrorException,StopException {
        AbstractSite site;
        try {
            Class<?> c = Class.forName(sitePackage + "." + siteName);
            site = (AbstractSite)c.newInstance();
        } catch (ClassNotFoundException e) {
            throw new StopException(siteName + "ClassNotFoundException -> " + e.getMessage());
        } catch (IllegalAccessException e) {
            throw new StopException(siteName + "IllegalAccessException -> " + e.getMessage());
        } catch (InstantiationException e) {
            throw new StopException(siteName + "InstantiationException -> " + e.getMessage());
        }
        site.bookId = bookId;
        Tool.save("----开始执行"+bookId+"----","articleHref");
        if(!site.isStop()){
            throw new StopException("stop crawl");
        }
        GetHtmlPage page = new GetHtmlPage();
        Response response = page.setMobile(site.isMobile).getPage(site.getPageUrl(bookId));
        site.setBook(site.getBookPage(response));
        response.close();
        site.getBook().setId(bookId);

        Response chapterRes = page.getPage(site.getChapterUrl());
        Chapter chapter = site.getChapterPage(chapterRes);
        chapterRes.close();
        site.getBook().setChapter(chapter);

        Arts arts = new Arts();

        int linkSize = chapter.getLinks().size();
        Counter counter = new Counter(linkSize);

        for (Chapter.Link link : chapter.getLinks()){
            excutor.execute((new Thread() {
                @Override
                public synchronized void run() {
                    Article article = null;
                    String log = link.getTitle() + "   " + link.getHref();
                    Long t1 = (new Date()).getTime();
                    try {
                        Response articleRes = page.getPage(link.getHref());
                        article = site.getArticlePage(articleRes);
                        articleRes.close();
                        if (null == article.getChapterNo()) {
                            article.setChapterNo(link.getChapterNo());
                        }
                        if (null == article.getTitle()) {
                            article.setTitle(link.getTitle());
                        }
                        log += "  -> success";
                    } catch (PageErrorException e) {
                        log += "  -> fail:" + e.getMessage();
                    }finally {
                        Long t2 = (new Date()).getTime();
                        Tool.save((t2 - t1)/1000 + " " + log,"articleHref");
                        counter.add();
                        arts.setArt(article);
                    }
                }
            }));
        }

        while (counter.getSize() < linkSize) {
            try {
                sleep(3000);
                Tool.log(counter.string()+" : activeCount"
                        + excutor.getActiveCount()
                        + "\t queueSize" + excutor.getQueue().size());
            } catch (InterruptedException e) {
                e.printStackTrace();
                Tool.log("sleep Error" + e.getMessage());
            }
        }
        site.getBook().setArticles(arts.getArticles());
        return site;
    }

    public void setBook(Book book) {
        this.book = book;
    }
    public Book getBook(){
        return book;
    }

    /**
     * 获取文章的地址
     * @param id 章节ID
     * @return 章节URL
     */
    protected abstract String getPageUrl(String id);

    /**
     * 获取章节的地址
     * @return
     */
    protected abstract String getChapterUrl();

    /**
     * 获取当前的文章地址
     * @param url 文章的URL
     * @return 当前文章的地址
     */
    protected abstract String getArticleUrl(String url);

    protected Book getBookPage(Response response) throws PageErrorException{
        if(!checkResponse(response)){
            throw new PageErrorException("getBookPage: Response fail" + response.message());
        }
        try {
            Book book = new Book();
            book.setUrl(response.request().url().toString());
            book.setDocument(Jsoup.parse(response.body().string()));
            return book;
        } catch (IOException e) {
            e.printStackTrace();
            throw new PageErrorException("getBookPage: IOException" + e.getMessage());
        }
    }

    /**
     * 章节页面转换成 Chapter 对象
     * @param response Http 响应
     * @return
     * @throws PageErrorException
     */
    protected Chapter getChapterPage(Response response) throws PageErrorException{
        if(!checkResponse(response)){
            throw new PageErrorException("getChapterPage: Response fail");
        }
        try{
            Chapter chapter = new Chapter();
            chapter.setUrl(response.request().url().toString());
            chapter.setDocument(Jsoup.parse(response.body().string()));
            return chapter;
        }catch (IOException e){
            e.printStackTrace();
            throw new PageErrorException("getBookPage: IOException" + e.getMessage());
        }
    }

    /**
     * 文章页面转换成 Article 对象
     * @param response
     * @throws PageErrorException
     * @return
     */
    protected Article getArticlePage(Response response) throws PageErrorException {
        if(!checkResponse(response)){
            throw new PageErrorException("getArticlePage");
        }
        try{
            Article article = new Article();
            article.setUrl(response.request().url().toString());
            article.setDocument(Jsoup.parse(response.body().string()));
            return article;
        }catch (IOException e){
            e.printStackTrace();
            throw new PageErrorException("getArticlePage: IOException" + e.getMessage());
        }
    };

    /**
     * 是否需要停止抓取下一个 ID
     * @return
     */
    protected abstract Boolean isStop();

    /**
     * 检测 response 是否正常
     * @param response
     * @return
     */
    protected boolean checkResponse(Response response){
        return response.isSuccessful();
    }

    protected String joinUrl(String href,String url){
        if(href.startsWith("http")){
            return href;
        }else {
            if(href.startsWith("/")){
                try {
                    URL u = new URL(url);
                    return u.getProtocol()+"://"+u.getHost()+href;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    return href;
                }
            }else {
                return url + href;
            }
        }
    }

}

/**
 * 线程计数 方便统计线程状态
 */
class Counter{

    private Integer size = 0;

    private Integer current = 0;

    public Counter(Integer size) {
        this.size = size;
    }

    public void add(){
        current++;
    }

    public Integer getSize(){
        return size;
    }

    public Integer getCurrent(){
        return current;
    }

    public String string(){
        return size + "<-size  current->" + current;
    }

    public boolean isEqual(){
        return size.equals(current);
    }

}

class Arts{
    private ArrayList<Article> articles = new ArrayList<>();

    public void setArt(Article article){
        Tool.log(article.getTitle());
        articles.add(article);
    }

    public ArrayList<Article> getArticles(){
        return articles;
    }

}