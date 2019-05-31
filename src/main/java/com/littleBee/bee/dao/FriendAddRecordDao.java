package com.littleBee.bee.dao;

import com.littleBee.bee.domain.FriendAddRecord;
import com.littleBee.bee.dto.FriendRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FriendAddRecordDao {
    void insertRecord(@Param("record") FriendAddRecord record);
    List<FriendAddRecord> selectFriendAddRecordById(@Param("id") int id);
    void updateRecord(@Param("record") FriendAddRecord record);
    List<FriendRecord> listFriendRecordByUserId(@Param("userId") int userId);
}
