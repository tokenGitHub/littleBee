package com.littleBee.bee.service.user;

public interface EmailService {
    String sendSimpleMail(String toAddress) throws Exception;
    String sendSimpleMail(String toAddress, String context) throws Exception;
}
