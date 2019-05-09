package com.littleBee.bee.controller;

import com.littleBee.bee.domain.FriendAddRecord;
import com.littleBee.bee.domain.User;
import com.littleBee.bee.dto.LoginMessage;
import com.littleBee.bee.service.*;
import com.littleBee.bee.service.user.EmailService;
import com.littleBee.bee.service.user.FriendAddRecordService;
import com.littleBee.bee.service.user.MessageService;
import com.littleBee.bee.service.user.UserService;
import com.littleBee.bee.utills.JsonUtils;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户接口，请求方式  地址：端口号/user/接口
 * eg :
 *   106.15.92.48:8080/user/register  +  参数
 */
@RestController
@Log
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private FriendAddRecordService friendAddRecordService;

    @Autowired
    private MessageService messageService;

    /**
     *
     * @param userName 用户名
     * @param password 密码
     * @param email    邮箱地址
     * @param realName 真实姓名
     * @param tele     电话号码
     * @param sex      用户性别（0男，1女）
     * @param verification  验证码，通过邮件发送到用户邮箱
     * @return         用户完整信息
     */
    @GetMapping("register")
    public Object userRegister(@RequestParam String userName,
                              @RequestParam String password,
                              @RequestParam String email,
                              @RequestParam String realName,
                              @RequestParam String tele,
                              @RequestParam int sex,
                              @RequestParam String verification){

        if(verification.equals(redisService.getEmailVerificationCode(email))) {
            User user = parseUserByData(userName, password, email, realName, sex, tele);
            userService.insertUser(user);
            return JsonUtils.getSuccessResult(user);
        }else {
            return JsonUtils.getFailResult("Exception : 验证码输入错误，或者已经过期");
        }
    }

    /**
     *
     * @param userName 用户名
     * @param password 密码
     * @return  成功返回： 用户 id，用户 token 码
     *          失败返回： 错误信息
     */
    @GetMapping("login")
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

    /**
     *
     * @param toAddress 接收验证码的邮箱地址
     * @return 成功返回OK，失败返回错误信息
     */
    @GetMapping("verification")
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
     *
     * @param realName 用户真实姓名 为空时填写空串
     * @param tele     用户电话号码 为空时填写空串
     * @return         返回用户所有信息
     */
    public Object findUser( @RequestParam String realName,@RequestParam String tele){
        if(realName != null && !realName.isEmpty()){
            List<User> userList = userService.listUserByRealName(realName);
            return JsonUtils.getSuccessResult(userList);
        }else if(tele != null && !tele.isEmpty()){
            List<User> userList = userService.listUserByUserTele(tele);
            return JsonUtils.getSuccessResult(userList);
        }else{
            return JsonUtils.getFailResult("没有输入正确的参数,必须有用户名和电话的其中之一");
        }
    }

    @PostMapping("addFriend")
     *
     * @param userId     用户id
     * @param friendId   好友的用户id
     * @param context    验证消息
     * @return           返回请求是否发送成功
     */
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

    @PostMapping("sendMessage")
    public Object sendMessage(@RequestHeader("userId") int userId, @RequestParam("targetUserId") int targetUserId, @RequestParam("context") String context){
        User user = userService.selectUserById(targetUserId);
        if(user == null){
            return JsonUtils.getFailResult(new Exception("目标用户不存在"));
        }
        messageService.sendMessage(userId, targetUserId, context);
        return JsonUtils.getSuccessResult(null);
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
