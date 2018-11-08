package com.ihuanglele.book.strategy;

import com.ihuanglele.book.exception.PageErrorException;
import com.ihuanglele.book.page.Article;
import com.ihuanglele.book.page.Book;
import com.ihuanglele.book.page.Chapter;
import com.ihuanglele.book.util.Tool;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

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

    protected Book getBookPage(Response response) throws PageErrorException {
        if(!checkResponse(response)){
            throw new PageErrorException();
        }
        Book book = new Book();
        try {
            Document document = Jsoup.parse(response.body().string());
            book.setDocument(document);
            Element infoDesc = document.selectFirst(".info_des");
            book.setTitle(infoDesc.getElementsByTag("h1").first().text());
            book.setAuthor(infoDesc.getElementsByTag("dl").first().text());
            book.setDesc(infoDesc.selectFirst(".intro").text());
            book.setAlbum(document.selectFirst(".tupian img").attr("src"));
            return book;
        } catch (IOException e) {
            e.printStackTrace();
            throw new PageErrorException();
        }
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
