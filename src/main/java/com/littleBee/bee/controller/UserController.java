package com.littleBee.bee.controller;

import com.littleBee.bee.domain.User;
import com.littleBee.bee.service.EmailService;
import com.littleBee.bee.service.UserService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

@RestController
@Log
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    EmailService emailService;

    @PostMapping("register")
    public Object userRegister(@RequestParam String userName,
                              @RequestParam String password,
                              @RequestParam String email,
                              @RequestParam Date birthday,
                              @RequestParam String degree,
                              @RequestParam String address,
                              @RequestParam String realName){
        Date date = new Date(new java.util.Date().getTime());
        User user = parseUserByData(userName, password, email,
                birthday, degree, address, realName,date);
        userService.insertUser(user);
        return user;
    }

    @GetMapping("test")
    public Object testUserRegister(){
        Date date = new Date(new java.util.Date().getTime());
        User user = parseUserByData("testName","password","email",
                date,"本科","中国","王大炮",date);
        userService.insertUser(user);
        return "OK";
    }

    @GetMapping("verification")
    public Object sendVerification(@RequestParam String toAddress){
        try {
            String verification = emailService.sendSimpleMail(toAddress);
        }catch (Exception e){
            log.info(e.getMessage());
            return "fial : 邮箱不存在？";
        }
        return "OK";
    }

    private User parseUserByData(String userName,
                               String password,
                               String email,
                               Date birthday,
                               String degree,
                               String address,
                               String realName,
                               Date createTime){
        User user = new User();
        user.setUserName(userName);
        user.setEmail(email);
        user.setAddress(address);
        user.setPassword(password);
        user.setBirthday(birthday);
        user.setDegree(degree);
        user.setRealName(realName);
        user.setCreateTime(createTime);
        return user;
    }
}
