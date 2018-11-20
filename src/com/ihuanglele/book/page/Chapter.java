package com.ihuanglele.book.page;

import com.ihuanglele.book.util.Tool;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by ihuanglele on 2018/11/8.
 */
public class Chapter {

    public class Link {
        private String href;
        private String title;
        private String chapterNo;

        public Link(String title,String href, String chapterNo){
            this.title = title;
            this.href = href;
            this.chapterNo = chapterNo;
        }

        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getChapterNo() {
            return chapterNo;
        }

        public void setChapterNo(String chapterNo) {
            this.chapterNo = chapterNo;
        }

        public void string(){
            Tool.log("title: "+title+"   href: "+href + "    chapter_no: "+chapterNo);
        }
    }

    // 章节页面地址
    private String url;
    // 章节 链接 Set
    private ArrayList<Link> links;
    // 页面 Document 对象
    private Document document;

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ArrayList<Link> getLinks() {
        return links;
    }

    public void setLinks(ArrayList<Link> links) {
        this.links = links;
    }

    public void printLinks(){
        for (Link link : links){
            link.string();
        }
    }
}
