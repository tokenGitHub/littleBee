package com.littleBee.bee.controller;

import com.littleBee.bee.domain.Work;
import com.littleBee.bee.dto.ListWorkByPositionAndIdentityData;
import com.littleBee.bee.dto.ListWorkByWorkNameData;
import com.littleBee.bee.dto.WorkSignUp;
import com.littleBee.bee.service.StatisticsService;
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

    @Autowired
    private StatisticsService statisticsService;

    /**
     *
       city 城市
       identity  工作类型（0兼职，1全职）
     * @return 返回对应城市的所有对应类型的职位
     */
    @PostMapping("listWorkByPositionAndIdentity")
    public Object listWorkByPositionAndIdentity(@RequestBody ListWorkByPositionAndIdentityData listWorkByPositionAndIdentityData){
        if(listWorkByPositionAndIdentityData.getCity() == null ||
        listWorkByPositionAndIdentityData.getCity().isEmpty()){
            listWorkByPositionAndIdentityData.setCity(null);
        }
        List<Work> workList = workService.listWorkByPositionAndIdentity(
                listWorkByPositionAndIdentityData.getCity(),
                listWorkByPositionAndIdentityData.getIdentity()
        );
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


    /**
     *
       userId 普通用户id
     * @return 返回该用户参加过的所有工作
     */
    @PostMapping("listWorkRecord")
    public Object listWorkRecord(@RequestHeader("userId") int userId){
        List<Work> list = workService.listWorkRecord(userId);
        return JsonUtils.getSuccessResult(list);
    }


    /**
     *
     * workName 工作名称
     * @return 返回所有跟 workName 相关的工作
     */
    @PostMapping("listWorkByWorkName")
    public Object listWorkByWorkName(@RequestBody ListWorkByWorkNameData listWorkByWorkNameData){
        List<Work> workList = workService.listWorkByWorkName(listWorkByWorkNameData.getWorkName());
        return JsonUtils.getSuccessResult(workList);
    }


    @PostMapping("searchStaticsActiveData")
    public Object searchStaticsActiveData(){
        return JsonUtils.getSuccessResult(statisticsService.searchStaticsActiveData());
    }

    @PostMapping("workSignUp")
    public Object workSignUp(@RequestBody WorkSignUp workSignUp){
        workService.insertWorkRecord(workSignUp.getUserId(), workSignUp.getWorkId());
        return JsonUtils.getSuccessResult("报名成功");
    }
}
