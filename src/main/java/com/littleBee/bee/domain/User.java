package com.littleBee.bee.domain;

import lombok.Data;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Data
@EntityScan
public class User {
    private Integer id;
    private String userName;
    private String password;
    private String email;
    private String realName;
    private String tele;
    private int sex;
    private int identity;
    private String companyName;
    private String industry;
    private String companyIntroduce;
}
