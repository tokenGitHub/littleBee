package com.littleBee.bee.service.user;

import com.littleBee.bee.dao.UserDao;
import com.littleBee.bee.domain.User;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.sql.Date;
import java.util.List;

@Log
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private EmailService emailService;

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

    @Override
    @Transactional
    public User selectUserByUserName(String userName){
        return userDao.selectUserByUserName(userName);
    }

    @Override
    @Transactional
    public User selectUserById(int id){
        return userDao.selectUserById(id);
    }

    @Override
    @Transactional
    public List<User> listFriendByUserId(int id){
        return userDao.listFriendByUserId(id);
    }

    @Override
    @Transactional
    public List<User> listUserByUserTele(String tele){
        return userDao.listUserByUserTele(tele);
    }

    @Override
    @Transactional
    public List<User> listUserByRealName(String realName){
        return userDao.listUserByRealName(realName);
    }

    @Override
    @Transactional
    public void examine(int userId, int status){
        User user = userDao.selectUserById(userId);
        String context = "";
        if(status == 1){
            context = "审核已通过,祝您工作顺利";
        }
        if(status == 2){
            context = "审核不通过，请重新注册";
        }
        try {
            emailService.sendSimpleMail(user.getEmail(), context);
        }catch (Exception e){
            log.info("examine : 通知邮件发送失败");
        }
        userDao.examine(userId, status);
    }

    @Override
    @Transactional
    public List<User> listAllCompanyUser(){
        List<User> list = userDao.listAllCompanyUser();
        return list;
    }
}
