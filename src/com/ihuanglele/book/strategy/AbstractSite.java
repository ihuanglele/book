package com.ihuanglele.book.strategy;

import com.ihuanglele.book.exception.StopException;
import com.ihuanglele.book.page.Article;
import com.ihuanglele.book.page.Book;
import com.ihuanglele.book.page.Chapter;
import com.ihuanglele.book.util.GetHtmlPage;
import okhttp3.Response;

/**
 * Created by ihuanglele on 2018/11/8.
 */
public abstract class AbstractSite {

    private Book book;

    protected Boolean isMobile = false;

    public String getNextPageUrl() {
        Integer id = Integer.valueOf(book.getId()) + 1;
        return getPageUrl(String.valueOf(id));
    }

    public void start(String bookId) throws StopException {
        GetHtmlPage page = new GetHtmlPage();
        Response response = page.setMobile(this.isMobile)
                .getPage(this.getPageUrl(bookId));
        book = getBookPage(response);

        Chapter chapter = getChapterPage(page.getPage(this.getChapterUrl()));
        book.setChapter(chapter);

        for (Chapter.Link link : chapter.getLinks()){
            String articleUrl = this.getArticleUrl(link.getHref());
            book.getArticles().add(this.getArticlePage(page.getPage(articleUrl)));
        }
        if(!this.isStop()){
            start(this.getNextPageUrl());
        }
    }

    public Book getBook(){
        return book;
    }

    protected abstract String getPageUrl(String id);

    protected abstract String getChapterUrl();

    protected abstract String getArticleUrl(String url);

    protected abstract Book getBookPage(Response response);

    protected abstract Chapter getChapterPage(Response response);

    protected abstract Article getArticlePage(Response response);

    protected abstract Boolean isStop();

}
