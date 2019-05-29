package com.littleBee.bee.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;

@Service
public class EmailServiceImpl implements EmailService{

    @Autowired
    private  JavaMailSender mailSender;
    private  Random random = new Random();

    @Override
    public String sendSimpleMail(String toAddress) {
        SimpleMailMessage message = new SimpleMailMessage();
        String verification = randomStr();
        message.setFrom("littlebeeyd@qq.com");
        message.setTo(toAddress);
        message.setSubject("主题：大象职呗验证");
        message.setText("您的验证码为：" + verification);
        mailSender.send(message);
        return verification;
    }

    @Override
    public String sendSimpleMail(String toAddress , String context) {
        SimpleMailMessage message = new SimpleMailMessage();
        String verification = randomStr();
        message.setFrom("littlebeeyd@qq.com");
        message.setTo(toAddress);
        message.setSubject("主题：大象职呗验证");
        message.setText(context);
        mailSender.send(message);
        return verification;
    }

//  生成随机的验证码
    private String randomStr(){
        String result = "";
        random.setSeed(new Date().getTime());

        for(int i = 0; i < 6 ; i++){
            int flag = random.nextInt(10);
            if(flag % 2 == 1){
                result += (char)(random.nextInt(26) + 'a');
            }else{
                result += random.nextInt(10);
            }
        }
        return result;
    }
}
