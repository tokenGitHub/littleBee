package com.littleBee.bee.controller;

import com.littleBee.bee.domain.Work;
import com.littleBee.bee.service.work.WorkService;
import com.littleBee.bee.utills.JsonUtils;
import netscape.javascript.JSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("work")
public class WorkController {

    @Autowired
    private WorkService workService;

    /**
     *
     * @param province 省份
     * @param city 城市
     * @param identity  工作类型（0兼职，1全职）
     * @return 返回对应城市的所有对应类型的职位
     */
    @PostMapping("listWorkByPositionAndIdentity")
    public Object listWorkByPositionAndIdentity(@RequestParam("province") String province, @RequestParam("city") String city, @RequestParam("identity") int identity){
        List<Work> workList = workService.listWorkByPositionAndIdentity(province, city, identity);
        return JsonUtils.getSuccessResult(workList);
    }

    /**
     *
     * @return 返回所有的全职工作
     */
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

    /**
     *
     * @param userId 普通用户id
     * @return 返回该用户参加过的所有工作
     */
    @PostMapping("listWorkRecord")
    public Object listWorkRecord(@RequestHeader("userId") int userId){
        List<Work> list = workService.listWorkRecord(userId);
        return JsonUtils.getSuccessResult(list);
    }


    /**
     *
     * @param workName 工作名称
     * @return 返回所有跟 workName 相关的工作
     */
    @PostMapping("listWorkByWorkName")
    public Object listWorkByWorkName(@RequestParam("workName") String workName){
        List<Work> workList = workService.listWorkByWorkName(workName);
        return JsonUtils.getSuccessResult(workList);
    }
}
