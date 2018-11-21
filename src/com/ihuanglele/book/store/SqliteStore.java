package com.ihuanglele.book.store;

import com.ihuanglele.book.entity.ArticleEntity;
import com.ihuanglele.book.entity.BookEntity;
import com.ihuanglele.book.page.Article;
import com.ihuanglele.book.page.Book;
import org.orman.dbms.Database;
import org.orman.dbms.sqlite.SQLite;
import org.orman.mapper.MappingConfiguration;
import org.orman.mapper.MappingSession;

/**
 * Created by ihuanglele on 2018/11/9.
 */
public class SqliteStore implements IStore {

    static {
        Database db = new SQLite("db/db.sqlite");
        MappingSession.registerDatabase(db);
        MappingConfiguration configuration = new MappingConfiguration();
        MappingSession.setConfiguration(configuration);
        MappingSession.registerEntity(BookEntity.class);
        MappingSession.registerEntity(ArticleEntity.class);
        MappingSession.start();
    }

    @Override
    public boolean save(Book book) {

        BookEntity bookEntity = ormBook(book);
        for (Article article : book.getArticles()){
            if(null != article){
                ArticleEntity articleEntity = new ArticleEntity();
                articleEntity.bookId = bookEntity.id;
                articleEntity.chapterNo = article.getChapterNo();
                articleEntity.title = article.getTitle();
                articleEntity.content = article.getContent();
                articleEntity.lastUpdateTime = article.getLastUpdateTime();
                articleEntity.url = article.getUrl();
                articleEntity.status = article.getStatus();
                articleEntity.insert();
            }
        }
        return true;
    }


    private BookEntity ormBook(Book book){
        BookEntity bookEntity = new BookEntity();
        bookEntity.title = book.getTitle();
        bookEntity.author = book.getAuthor();
        bookEntity.brief = book.getDesc();
        bookEntity.album = book.getAlbum();
        bookEntity.url = book.getUrl();
        bookEntity.chapterNum = String.valueOf(book.getChapter().getLinks().size());
        bookEntity.insert();
        return bookEntity;
    }

}
