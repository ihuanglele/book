package com.ihuanglele.book.store;

import com.ihuanglele.book.page.Article;
import com.ihuanglele.book.page.Book;
import com.ihuanglele.book.util.Tool;

/**
 * Created by ihuanglele on 2018/11/8.
 */
public class FileStore implements IStore {

    public boolean save(Book book) {
        for (Article article : book.getArticles()){
            Tool.log(article.getChapterNo() + " : " + article.getTitle());
        }
        return false;
    }

}
