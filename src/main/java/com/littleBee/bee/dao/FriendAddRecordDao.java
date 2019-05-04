package com.littleBee.bee.dao;

import com.littleBee.bee.domain.FriendAddRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FriendAddRecordDao {
    void insertRecord(@Param("record")FriendAddRecord record);
    FriendAddRecord selectFriendAddRecordById(@Param("id") int id);
    void updateRecord(@Param("record") FriendAddRecord record);
}
