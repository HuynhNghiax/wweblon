package com.example.doanltweb.dao;

import com.example.doanltweb.dao.db.JDBIConnect;
import org.jdbi.v3.core.Jdbi;

public class VoucherUsageDao {
    private static final Jdbi jdbi = JDBIConnect.get();

    public int countUsageByUser(int userId, int voucherId) {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT COUNT(*) FROM voucher_usages WHERE user_id = :userId AND voucher_id = :voucherId")
                        .bind("userId", userId)
                        .bind("voucherId", voucherId)
                        .mapTo(int.class)
                        .findOnly()
        );
    }
}
