package com.littleBee.bee.service;

import com.littleBee.bee.domain.User;

public interface UserService {
    void insertUser(User user);
    String userLogin(String userName, String password);
}
