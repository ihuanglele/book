package com.ihuanglele.book.entity;

import org.orman.mapper.Model;
import org.orman.mapper.annotation.Column;
import org.orman.mapper.annotation.Entity;
import org.orman.mapper.annotation.Index;
import org.orman.mapper.annotation.PrimaryKey;

/**
 * Created by ihuanglele on 2018/11/9.
 */
@Entity(table = "article")
public class ArticleEntity extends Model<ArticleEntity> {

    @PrimaryKey(autoIncrement = true)
    @Column(name = "id",type = "INTEGER")
    public Integer id;

    @Index(name = "book_id")
    @Column(name = "book_id", type = "INTEGER")
    public Integer bookId;

    public String title;

    @Column(name = "chapter_no")
    public String chapterNo;

    public String content;

    @Column(name = "last_update_time")
    public String lastUpdateTime;

    public String url;

    public String status;
}
