package com.littleBee.bee.dao;

import com.littleBee.bee.domain.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Date;
import java.util.List;

@Mapper
public interface MessageDao {
    List<Message> listUserChatMessage(@Param("userId") int userId,@Param("targetUserId") int targetUserId);
    void insertMessage(@Param("sendUserId") int sendUserId, @Param("targetUserId") int targetUserId, @Param("context") String context, @Param("date") Date date);
}
