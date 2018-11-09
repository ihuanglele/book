package com.ihuanglele.book.store;

import com.ihuanglele.book.page.Book;

import java.sql.*;

/**
 * Created by ihuanglele on 2018/11/9.
 */
public class SqliteStore implements IStore {

    private final static String drivder = "org.sqlite.JDBC";

    private static Statement statement;

    static  {
        try {
            // 加载驱动,连接sqlite的jdbc
            Class.forName(drivder);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //连接数据库zhou.db,不存在则创建
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:db/book.db");
            //创建连接对象，是Java的一个操作数据库的重要接口
            SqliteStore.statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveBook(){

    }

    @Override
    public boolean save(Book book) {
        return false;
    }
}
