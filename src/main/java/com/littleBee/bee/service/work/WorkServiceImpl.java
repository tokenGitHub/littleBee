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

    @Override
    @Transactional
    public List<Work> listWorkRecord(int userId){
        return workDao.listWorkRecord(userId);
    }

    @Override
    @Transactional
    public void saveWork(Work work){
        workDao.saveWork(work);
    }

    @Override
    @Transactional
    public List<Work> listAllReleaseWorkByUserIdAndIdentity(int userId, int identity){
        return workDao.listAllReleaseWorkByUserIdAndIdentity(userId, identity);
    }

}
