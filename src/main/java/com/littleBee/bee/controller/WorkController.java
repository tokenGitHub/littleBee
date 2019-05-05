package com.littleBee.bee.controller;

import com.littleBee.bee.domain.Work;
import com.littleBee.bee.service.work.WorkService;
import com.littleBee.bee.utills.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("work")
public class WorkController {

    @Autowired
    private WorkService workService;

    @PostMapping("listWork")
    public Object listWork(@RequestParam("province") String province, @RequestParam("city") String city, @RequestParam("identity") int identity){
        List<Work> workList = workService.listWorkByPositionAndIdentity(province, city, identity);
        return JsonUtils.getSuccessResult(workList);
    }
}
