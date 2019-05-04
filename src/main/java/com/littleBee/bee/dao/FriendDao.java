package com.littleBee.bee.dao;

import com.littleBee.bee.domain.Friend;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FriendDao {
    void insertFriend(@Param("friend") Friend friend);
}
