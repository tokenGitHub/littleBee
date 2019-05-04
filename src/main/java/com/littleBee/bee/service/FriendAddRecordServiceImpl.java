package com.littleBee.bee.service;

import com.littleBee.bee.dao.FriendAddRecordDao;
import com.littleBee.bee.domain.FriendAddRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FriendAddRecordServiceImpl implements FriendAddRecordService{

    @Autowired
    FriendAddRecordDao friendAddRecordDao;

    @Override
    @Transactional
    public void saveRecord(FriendAddRecord record){
        friendAddRecordDao.insertRecord(record);
    }

}
