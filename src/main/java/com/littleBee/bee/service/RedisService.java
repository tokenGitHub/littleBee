package com.littleBee.bee.service;

public interface RedisService {
    void saveEmailVerificationCode(String mail, String verification);
    String getEmailVerificationCode(String mail);
    void setUserLoginCode(String userName, String loginCode);
    String getUserLoginCode(String userName);
}
