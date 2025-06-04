package com.example.doanltweb.dao;

import com.example.doanltweb.dao.db.JDBIConnect;
import com.example.doanltweb.dao.model.Product;
import com.example.doanltweb.dao.model.User;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.apache.commons.codec.digest.DigestUtils;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDao {
    static Map<Integer, User> data = new HashMap<>();
//admin
    public List<User> getUsersForAdmin() {
        String sql = "SELECT id, avatar, username, fullname, email, phone, address, idPermission, is_verified FROM user";

        Jdbi jdbi = JDBIConnect.get();  // Kết nối JDBI

        return jdbi.withHandle(handle -> {
            // Thực thi truy vấn và ánh xạ kết quả vào danh sách User
            return handle.createQuery(sql)
                    .mapToBean(User.class)  // Ánh xạ các kết quả thành đối tượng User
                    .list();                // Thu thập kết quả vào danh sách và trả về
        });
    }
    public boolean updateUserByAdmin(User user) {
        String sql = "UPDATE user SET avatar = :avatar,username = :username,fullname = :fullname,email = :email,phone = :phone,address = :address,idPermission = :idPermission,is_verified = :isVerified WHERE id = :id";
        Jdbi jdbi = JDBIConnect.get();

        int rowsAffected = jdbi.withHandle(handle ->
                handle.createUpdate(sql)
                        .bind("avatar", user.getAvatar())
                        .bind("username", user.getUsername())
                        .bind("fullname", user.getFullname())
                        .bind("email", user.getEmail())
                        .bind("phone", user.getPhone())
                        .bind("address", user.getAddress())
                        .bind("idPermission", user.getIdPermission())
                        .bind("isVerified", user.getIsVerified())
                        .bind("id", user.getId())
                        .execute()
        );

        return rowsAffected > 0;
    }
    public boolean updateRoleAndStatus(int id, int idPermission, int isVerified) {
        String sql = "UPDATE user SET idPermission = :idPermission, is_verified = :isVerified WHERE id = :id";
        Jdbi jdbi = JDBIConnect.get();
        int rows = jdbi.withHandle(handle ->
                handle.createUpdate(sql)
                        .bind("id", id)
                        .bind("idPermission", idPermission)
                        .bind("isVerified", isVerified)
                        .execute()
        );
        return rows > 0;
    }
    public boolean updateVerifiedStatus(int id, int isVerified) {
        String sql = "UPDATE user SET is_verified = :isVerified WHERE id = :id";
        Jdbi jdbi = JDBIConnect.get();

        int rowsAffected = jdbi.withHandle(handle ->
                handle.createUpdate(sql)
                        .bind("isVerified", isVerified)
                        .bind("id", id)
                        .execute()
        );

        return rowsAffected > 0;
    }


//user
    public List<User> getAllUsers() {
        Jdbi jdbi = JDBIConnect.get();
        return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM user").mapToBean(User.class).list());
    }

    public User getUserbyid(int id) {
        Jdbi jdbi = JDBIConnect.get();
        return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM user where id= :id").bind("id", id)
                .mapToBean(User.class).findOne().orElse(null));
    }

    public User login(String username, String rawPassword) {
        Jdbi jdbi = JDBIConnect.get();
        try {
            User user = jdbi.withHandle(handle ->
                    handle.createQuery("SELECT * FROM user WHERE username = :username")
                            .bind("username", username)
                            .mapToBean(User.class)
                            .findOne()
                            .orElse(null)
            );
            if (user == null) {
                return null;
            }
            // So sánh mật khẩu nhập vào với hash trong db
            if (BCrypt.checkpw(rawPassword, user.getPassword())) {
                return user;
            } else {
                return null; // Mật khẩu sai
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void insert(String username,
                       String rawPassword,
                       String fullname,
                       String email,
                       String phone,
                       String address,
                       int idPermission) {
        Jdbi jdbi = JDBIConnect.get();

        // Hash mật khẩu trước khi lưu
        String hashedPassword = BCrypt.hashpw(rawPassword, BCrypt.gensalt());

        jdbi.useHandle(handle -> handle.createUpdate(
                        "INSERT INTO user (username, password, fullname, email, phone, address, idPermission) " +
                                "VALUES (:username, :password, :fullname, :email, :phone, :address, :idPermission)")
                .bind("username", username)
                .bind("password", hashedPassword)  // Lưu mật khẩu đã hash
                .bind("fullname", fullname)
                .bind("email", email)
                .bind("phone", phone)
                .bind("address", address)
                .bind("idPermission", idPermission)
                .execute());
    }


    public boolean delete(int id) {
        Jdbi jdbi = JDBIConnect.get();
        try {
            jdbi.useHandle(handle -> handle.createUpdate("DELETE FROM user WHERE id = :id")
                    .bind("id", id)
                    .execute());
            return true; // Xóa thành công
        } catch (Exception e) {
            e.printStackTrace(); // In thông tin lỗi
            return false; // Xóa thất bại
        }
    }

public boolean updateUser(String fullname, String email, String address, String phone, String avatar, int id) {
    String sql = "UPDATE `user` SET fullname = :fullname, address = :address, phone = :phone, email = :email, avatar = :avatar WHERE id = :id";
    Jdbi jdbi = JDBIConnect.get();
    int updatedRows = jdbi.withHandle(handle ->
            handle.createUpdate(sql)
                    .bind("fullname", fullname)
                    .bind("address", address)
                    .bind("phone", phone)
                    .bind("email", email)
                    .bind("avatar", avatar)
                    .bind("id", id)
                    .execute()
    );
    return updatedRows > 0;
}

    public User getUserByEmail(String email) {
        Jdbi jdbi = new JDBIConnect().get(); // Kết nối Jdbi
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT * FROM user WHERE email = :email")
                        .bind("email", email)
                        .mapToBean(User.class) // Ánh xạ kết quả vào class User
                        .findOne() // Trả về Optional<User>
                        .orElse(null) // Nếu không tìm thấy, trả về null
        );
    }

    public User getUserById(int userId) {
        Jdbi jdbi = new JDBIConnect().get(); // Lấy kết nối Jdbi
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT * FROM user WHERE id = :userId")
                        .bind("userId", userId)
                        .mapToBean(User.class) // Ánh xạ dữ liệu vào class User
                        .findOne() // Trả về Optional<User>
                        .orElse(null) // Nếu không tìm thấy, trả về null
        );
    }
    public void updatePassword(String email, String rawPassword) {
        Jdbi jdbi = new JDBIConnect().get();

        // Hash mật khẩu mới
        String hashedPassword = BCrypt.hashpw(rawPassword, BCrypt.gensalt());

        jdbi.useHandle(handle ->
                handle.createUpdate("UPDATE user SET password = :password WHERE email = :email")
                        .bind("password", hashedPassword)
                        .bind("email", email)
                        .execute()
        );
    }
    public void addUser(String username, String rawPassword, String email, String fullname, String phone, String address) {
        Jdbi jdbi = new JDBIConnect().get();

        // Hash mật khẩu
        String hashedPassword = BCrypt.hashpw(rawPassword, BCrypt.gensalt());

        jdbi.useHandle(handle ->
                handle.execute("INSERT INTO user (username, password, email, fullname, phone, address, is_verified, idPermission) " +
                                "VALUES (?, ?, ?, ?, ?, ?, 0, 2)",
                        username, hashedPassword, email, fullname, phone, address)
        );
    }


    public boolean isUserExists(String email) {
        Jdbi jdbi = new JDBIConnect().get(); // Kết nối Jdbi
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT COUNT(*) FROM user WHERE email = ?")
                        .bind(0, email)
                        .mapTo(Integer.class)
                        .one()
        ) > 0;
    }
    public void updateUserVerifiedById(int userId) {
        Jdbi jdbi = new JDBIConnect().get(); // Kết nối Jdbi
        jdbi.useHandle(handle ->
                handle.execute("UPDATE user SET is_verified = 1 WHERE id = ?", userId)
        );
    }

    public int getUserIdByEmail(String email) {
        Jdbi jdbi = new JDBIConnect().get(); // Kết nối Jdbi
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT id FROM user WHERE email = ?")
                        .bind(0, email)
                        .mapTo(Integer.class)
                        .findOne()
                        .orElse(-1)  // Trả về -1 nếu không tìm thấy
        );
    }
    public void insertUser(String username, String fullname, String email) {
        Jdbi jdbi = new JDBIConnect().get();
        String sql = "INSERT INTO user (username, fullname, email, password, idPermission, is_verified) " +
                "VALUES (:username, :fullname, :email, :password, :idPermission, :isVerified)";

        // Hash mật khẩu mặc định "default123"
        String defaultPassword = "default123";
        String hashedPassword = BCrypt.hashpw(defaultPassword, BCrypt.gensalt());

        jdbi.useHandle(handle ->
                handle.createUpdate(sql)
                        .bind("username", username)
                        .bind("fullname", fullname)
                        .bind("email", email)
                        .bind("password", hashedPassword)  // Lưu mật khẩu đã hash
                        .bind("idPermission", 2)  // Mặc định quyền user
                        .bind("isVerified", 1)    // Mặc định đã verified
                        .execute()
        );
    }

    public User findByEmail(String email) {
        Jdbi jdbi = new JDBIConnect().get();
        try (Handle handle = jdbi.open()) {
            return handle.createQuery("SELECT * FROM user WHERE email = :email")
                    .bind("email", email)
                    .mapToBean(User.class)
                    .findOne()
                    .orElse(null);
        }
    }
    public void lockUserByUsername(String username) {
        Jdbi jdbi = new JDBIConnect().get();
        jdbi.useHandle(handle -> {
            String sql = "UPDATE user SET is_verified = 0 WHERE username = :username";
            handle.createUpdate(sql)
                    .bind("username", username)
                    .execute();
        });
        System.out.println("lock user by username " + username);
    }
    public boolean checkLockUserByUsername(String username) {
        Jdbi jdbi = new JDBIConnect().get();

        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT is_verified FROM user WHERE username = :username")
                        .bind("username", username)
                        .mapTo(Integer.class)
                        .findOne()
                        .map(isVerified -> isVerified == 0) // true nếu bị khóa
                        .orElse(false)
        );
    }
    public boolean changePassword(int userId, String newPassword) throws SQLException {
        Jdbi jdbi = JDBIConnect.get();

        int result = jdbi.withHandle(handle ->
                handle.createUpdate("UPDATE users SET password = :password WHERE id = :id")
                        .bind("password", newPassword)
                        .bind("id", userId)
                        .execute()
        );
        return result > 0;
    }
    public boolean checkPassword(int userId, String password) {
        Jdbi jdbi = JDBIConnect.get(); // Giả sử jdbiconnect.get() trả về một đối tượng Jdbi

        String storedPassword = jdbi.withHandle(handle ->
                handle.createQuery("SELECT password FROM user WHERE id = :userId")
                        .bind("userId", userId)
                        .mapTo(String.class)
                        .findOne()
                        .orElse(null)
        );

        if (storedPassword == null) {
            return false; // Không tìm thấy người dùng
        }

        return storedPassword.equals(password); // So sánh mật khẩu đơn giản (chưa mã hóa)
    }
    public boolean updatePassword(int userId, String newPassword) {
        Jdbi jdbi = JDBIConnect.get();
        int updatedRows = jdbi.withHandle(handle ->
                handle.createUpdate("UPDATE user SET password = :password WHERE id = :userId")
                        .bind("password", newPassword)
                        .bind("userId", userId)
                        .execute()
        );
        return updatedRows > 0;
    }


//        userDao.lockUserByUsername("admin");


    public static void main(String[] args) {
    }
}