package com.littleBee.bee.service;

import com.littleBee.bee.dao.FriendAddRecordDao;
import com.littleBee.bee.dao.FriendDao;
import com.littleBee.bee.domain.Friend;
import com.littleBee.bee.domain.FriendAddRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FriendAddRecordServiceImpl implements FriendAddRecordService{

    @Autowired
    FriendAddRecordDao friendAddRecordDao;

    @Autowired
    FriendDao friendDao;

    @Override
    @Transactional
    public void saveRecord(FriendAddRecord record){
        friendAddRecordDao.insertRecord(record);
    }

    @Override
    @Transactional
    public FriendAddRecord selectFriendAddRecordById(int id){
        return friendAddRecordDao.selectFriendAddRecordById(id);
    }

    @Override
    @Transactional
    public void saveFriend(FriendAddRecord record, int agreeOrNot){
        record.setStatus(agreeOrNot);
        if(agreeOrNot == 1){
            Friend friend = new Friend();
            friend.setUserId(record.getUserId());
            friend.setFriendUserId(record.getFriendUserId());
            friendDao.insertFriend(friend);
            friend.setFriendUserId(record.getUserId());
            friend.setUserId(record.getFriendUserId());
            friendDao.insertFriend(friend);
        }
        friendAddRecordDao.updateRecord(record);
    }
}