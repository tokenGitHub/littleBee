package com.littleBee.bee.controller;

import com.littleBee.bee.domain.User;
import com.littleBee.bee.domain.Work;
import com.littleBee.bee.dto.ListAllReleaseWorkData;
import com.littleBee.bee.dto.ListPersonForWorkData;
import com.littleBee.bee.dto.RegisterData;
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
       userName 用户名
       companyName   公司名
       industry      所属行业
       companyIntroduce  公司介绍
       password      密码
       email         公司邮箱
       realName      用户真实姓名
       tele          公司电话
       sex           注册人性别
       verification  验证码
     * @return  返回公司所有信息
     */
    @PostMapping("register")
    public Object userRegister(@RequestBody RegisterData registerData){

        if(registerData.getVerification().equals(redisService.getEmailVerificationCode(registerData.getEmail()))) {
            User user = parseUserByData(registerData);
            userService.insertUser(user);
            return JsonUtils.getSuccessResult(user);
        }else {
            return JsonUtils.getFailResult("Exception : 验证码输入错误，或者已经过期");
        }
    }

    /**
     *
       userId     发布者id，来自header
       identity   职位类型（0兼职，1全职）
       needPerson 需要人数
       wages      工资
       workTime   工作时间
       city       市
       duty       工作职责
       requirement   工作要求
       contacts      联系人
       tele          联系电话
       remarks       备注
       workName       工作名称

       day         工作天数
     * @return  求职信息是否发布成功
     */
    @PostMapping("releaseFullTimeWork")
    public Object releaseFullTimeWork(@RequestHeader("userId") int userId, @RequestBody Work work){
        User user = userService.selectUserById(userId);
        if(user == null){
            return JsonUtils.getFailResult(new Exception("用户不存在"));
        }else if(user.getIdentity() != 1){
            return JsonUtils.getFailResult(new Exception("只有商家才能发布招聘信息"));
        }
        workService.saveWork(work);
        return JsonUtils.getSuccessResult("职位信息发布成功");
    }

    /**
     *
       userId    商家id
       identity  职位类型 工作类型（0 兼职，1 全职）
     * @return  返回该商家发布的所有对应职业类型的职位
     */
    @PostMapping("listAllReleaseWork")
    public Object listAllReleaseWork(@RequestHeader("userId") int userId, @RequestBody ListAllReleaseWorkData listAllReleaseWorkData){
        User user = userService.selectUserById(userId);
        if(user == null ){
            return JsonUtils.getFailResult(new Exception("用户不存在"));
        }else if(user.getIdentity() != 1){
            return JsonUtils.getFailResult("用户身份不正确");
        }
        List<Work> workList = workService.listAllReleaseWorkByUserIdAndIdentity(userId, listAllReleaseWorkData.getIdentity());
        return JsonUtils.getSuccessResult(workList);
    }


    /**
     *
       userId 商家id
       workId 职位id
     * @return  返回该商家发布的对应职位，的所有报名人的信息
     */
    @PostMapping("listPersonForWork")
    public Object listUserForWork(@RequestHeader("userId") int userId, @RequestBody ListPersonForWorkData listPersonForWorkData){
        User user = userService.selectUserById(userId);
        if(checkBusiness(user)){
            return JsonUtils.getFailResult("用户身份错误");
        }
        List<User> userList = workService.listUserByWorkId(listPersonForWorkData.getWorkId());
        return JsonUtils.getSuccessResult(userList);
    }

    private boolean checkBusiness(User user){
        return user != null && user.getIdentity() == 1;
    }

    private User parseUserByData(RegisterData registerData){
        User user = new User();
        user.setUserName(registerData.getUserName());
        user.setEmail(registerData.getEmail());
        user.setCompanyIntroduce(registerData.getCompanyIntroduce());
        user.setCompanyName(registerData.getCompanyName());
        user.setIndustry(registerData.getIndustry());
        user.setPassword(registerData.getPassword());
        user.setRealName(registerData.getRealName());
        user.setTele(registerData.getTele());
        user.setSex(registerData.getSex());
        user.setIdentity(1);
        return user;
    }
}
