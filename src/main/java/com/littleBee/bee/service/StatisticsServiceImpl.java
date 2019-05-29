package com.littleBee.bee.service;

import com.littleBee.bee.dao.UserDao;
import com.littleBee.bee.dao.WorkDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class StatisticsServiceImpl implements  StatisticsService {
    @Autowired
    UserDao userDao;

    @Autowired
    WorkDao workDao;

    @Override
    @Transactional
    public List<List<Integer>> searchStaticsActiveData(){
        List<List<Integer>> result = new ArrayList();
        List<Integer> userData = userDao.listUserActive();
        int userStartData = userDao.selectUserStartData();
        result.add(userData);
        result.add(statisticsList(userData, userStartData));

        List<Integer> businessData = userDao.listBusinessActive();
        int businessDataStartData = userDao.selectBusinessStartData();
        result.add(businessData);
        result.add(statisticsList(businessData, businessDataStartData));

        List<Integer> partTimeJobData = workDao.listPartTimeJobData();
        int partTimeJobStartData = workDao.selectPartTimeJobStartData();
        result.add(partTimeJobData);
        result.add(statisticsList(partTimeJobData, partTimeJobStartData));
        return result;
    }

    private List<Integer> statisticsList(List<Integer> data, int start){
        List<Integer> result = new ArrayList<>();
        for(Integer i : data){
            result.add(start + i);
            start = start + i;
        }
        return result;
    }
}
