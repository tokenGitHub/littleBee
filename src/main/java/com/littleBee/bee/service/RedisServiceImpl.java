package com.littleBee.bee.service;

import com.littleBee.bee.utills.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RedisServiceImpl implements RedisService{
    @Autowired
    private RedisUtils<String> stringRedisUtils;

    private final String LOGIN_CODE = "LOGIN_CODE_";
    private final String VERIFICATION_CODE = "VERIFICATION_CODE_";

    @Override
    public void saveEmailVerificationCode(String mail, String verification){
        stringRedisUtils.setValue(VERIFICATION_CODE + mail, verification, 60L);
    }

    @Override
    public String getEmailVerificationCode(String mail){
        String value = null;
        try {
            value = stringRedisUtils.getValue(VERIFICATION_CODE + mail);
        }catch (Exception e){
            e.printStackTrace();
        }
        return value;
    }

    @Override
    public void setUserLoginCode(String userName, String loginCode){
        try {
            stringRedisUtils.setValue(LOGIN_CODE + userName, loginCode, 60 * 30);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public String getUserLoginCode(String userName){
        return stringRedisUtils.getValue(LOGIN_CODE + userName);
    }
}
