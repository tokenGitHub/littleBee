package com.littleBee.bee.service;

import com.littleBee.bee.dao.UserDao;
import com.littleBee.bee.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    @Transactional
    public void insertUser(User user){
        userDao.insertUser(user);
    }
}
