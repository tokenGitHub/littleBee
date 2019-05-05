package com.littleBee.bee.service.work;

import com.littleBee.bee.dao.WorkDao;
import com.littleBee.bee.domain.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WorkServiceImpl implements WorkService {

    @Autowired
    private WorkDao workDao;

    @Override
    @Transactional
    public List<Work> listWorkByPositionAndIdentity(String province, String city, int identity){
        return workDao.listWorkByPositionAndIdentity(province, city, identity);
    }

    @Override
    @Transactional
    public List<Work> listAllFullTimeWork(){
        return workDao.listAllFullTimeWork();
    }

    public List<Work> listAllWork(){
        return workDao.listAllWork();
    }
}
