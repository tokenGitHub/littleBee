package com.littleBee.bee.domain;

import lombok.Data;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.sql.Date;

@Data
@EntityScan
public class User {
    private Integer id;
    private String userName;
    private String password;
    private String email;
    private Date birthday;
    private Date createTime;
    private String degree;
    private String address;
    private String realName;
}
