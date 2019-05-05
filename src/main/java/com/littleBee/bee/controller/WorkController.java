package com.littleBee.bee.controller;

import com.littleBee.bee.domain.Work;
import com.littleBee.bee.service.work.WorkService;
import com.littleBee.bee.utills.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("work")
public class WorkController {

    @Autowired
    private WorkService workService;

    @PostMapping("listWorkByPositionAndIdentity")
    public Object listWorkByPositionAndIdentity(@RequestParam("province") String province, @RequestParam("city") String city, @RequestParam("identity") int identity){
        List<Work> workList = workService.listWorkByPositionAndIdentity(province, city, identity);
        return JsonUtils.getSuccessResult(workList);
    }

    @PostMapping("listAllFullTimeWork")
    public Object listAllFullTimeWork(){
        List<Work> workList = workService.listAllFullTimeWork();
        return JsonUtils.getSuccessResult(workList);
    }

    @PostMapping("listAllWork")
    public Object listAllWork(){
        List<Work> workList = workService.listAllWork();
        return JsonUtils.getSuccessResult(workList);
    }

    @PostMapping("listWorkRecord")
    public Object listWorkRecord(@RequestHeader("userId") int userId){
        List<Work> list = workService.listWorkRecord(userId);
        return JsonUtils.getSuccessResult(list);
    }
}
