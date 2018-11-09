package com.ihuanglele.book.strategy;

import com.ihuanglele.book.exception.PageErrorException;
import com.ihuanglele.book.page.Article;
import com.ihuanglele.book.page.Book;
import com.ihuanglele.book.page.Chapter;
import com.ihuanglele.book.util.Tool;
import okhttp3.Response;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by ihuanglele on 2018/11/8.
 */
public class QisuuLa extends AbstractSite {

    private static String domain =  "https://www.qisuu.la/";

    protected String getPageUrl(String bookId) {
        return domain+"du/1/"+bookId;
    }

    protected String getChapterUrl() {
        return getPageUrl(getBook().getId());
    }

    protected String getArticleUrl(String chapterId) {
        return domain+"du/1/"+getBook().getId()+"/"+chapterId+".html";
    }

    protected Book getBookPage(Response response) throws PageErrorException {
        Book book = super.getBookPage(response);
        Element infoDesc = book.getDocument().selectFirst(".info_des");
        book.setTitle(infoDesc.getElementsByTag("h1").first().text());
        book.setAuthor(infoDesc.getElementsByTag("dl").first().text());
        book.setDesc(infoDesc.selectFirst(".intro").text());
        String imgUri = book.getDocument()
                .selectFirst(".tupian img").attr("src");
        book.setAlbum(joinUrl(imgUri,response.request().url().toString()));
        return book;
    }

    protected Chapter getChapterPage(Response response) throws PageErrorException {
        Chapter chapter = super.getChapterPage(response);
        Element ulElement = chapter.getDocument().select(".pc_list ul").last();
        ArrayList<Chapter.Link> links = new ArrayList<>();
        Integer i = 1;
        for (Element liA : ulElement.select("li>a")){
            String url = joinUrl(liA.attr("href"), chapter.getUrl());
            links.add(chapter.new Link(liA.text(),url,String.valueOf(i) ));
            i++;
        }
        chapter.setLinks(links);
        return  chapter;
    }

    protected Article getArticlePage(Response response) throws PageErrorException {
        Article article = super.getArticlePage(response);
        article.setContent(article.getDocument().select("#content1").html());
        return article;
    }

    protected Boolean isStop() {
        return true;
    }


}
