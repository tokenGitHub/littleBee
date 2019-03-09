package com.littleBee.bee.service;

import com.littleBee.bee.utills.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RedisServiceImpl implements RedisService{
    @Autowired
    private RedisUtils<String> stringRedisUtils;

    @Override
    public void saveEmailVerificationCode(String mail, String verification){
        stringRedisUtils.setValue(mail, verification, 60L);
    }

    @Override
    public String getEmailVerificationCode(String mail){
        String value = null;
        try {
            value = stringRedisUtils.getValue(mail);
        }catch (Exception e){
            e.printStackTrace();
        }
        return value;
    }
}
