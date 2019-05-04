package com.littleBee.bee.controller;

import com.littleBee.bee.domain.FriendAddRecord;
import com.littleBee.bee.domain.Message;
import com.littleBee.bee.domain.User;
import com.littleBee.bee.dto.LoginMessage;
import com.littleBee.bee.service.*;
import com.littleBee.bee.utills.JsonUtils;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    EmailService emailService;

    @Autowired
    RedisService redisService;

    @Autowired
    FriendAddRecordService friendAddRecordService;

    @Autowired
    MessageService messageService;

    @PostMapping("register")
    public Object userRegister(@RequestParam String userName,
                              @RequestParam String password,
                              @RequestParam String email,
                              @RequestParam String realName,
                              @RequestParam String para,
                              @RequestParam int sex,
                              @RequestParam String verification){

        if(verification.equals(redisService.getEmailVerificationCode(email))) {
            User user = parseUserByData(userName, password, email, realName, sex, para);
            userService.insertUser(user);
            return JsonUtils.getSuccessResult(user);
        }else {
            return JsonUtils.getFailResult("Exception : 验证码输入错误，或者已经过期");
        }
    }

    @PostMapping("login")
    public Object login(@RequestParam String userName, @RequestParam String password){
        String userCode = userService.userLogin(userName, password);
        User user = userService.selectUserByUserName(userName);
        if(userCode != null){
            redisService.setUserLoginCode(userName, userCode);
            return JsonUtils.getSuccessResult(new LoginMessage(user.getId(), userCode));
        }else{
            return JsonUtils.getFailResult(new Exception("Exception : 账号或密码错误"));
        }
    }

    @PostMapping("verification")
    public Object sendVerification(@RequestParam String toAddress){
        try {
            String verification = emailService.sendSimpleMail(toAddress);
            redisService.saveEmailVerificationCode(toAddress, verification);
        }catch (Exception e){
            log.info(e.getMessage());
            return JsonUtils.getFailResult("Exception : 邮件发送失败");
        }
        return JsonUtils.getSuccessResult("OK");
    }

    @PostMapping("listFriend")
    public Object listFriendByUserId(@RequestParam("id") int id){
        User user = userService.selectUserById(id);
        if(user == null ){
            return JsonUtils.getFailResult(new Exception("用户不存在"));
        }
        List<User> userList = userService.listFriendByUserId(id);
        return JsonUtils.getSuccessResult(userList);
    }

    @PostMapping("findUser")
    public Object findUser( @RequestParam("userName")  String realName, String tele){
        if(realName != null){
            List<User> userList = userService.listUserByRealName(realName);
            return JsonUtils.getSuccessResult(userList);
        }else if(tele != null){
            List<User> userList = userService.listUserByUserTele(tele);
            return JsonUtils.getSuccessResult(userList);
        }else{
            return JsonUtils.getFailResult("没有输入正确的参数,必须有用户名和电话的其中之一");
        }
    }

    @PostMapping("addFriend")
    public Object addFriend(@RequestHeader("userId") int userId, @RequestParam("friendId") int friendId, @RequestParam("context") String context){
        User user = userService.selectUserById(userId);
        User friend = userService.selectUserById(friendId);
        if(user == null || friend == null){
            return JsonUtils.getFailResult(new Exception("用户不存在或者要添加的用户不存在"));
        }
        FriendAddRecord record = new FriendAddRecord();
        record.setContext(context);
        record.setFriendUserId(friendId);
        record.setUserId(userId);
        friendAddRecordService.saveRecord(record);
        return JsonUtils.getSuccessResult("好友请求已记录");
    }

    @PostMapping("friendVerification")
    public Object friendVerification(@RequestParam("recordId") int id, int agreeOrNot){
        FriendAddRecord record = friendAddRecordService.selectFriendAddRecordById(id);
        if(record == null){
            return JsonUtils.getFailResult( new Exception("好友请求不存在") );
        }

        friendAddRecordService.saveFriend(record, agreeOrNot);
        return JsonUtils.getSuccessResult("成功");
    }

    @PostMapping("listFriendRequest")
    public Object listFriendRequest(@RequestHeader("userId") int userId){
        return friendAddRecordService.listFriendAddRecordByUserId(userId);
    }

    @PostMapping("listUserChatMessage")
    public Object listUserChatMessage(@RequestHeader("userId") int userId, @RequestParam("targetUserId") int targetUserId){
        return messageService.listUserChatMessage(userId, targetUserId);
    }

    private User parseUserByData(String userName, String password, String email, String realName, int sex, String tele){
        User user = new User();
        user.setUserName(userName);
        user.setEmail(email);
        user.setPassword(password);
        user.setRealName(realName);
        user.setTele(tele);
        user.setSex(sex);
        user.setIdentity(0);
        return user;
    }
}
