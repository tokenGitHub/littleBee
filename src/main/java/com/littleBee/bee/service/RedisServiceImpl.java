package com.littleBee.bee.service;

import com.littleBee.bee.utills.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RedisServiceImpl implements RedisService{
    @Autowired
    private RedisUtil<String> redisUtil;

    private final String LOGIN_CODE = "LOGIN_CODE_";
    private final String VERIFICATION_CODE = "VERIFICATION_CODE_";

    @Override
    public void saveEmailVerificationCode(String mail, String verification){
        redisUtil.set(VERIFICATION_CODE + mail, verification, 60L);
    }

    @Override
    public String getEmailVerificationCode(String mail){
        String value = null;
        try {
            value = (String)redisUtil.get(VERIFICATION_CODE + mail);
        }catch (Exception e){
            e.printStackTrace();
        }
        return value;
    }

    @Override
    public void setUserLoginCode(String userName, String loginCode){
        try {
            redisUtil.set(LOGIN_CODE + userName, loginCode, 60 * 30);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public String getUserLoginCode(String userName){
        return (String) redisUtil.get(LOGIN_CODE + userName);
    }
}
