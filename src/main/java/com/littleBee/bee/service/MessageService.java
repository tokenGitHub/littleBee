package com.littleBee.bee.service;

import com.littleBee.bee.domain.Message;

import java.util.List;

public interface MessageService {
    List<Message> listUserChatMessage(int userId, int targetUserId);
    void sendMessage(int sendUserId, int targetUserId, String context);
}
