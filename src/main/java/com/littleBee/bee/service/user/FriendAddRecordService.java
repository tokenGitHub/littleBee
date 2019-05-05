package com.littleBee.bee.service.user;

import com.littleBee.bee.domain.FriendAddRecord;
import com.littleBee.bee.dto.FriendRecord;

import java.util.List;

public interface FriendAddRecordService {
    void saveRecord(FriendAddRecord record);
    FriendAddRecord selectFriendAddRecordById(int id);
    void saveFriend(FriendAddRecord record, int agreeOrNot);
    List<FriendRecord> listFriendAddRecordByUserId(int userId);
}
