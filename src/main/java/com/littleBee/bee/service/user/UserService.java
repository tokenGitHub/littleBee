package com.littleBee.bee.service.user;

import com.littleBee.bee.domain.User;

import java.util.List;

public interface UserService {
    void insertUser(User user);
    String userLogin(String userName, String password);
    User selectUserByUserName(String userName);
    User selectUserById(int id);
    List<User> listFriendByUserId(int id);
    List<User> listUserByUserTele(String tele);
    List<User> listUserByRealName(String realName);
    void examine(int userId, int status);
}
