package com.littleBee.bee.service;

import com.littleBee.bee.dao.UserDao;
import com.littleBee.bee.domain.User;
import org.apache.tomcat.util.digester.Digester;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.sql.Date;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    @Transactional
    public void insertUser(User user){
        userDao.insertUser(user);
    }

    @Override
    @Transactional
    public String userLogin(String userName, String password){
        Date date = new Date(new java.util.Date().getTime());
        String select = userDao.userLogin(userName);
        if(select != null && select.equals(password)) {
            String md5 = DigestUtils.md5DigestAsHex((userName + password + date.toString()).getBytes());
            return md5;
        }else{
            return null;
        }
    }
}
