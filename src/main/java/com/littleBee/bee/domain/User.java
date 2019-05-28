package com.littleBee.bee.domain;

import lombok.Data;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.util.Date;

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
    private String file;
    private Date createDate;
    private int status;
}
