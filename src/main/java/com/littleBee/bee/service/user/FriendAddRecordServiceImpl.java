package com.littleBee.bee.service.user;

import com.littleBee.bee.dao.FriendAddRecordDao;
import com.littleBee.bee.dao.FriendDao;
import com.littleBee.bee.domain.Friend;
import com.littleBee.bee.domain.FriendAddRecord;
import com.littleBee.bee.dto.FriendRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FriendAddRecordServiceImpl implements FriendAddRecordService{

    @Autowired
    private FriendAddRecordDao friendAddRecordDao;

    @Autowired
    private FriendDao friendDao;

    @Override
    @Transactional
    public void saveRecord(FriendAddRecord record){
        friendAddRecordDao.insertRecord(record);
    }

    @Override
    @Transactional
    public FriendAddRecord selectFriendAddRecordById(int id){
        List<FriendAddRecord> list = friendAddRecordDao.selectFriendAddRecordById(id);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
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

    @Override
    @Transactional
    public List<FriendRecord> listFriendAddRecordByUserId(int userId){
        return friendAddRecordDao.listFriendRecordByUserId(userId);
    }

}
