package com.littleBee.bee.controller;

import com.littleBee.bee.domain.User;
import com.littleBee.bee.domain.Work;
import com.littleBee.bee.service.user.EmailService;
import com.littleBee.bee.service.user.UserService;
import com.littleBee.bee.service.work.WorkService;
import com.littleBee.bee.utills.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("business")
public class BusinessController {

    @Autowired
    private WorkService workService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private UserService userService;

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
}
