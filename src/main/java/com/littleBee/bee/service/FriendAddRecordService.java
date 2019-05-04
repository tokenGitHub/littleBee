package com.littleBee.bee.service;

import com.littleBee.bee.domain.FriendAddRecord;

public interface FriendAddRecordService {
    void saveRecord(FriendAddRecord record);
    FriendAddRecord selectFriendAddRecordById(int id);
    void saveFriend(FriendAddRecord record, int agreeOrNot);
}
