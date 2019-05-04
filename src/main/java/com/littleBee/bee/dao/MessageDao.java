package com.littleBee.bee.dao;

import com.littleBee.bee.domain.Message;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MessageDao {
    List<Message> listUserChatMessage(int userId, int targetUserId);
}
