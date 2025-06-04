package com.example.doanltweb.dao.model;

public class Comment {
        private int id;
        private String content;
        private int star;
        private int idUser;
        private int idProduct;
        private String username;

    public Comment() {

    }

    public Comment(int id, String content, int star, int idUser, int idProduct, String username) {
        this.id = id;
        this.content = content;
        this.star = star;
        this.idUser = idUser;
        this.idProduct = idProduct;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", star=" + star +
                ", idUser=" + idUser +
                ", idProduct=" + idProduct +
                ", username='" + username + '\'' +
                '}';
    }
}
