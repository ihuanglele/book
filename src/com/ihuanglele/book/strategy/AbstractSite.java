package com.ihuanglele.book.strategy;

import com.ihuanglele.book.exception.PageErrorException;
import com.ihuanglele.book.exception.StopException;
import com.ihuanglele.book.page.Article;
import com.ihuanglele.book.page.Book;
import com.ihuanglele.book.page.Chapter;
import com.ihuanglele.book.store.IStore;
import com.ihuanglele.book.util.GetHtmlPage;
import okhttp3.Response;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by ihuanglele on 2018/11/8.
 */
public abstract class AbstractSite {

    private Book book;

    protected Boolean isMobile = false;

    public IStore getStore() {
        return store;
    }

    public void setStore(IStore store) {
        this.store = store;
    }

    private IStore store;

    /**
     * 获取下一个章节地址
     * @return 下一个章节地址
     */
    protected String getNextPageUrl() {
        Integer id = Integer.valueOf(book.getId()) + 1;
        return getPageUrl(String.valueOf(id));
    }

    /**
     * 抓取入口
     * @param bookId 起始ID
     * @throws StopException
     */
    public void start(String bookId) throws StopException {
        GetHtmlPage page = new GetHtmlPage();
        Response response = page.setMobile(isMobile)
                .getPage(getPageUrl(bookId));
        this.book = getBookPage(response);
        book.setId(bookId);

        Chapter chapter = getChapterPage(page.getPage(getChapterUrl()));
        book.setChapter(chapter);

        ArrayList<Article> articles = new ArrayList<>();
        for (Chapter.Link link : chapter.getLinks()){
            Article article = getArticlePage(page.getPage(link.getHref()));
            if(null == article.getChapterNo()){
                article.setChapterNo(link.getChapterNo());
            }
            if(null == article.getTitle()){
                article.setTitle(link.getTitle());
            }
            articles.add(article);
        }
        book.setArticles(articles);
        store.save(book);
        if(!this.isStop()){
            start(this.getNextPageUrl());
        }
    }

    protected Book getBook(){
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
            return url + href;
        }
    }

}
