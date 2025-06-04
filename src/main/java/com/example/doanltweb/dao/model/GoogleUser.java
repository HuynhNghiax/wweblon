package com.example.doanltweb.dao.model;

public class GoogleUser {
    private String id;
    private String email;
    private String name;

    public GoogleUser(String id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }

    public GoogleUser() {
    }

    // Getter
    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }
}
