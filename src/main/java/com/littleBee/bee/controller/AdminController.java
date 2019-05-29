package com.littleBee.bee.controller;

import com.littleBee.bee.domain.Work;
import com.littleBee.bee.dto.Examine;
import com.littleBee.bee.dto.ExamineWork;
import com.littleBee.bee.service.user.UserService;
import com.littleBee.bee.service.work.WorkService;
import com.littleBee.bee.utills.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private UserService userService;

    @Autowired
    private WorkService workService;

    @PostMapping("examine")
    public Object examine(@RequestBody Examine examine){
        userService.examine(examine.getUserId(), examine.getStatus());
        return JsonUtils.getSuccessResult("审核成功，已经通过邮件告知用户");
    }

    @PostMapping("listAllCompanyUser")
    public Object listAllCompanyUser(){
        return JsonUtils.getSuccessResult(userService.listAllCompanyUser());
    }

    @PostMapping("listAllWork")
    public Object listAllWork(){
        List<Work> workList = workService.listAllWork();
        return JsonUtils.getSuccessResult(workList);
    }

    @PostMapping("examineWork")
    public Object examineWork(@RequestBody ExamineWork examineWork){
        workService.examine(examineWork.getWorkId(), examineWork.getStatus());
        return JsonUtils.getSuccessResult("工作审核完成");
    }
}
