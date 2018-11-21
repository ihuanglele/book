package com.ihuanglele.book.entity;

import org.orman.mapper.Model;
import org.orman.mapper.annotation.Column;
import org.orman.mapper.annotation.Entity;
import org.orman.mapper.annotation.PrimaryKey;

/**
 * Created by ihuanglele on 2018/11/9.
 */
@Entity(table = "book")
public class BookEntity extends Model<BookEntity> {

    @PrimaryKey(autoIncrement = true)
    @Column(name = "id",type = "INTEGER")
    public Integer id;

    public String title;

    public String author;

    public String brief;

    public String album;

    public String url;

    @Column(name="chapter_num")
    public String chapterNum;
}
