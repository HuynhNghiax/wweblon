package com.example.doanltweb.dao.db;

import java.io.IOException;
import java.util.Properties;

public class DBProperties {
    private static Properties prop = new Properties();

    static {
        try {
            // Kiểm tra và tải file db.properties
            if (DBProperties.class.getClassLoader().getResourceAsStream("db.properties") == null) {
                throw new IOException("File db.properties không được tìm thấy trong classpath");
            }
            prop.load(DBProperties.class.getClassLoader().getResourceAsStream("db.properties"));
        } catch (IOException e) {
            System.err.println("Không thể tải file db.properties. Kiểm tra lại!");
            e.printStackTrace();
            System.exit(1); // Tắt chương trình nếu không đọc được file
        }
    }

    public static String host() {
        return prop.get("db.host").toString();
    }

    public static int port() {
        try {
            return Integer.parseInt(prop.get("db.port").toString());
        } catch (NumberFormatException e) {
            return 3306;
        }
    }
    public static String username() {
        return prop.get("db.username").toString();
    }
    public static String password() {
        return prop.get("db.password").toString();
    }
    public static String dbname() {
        return prop.get("db.dbname").toString();
    }
    public static String option() {
        return prop.get("db.option").toString();
    }
}
