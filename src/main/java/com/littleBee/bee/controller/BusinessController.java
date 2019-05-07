package com.littleBee.bee.controller;

import com.littleBee.bee.domain.User;
import com.littleBee.bee.domain.Work;
import com.littleBee.bee.service.RedisService;
import com.littleBee.bee.service.user.UserService;
import com.littleBee.bee.service.work.WorkService;
import com.littleBee.bee.utills.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("business")
public class BusinessController {

    @Autowired
    private WorkService workService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private UserService userService;


    @PostMapping("register")
    public Object userRegister(@RequestParam String userName,
                               @RequestParam String companyName,
                               @RequestParam String industry,
                               @RequestParam String companyIntroduce,
                               @RequestParam String password,
                               @RequestParam String email,
                               @RequestParam String realName,
                               @RequestParam String para,
                               @RequestParam int sex,
                               @RequestParam String verification){

        if(verification.equals(redisService.getEmailVerificationCode(email))) {
            User user = parseUserByData(userName, password, email, realName, sex, para, companyName, industry, companyIntroduce);
            userService.insertUser(user);
            return JsonUtils.getSuccessResult(user);
        }else {
            return JsonUtils.getFailResult("Exception : 验证码输入错误，或者已经过期");
        }
    }

    @PostMapping("releaseFullTimeWork")
    public Object releaseFullTimeWork(@RequestHeader("userId") int userId, @RequestParam int identity,
                                      @RequestParam int needPerson, @RequestParam String wages,
                                      @RequestParam String workTime, @RequestParam String province,
                                      @RequestParam String city, @RequestParam String duty,
                                      @RequestParam String requirement, @RequestParam String contacts,
                                      @RequestParam String tele, @RequestParam String remarks){
        User user = userService.selectUserById(userId);
        if(user == null){
            return JsonUtils.getFailResult(new Exception("用户不存在"));
        }else if(user.getIdentity() != 1){
            return JsonUtils.getFailResult(new Exception("只有商家才能发布招聘信息"));
        }
        Work work = new Work();
        work.setCity(city);
        work.setContacts(contacts);
        work.setDuty(duty);
        work.setIdentity(identity);
        work.setNeedPerson(needPerson);
        work.setProvince(province);
        work.setRemarks(remarks);
        work.setTele(tele);
        work.setUserId(userId);
        work.setWages(wages);
        work.setWorkTime(workTime);
        work.setRequirement(requirement);

        workService.saveWork(work);
        return JsonUtils.getSuccessResult("求职信息发布成功");
    }

    @PostMapping("listAllReleaseWork")
    public Object listAllReleaseWork(@RequestHeader("userId") int userId, @RequestParam("identity") int identity){
        User user = userService.selectUserById(userId);
        if(user == null ){
            return JsonUtils.getFailResult(new Exception("用户不存在"));
        }else if(user.getIdentity() != 1){
            return JsonUtils.getFailResult("用户身份不正确");
        }
        List<Work> workList = workService.listAllReleaseWorkByUserIdAndIdentity(userId, identity);
        return JsonUtils.getSuccessResult(workList);
    }

    @PostMapping("listPersonForWork")
    public Object listUserForWork(@RequestHeader("userId") int userId, @RequestParam("workId") int workId){
        User user = userService.selectUserById(userId);
        if(checkBusiness(user)){
            return JsonUtils.getFailResult("用户身份错误");
        }
        List<User> userList = workService.listUserByWorkId(workId);
        return JsonUtils.getSuccessResult(userList);
    }

    private boolean checkBusiness(User user){
        return user != null && user.getIdentity() == 1;
    }

    private User parseUserByData(String userName, String password, String email, String realName, int sex, String tele, String companyName, String industry, String companyIntroduce){
        User user = new User();
        user.setUserName(userName);
        user.setEmail(email);
        user.setCompanyIntroduce(companyIntroduce);
        user.setCompanyName(companyName);
        user.setIndustry(industry);
        user.setPassword(password);
        user.setRealName(realName);
        user.setTele(tele);
        user.setSex(sex);
        user.setIdentity(1);
        return user;
    }
}
