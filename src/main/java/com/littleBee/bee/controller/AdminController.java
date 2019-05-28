package com.littleBee.bee.controller;

import com.littleBee.bee.dto.Examine;
import com.littleBee.bee.service.user.UserService;
import com.littleBee.bee.utills.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private UserService userService;

    @PostMapping("examine")
    public Object examine(@RequestBody Examine examine){
        userService.examine(examine.getUserId(), examine.getStatus());
        return JsonUtils.getSuccessResult("审核成功");
    }

    @PostMapping("listStatusIsZeroUser")
    public Object listStatusIsZeroUser(){
        return userService.listStatusIsZeroUser();
    }
}
