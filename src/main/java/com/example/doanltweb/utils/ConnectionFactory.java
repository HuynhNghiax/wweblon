package com.example.doanltweb.utils;

import com.example.doanltweb.dao.db.JDBIConnect;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionFactory {
    public static Connection getConnection() throws SQLException {
        return JDBIConnect.get().open().getConnection();
    }
}
