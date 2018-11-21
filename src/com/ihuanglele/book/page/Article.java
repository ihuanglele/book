package com.ihuanglele.book.page;

import org.jsoup.nodes.Document;

/**
 * Created by ihuanglele on 2018/11/8.
 */
public class Article {

    // 当前页面地址
    private String url;
    // 当前页面 Document 对象
    private Document document;
    // 文章标题
    private String title;
    // 文章内容
    private String content;
    // 最后更新时间
    private String lastUpdateTime;
    // 章节号
    private String chapterNo;
    // 状态
    private String status;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getChapterNo() {
        return chapterNo;
    }

    public void setChapterNo(String chapterNo) {
        this.chapterNo = chapterNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
