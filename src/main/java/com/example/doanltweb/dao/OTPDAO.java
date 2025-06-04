package com.example.doanltweb.dao;

import com.example.doanltweb.dao.db.JDBIConnect;
import org.jdbi.v3.core.Jdbi;

public class OTPDAO {
    public void saveOTP(int userId, String otp) {
        Jdbi jdbi = JDBIConnect.get();
        jdbi.useHandle(handle ->
                handle.execute("INSERT INTO otp_codes (user_id, otp_code) VALUES (?, ?)", userId, otp)
        );
    }

    public int getUserIdByEmail(String email) {
        Jdbi jdbi = JDBIConnect.get();
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT id FROM user WHERE email = ?")
                        .bind(0, email)
                        .mapTo(Integer.class)
                        .findOne()
                        .orElse(-1)  // Trả về -1 nếu không tìm thấy
        );
    }

    public String getOTPByUserId(int userId) {
        Jdbi jdbi = JDBIConnect.get();
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT otp_code FROM otp_codes WHERE user_id = ? ORDER BY created_at DESC LIMIT 1")
                        .bind(0, userId)
                        .mapTo(String.class)
                        .findOne()
                        .orElse(null)
        );
    }

}
