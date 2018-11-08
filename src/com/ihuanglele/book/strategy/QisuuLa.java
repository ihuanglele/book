package com.ihuanglele.book.strategy;

import com.ihuanglele.book.page.Article;
import com.ihuanglele.book.page.Book;
import com.ihuanglele.book.page.Chapter;
import okhttp3.Response;

/**
 * Created by ihuanglele on 2018/11/8.
 */
public class QisuuLa extends AbstractSite {

    private static String domain =  "https://www.qisuu.la/";

    protected String getPageUrl(String bookId) {
        return domain+"du/1/"+bookId;
    }

    protected String getChapterUrl() {
        return this.getPageUrl(getBook().getId());
    }

    protected String getArticleUrl(String chapterId) {
        return domain+"du/1/"+getBook().getId()+"/"+chapterId+".html";
    }

    protected Book getBookPage(Response response) {
        return null;
    }

    protected Chapter getChapterPage(Response response) {
        return null;
    }

    protected Article getArticlePage(Response response) {
        return null;
    }

    protected Boolean isStop() {
        return true;
    }


}
