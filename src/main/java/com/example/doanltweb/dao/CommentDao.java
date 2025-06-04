package com.example.doanltweb.dao;

import com.example.doanltweb.dao.db.JDBIConnect;
import com.example.doanltweb.dao.model.Comment;
import org.jdbi.v3.core.Jdbi;

import java.util.List;

public class CommentDao {
    private Jdbi jdbi;

    public CommentDao() {
        jdbi = JDBIConnect.get();
    }

    // Thêm comment
    public void insertComment(Comment comment) {
        String sql = "INSERT INTO comment(content, star, idUser, idProduct) VALUES (:content, :star, :idUser, :idProduct)";
        jdbi.useHandle(handle -> {
            handle.createUpdate(sql)
                    .bind("content", comment.getContent())
                    .bind("star", comment.getStar())
                    .bind("idUser", comment.getIdUser())
                    .bind("idProduct", comment.getIdProduct())
                    .execute();
        });
    }

    // Lấy danh sách comment theo id sản phẩm
    public List<Comment> getCommentsByProductId(int idProduct) {
        String sql = "SELECT c.*, u.username FROM comment c JOIN user u ON c.idUser = u.id WHERE c.idProduct = :idProduct";
        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .bind("idProduct", idProduct)
                        .map((rs, ctx) -> new Comment(
                                rs.getInt("id"),
                                rs.getString("content"),
                                rs.getInt("star"),
                                rs.getInt("idUser"),
                                rs.getInt("idProduct"),
                                rs.getString("username")
                        ))
                        .list()
        );
    }
    public static void main(String[] args) {

    }
}
