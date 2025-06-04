/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.doanltweb.dao;


import com.example.doanltweb.dao.db.JDBIConnect;
import com.example.doanltweb.dao.model.TokenForgetPassword;
import org.jdbi.v3.core.Jdbi;



public class DAOTokenForget {
    public boolean insertTokenForget(TokenForgetPassword tokenForget) {
        Jdbi jdbi = new JDBIConnect().get(); // Kết nối Jdbi
        int rowsAffected = jdbi.withHandle(handle ->
                handle.createUpdate("INSERT INTO tokenForgetPassword (token, expiryTime, isUsed, userId) VALUES (:token, :expiryTime, :isUsed, :userId)")
                        .bind("token", tokenForget.getToken())
                        .bind("expiryTime", tokenForget.getExpiryTime()) // Cột này là TIMESTAMP
                        .bind("isUsed", tokenForget.isIsUsed())
                        .bind("userId", tokenForget.getUserId())
                        .execute() // Thực thi lệnh SQL
        );

        return rowsAffected > 0; // Trả về true nếu có ít nhất một dòng bị ảnh hưởng
    }

    public TokenForgetPassword getTokenPassword(String token) {
        Jdbi jdbi = new JDBIConnect().get(); // Kết nối Jdbi
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT * FROM tokenForgetPassword WHERE token = :token")
                        .bind("token", token) // Gán giá trị token vào truy vấn
                        .mapToBean(TokenForgetPassword.class) // Ánh xạ dữ liệu vào class TokenForgetPassword
                        .findOne() // Trả về Optional<TokenForgetPassword>
                        .orElse(null) // Nếu không tìm thấy, trả về null
        );
    }

    public void updateStatus(TokenForgetPassword token) {
        Jdbi jdbi = new JDBIConnect().get(); // Kết nối Jdbi
        jdbi.useHandle(handle ->
                handle.createUpdate("UPDATE tokenForgetPassword SET isUsed = :isUsed WHERE token = :token")
                        .bind("isUsed", token.isIsUsed()) // Gán giá trị isUsed
                        .bind("token", token.getToken()) // Gán giá trị token
                        .execute() // Thực thi lệnh SQL
        );
    }
}
