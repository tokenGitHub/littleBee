package com.littleBee.bee.controller;

import com.littleBee.bee.domain.FriendAddRecord;
import com.littleBee.bee.domain.Message;
import com.littleBee.bee.domain.User;
import com.littleBee.bee.dto.*;
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
       userName 用户名
       password 密码
       email    邮箱地址
       realName 真实姓名
       tele     电话号码
       sex      用户性别（0男，1女）
       verification  验证码，通过邮件发送到用户邮箱
     * @return         用户完整信息
     */
    @PostMapping("register")
    public Object userRegister(@RequestBody RegisterData registerData){
        User user = parseUserByData(registerData);
        if(registerData.getVerification().equals(redisService.getEmailVerificationCode(user.getEmail()))) {
            userService.insertUser(user);
            return JsonUtils.getSuccessResult(user);
        }else {
            return JsonUtils.getFailResult("Exception : 验证码输入错误，或者已经过期");
        }
    }

    /**
     *
       userName 用户名
       password 密码
     * @return  成功返回： 用户 id，用户 token 码
     *          失败返回： 错误信息
     */
    @PostMapping("login")
    public Object login(@RequestBody User paraUser){
        String userName = paraUser.getUserName();
        String password = paraUser.getPassword();
        String userCode = userService.userLogin(userName, password);
        User user = userService.selectUserByUserName(userName);
        if(userCode != null){
            redisService.setUserLoginCode(userName, userCode);
            return JsonUtils.getSuccessResult(new LoginMessage(user.getId(), userCode));
        }else{
            return JsonUtils.getFailResult("Exception : 账号或密码错误");
        }
    }

    /**
     *
       toAddress 接收验证码的邮箱地址
     * @return 成功返回OK，失败返回错误信息
     */
    @PostMapping("verification")
    public Object sendVerification(@RequestBody VerificationData verificationData){
        try {
            String toAddress = verificationData.getToAddress();
            log.info(toAddress);
            String verification = emailService.sendSimpleMail(toAddress);
            redisService.saveEmailVerificationCode(toAddress, verification);
        }catch (Exception e){
            log.info(e.getMessage());
            return JsonUtils.getFailResult("Exception : 邮件发送失败");
        }
        return JsonUtils.getSuccessResult("OK");
    }

    /**
     *
       userId 用户id，来自 header
     * @return 返回好友申请列表
     */
    @PostMapping("listFriendByUserId")
    public Object listFriendByUserId(@RequestHeader("userId") int userId){
        User user = userService.selectUserById(userId);
        if(user == null ){
            return JsonUtils.getFailResult(new Exception("用户不存在"));
        }
        List<User> userList = userService.listFriendByUserId(userId);
        return JsonUtils.getSuccessResult(userList);
    }

    /**
     *
       realName 用户真实姓名 为空时填写空串
       tele     用户电话号码 为空时填写空串
     * @return         返回用户所有信息
     */
    @PostMapping("findUser")
    public Object findUser(@RequestBody FindUserData findUser){
        String realName = findUser.getRealName();
        String tele = findUser.getTele();
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

    /**
     *
       userId     用户id
       friendId   好友的用户id
       context    验证消息
     * @return           返回请求是否发送成功
     */
    @PostMapping("addFriend")
    public Object addFriend(@RequestHeader("userId") int userId,@RequestBody AddFriendData addFriendData){
        User user = userService.selectUserById(userId);
        int friendId = addFriendData.getFriendId();
        String context = addFriendData.getContext();

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


    /**
     *
       id  好友请求的id
       agreeOrNot    是否同意（1同意，2不同意）
     * @return   返回是否成功
     */
    @PostMapping("friendVerification")
    public Object friendVerification(@RequestBody FriendVerificationData friendVerificationData){
        FriendAddRecord record = friendAddRecordService.selectFriendAddRecordById(friendVerificationData.getRecordId());
        if(record == null){
            return JsonUtils.getFailResult( new Exception("好友请求不存在") );
        }

        friendAddRecordService.saveFriend(record, friendVerificationData.getAgreeOrNot());
        return JsonUtils.getSuccessResult("成功");
    }

    /**
     *
       userId 用户id，来自请求头
     * @return  返回该用户所有好友请求
     */
    @PostMapping("listFriendRequest")
    public Object listFriendRequest(@RequestHeader int userId){
        List list = friendAddRecordService.listFriendAddRecordByUserId(userId);
        return JsonUtils.getSuccessResult(list);
    }

    /**
     *
       userId 发送用户id，来自header
       targetUserId  接收用户id
     * @return 返回此两用户的所有聊天记录
     */
    @PostMapping("listUserChatMessage")
    public Object listUserChatMessage(@RequestHeader("userId") int userId, @RequestBody ListUserChatMessageData listUserChatMessageData){
        List<Message> messageList = messageService.listUserChatMessage(userId, listUserChatMessageData.getTargetUserId());
        return  JsonUtils.getSuccessResult(messageList);
    }

    /**
     *
       userId 发送消息的用户id，来自header
       targetUserId  接收消息的用户id
       context       所要发送的消息
     * @return  返回消息发送是否成功
     */
    @PostMapping("sendMessage")
    public Object sendMessage(@RequestHeader("userId") int userId, @RequestBody SendMessageData sendMessageData){
        int targetUserId = sendMessageData.getTargetUserId();
        User user = userService.selectUserById(targetUserId);
        if(user == null){
            return JsonUtils.getFailResult(new Exception("目标用户不存在"));
        }
        messageService.sendMessage(userId, targetUserId, sendMessageData.getContext());
        return JsonUtils.getSuccessResult(null);
    }

    private User parseUserByData(RegisterData registerData){
        User user = new User();
        user.setUserName(registerData.getUserName());
        user.setEmail(registerData.getEmail());
        user.setPassword(registerData.getPassword());
        user.setRealName(registerData.getRealName());
        user.setTele(registerData.getTele());
        user.setSex(registerData.getSex());
        user.setIdentity(0);
        return user;
    }
}
