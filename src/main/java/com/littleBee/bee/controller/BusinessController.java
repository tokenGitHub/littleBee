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

    /**
     *
     * @param userName 用户名
     * @param companyName   公司名
     * @param industry      所属行业
     * @param companyIntroduce  公司介绍
     * @param password      密码
     * @param email         公司邮箱
     * @param realName      用户真实姓名
     * @param tele          公司电话
     * @param sex           注册人性别
     * @param verification  验证码
     * @return  返回公司所有信息
     */
    @GetMapping("register")
    public Object userRegister(@RequestParam String userName,
                               @RequestParam String companyName,
                               @RequestParam String industry,
                               @RequestParam String companyIntroduce,
                               @RequestParam String password,
                               @RequestParam String email,
                               @RequestParam String realName,
                               @RequestParam String tele,
                               @RequestParam int sex,
                               @RequestParam String verification){

        if(verification.equals(redisService.getEmailVerificationCode(email))) {
            User user = parseUserByData(userName, password, email, realName, sex, tele, companyName, industry, companyIntroduce);
            userService.insertUser(user);
            return JsonUtils.getSuccessResult(user);
        }else {
            return JsonUtils.getFailResult("Exception : 验证码输入错误，或者已经过期");
        }
    }

    /**
     *
     * @param userId     发布者id，来自header
     * @param identity   职位类型（0兼职，1全职）
     * @param needPerson 需要人数
     * @param wages      工资
     * @param workTime   工作时间
     * @param province   省
     * @param city       市
     * @param duty       工作职责
     * @param requirement   工作要求
     * @param contacts      联系人
     * @param tele          联系电话
     * @param remarks       备注
     * @return  求职信息是否发布成功
     */
    @GetMapping("releaseFullTimeWork")
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
        return JsonUtils.getSuccessResult("职位信息发布成功");
    }

    /**
     *
     * @param userId    商家id
     * @param identity  职位类型 工作类型（0兼职，1全职）
     * @return  返回该商家发布的所有对应职业类型的职位
     */
    @GetMapping("listAllReleaseWork")
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


    /**
     *
     * @param userId 商家id
     * @param workId 职位id
     * @return  返回该商家发布的对应职位，的所有报名人的信息
     */
    @GetMapping("listPersonForWork")
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
