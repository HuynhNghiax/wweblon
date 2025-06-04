package com.example.doanltweb.service;

import com.example.doanltweb.dao.UserDao;
import com.example.doanltweb.dao.model.User;

import java.util.List;

public class UserService {
    static UserDao userDao = new UserDao();
    public List<User> getUserList() {
        return userDao.getAllUsers();
    }
    public User getUserById(int id) {
        return userDao.getUserbyid(id);
    }
    public boolean deleteUserById(int id) {
        return userDao.delete(id);
    }
    public List<User> getUsersForAdmin() {
        return userDao.getUsersForAdmin();
    }
    public boolean updateUser(User user) {
        return userDao.updateUserByAdmin(user);
    }
    public boolean updateVerifiedStatus(int id, int isVerified) {
        return userDao.updateVerifiedStatus(id, isVerified);
    }
    public boolean updateRoleAndStatus(int id, int idPermission, int isVerified) {
        return userDao.updateRoleAndStatus(id, idPermission, isVerified);
    }
    public static void main(String[] args) {
        UserService userService = new UserService();
        List<User> userList = userService.getUserList();
        System.out.println(userList);
    }
}
