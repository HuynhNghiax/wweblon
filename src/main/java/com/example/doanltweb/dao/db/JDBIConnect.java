package com.example.doanltweb.dao.db;

import com.example.doanltweb.dao.model.Product;
import com.example.doanltweb.dao.model.User;
import com.mysql.cj.jdbc.MysqlDataSource;
import org.jdbi.v3.core.Jdbi;

import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class JDBIConnect {
    static String url = "jdbc:mysql://" + DBProperties.host() + ":" + DBProperties.port() + "/" + DBProperties.dbname() + "?" + DBProperties.option();
    static Jdbi jdbi;

    public static Jdbi get() {
        if (jdbi == null) {
            makeConnect();
        }
        return jdbi;
    }

    private static void makeConnect() {
        MysqlDataSource src = new MysqlDataSource();
        src.setURL(url);
        src.setUser(DBProperties.username());
        src.setPassword(DBProperties.password());
        try {
            src.setUseCompression(true);
            src.setAutoReconnect(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        jdbi = Jdbi.create(src);
    }

    public static void main(String[] args) {
        JDBIConnect.get().withHandle(handle -> handle.createQuery("SELECT * FROM product").mapToBean(Product.class).list());
        List<Product> list = JDBIConnect.get().withHandle(handle -> handle.createQuery("SELECT * FROM product").mapToBean(Product.class).list());
        System.out.println(list);
        JDBIConnect.get().withHandle(handle -> handle.createQuery("SELECT * FROM user").mapToBean(User.class).list());
        List<User> list1 = JDBIConnect.get().withHandle(handle -> handle.createQuery("SELECT * FROM user").mapToBean(User.class).list());
        System.out.println(list1);
    }
}
