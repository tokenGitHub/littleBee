package com.littleBee.bee.service.work;

import com.littleBee.bee.dao.WorkDao;
import com.littleBee.bee.dao.WorkRecordDao;
import com.littleBee.bee.domain.User;
import com.littleBee.bee.domain.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WorkServiceImpl implements WorkService {

    @Autowired
    private WorkDao workDao;

    @Autowired
    private WorkRecordDao workRecordDao;

    @Override
    @Transactional
    public List<Work> listWorkByPositionAndIdentity(String city, int identity){
        return workDao.listWorkByPositionAndIdentity(city, identity);
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

    @Override
    @Transactional
    public List<User> listUserByWorkId(int workId){
        return workDao.listUserByWorkId(workId);
    }

    @Override
    @Transactional
    public List<Work> listWorkByWorkName(String workName){
        return workDao.listWorkByWorkName(workName);
    }

    @Override
    @Transactional
    public void examine(int workId, int status){
        workDao.examine(workId,status);
    }

    @Override
    @Transactional
    public void insertWorkRecord(int userId,int workId){
        workRecordDao.insertWorkRecord(userId, workId);
        int actualNumber = workRecordDao.selectActualByWorkId(workId);
        Work work = workDao.selectWorkByWorkId(workId);

        if(actualNumber == work.getNeedPerson()){
            workDao.examine(workId, 3);
        }
    }

}
