package com.ihuanglele.book.page;

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
    }

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public HashSet<Link> getLinks() {
        return links;
    }

    public void setLinks(HashSet<Link> links) {
        this.links = links;
    }

    private HashSet<Link> links;
}
