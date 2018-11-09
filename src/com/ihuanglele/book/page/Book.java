package com.ihuanglele.book.page;

import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Created by ihuanglele on 2018/11/8.
 */
public class Book {

    private String title;
    private String url;
    private String author;
    private String desc;
    private String album;

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    private Document document;

    protected String id;
    private Chapter chapter;
    private ArrayList<Article> articles;

    public Chapter getChapter() {
        return chapter;
    }


    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    public ArrayList<Article> getArticles() {
        return articles;
    }

    public void setArticles(ArrayList<Article> articles) {
        this.articles = articles;
    }

    public Map<String,String> string(){
        Map<String,String> map = new HashMap<String, String>();
        map.put("title",title);
        map.put("url",url);
        map.put("author",author);
        map.put("desc",desc);
        map.put("album",album);
        return map;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
