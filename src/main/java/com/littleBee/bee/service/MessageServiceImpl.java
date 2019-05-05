package com.littleBee.bee.service;

import com.littleBee.bee.dao.MessageDao;
import com.littleBee.bee.domain.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    MessageDao messageDao;

    @Override
    @Transactional
    public List<Message> listUserChatMessage(int userId, int targetUserId){
        return messageDao.listUserChatMessage(userId, targetUserId);
    }

    @Override
    @Transactional
    public void sendMessage(int sendUserId, int targetUserId, String context){
        Date date = new Date(new java.util.Date().getTime());
        messageDao.insertMessage(sendUserId, targetUserId, context, date);
    }

}
