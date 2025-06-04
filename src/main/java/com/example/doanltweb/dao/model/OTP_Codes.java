package com.example.doanltweb.dao.model;

import java.time.LocalDateTime;

public class OTP_Codes {
    private int id, user_Id;
    private String code;
    private LocalDateTime create_time;

    public OTP_Codes(int id, int user_Id, String code, LocalDateTime create_time) {
        this.id = id;
        this.user_Id = user_Id;
        this.code = code;
        this.create_time = create_time;
    }

    public OTP_Codes() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_Id() {
        return user_Id;
    }

    public void setUser_Id(int user_Id) {
        this.user_Id = user_Id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getCreate_time() {
        return create_time;
    }

    public void setCreate_time(LocalDateTime create_time) {
        this.create_time = create_time;
    }

    @Override
    public String toString() {
        return "OTP_Codes{" +
                "id=" + id +
                ", user_Id=" + user_Id +
                ", code='" + code + '\'' +
                ", create_time=" + create_time +
                '}';
    }
}
