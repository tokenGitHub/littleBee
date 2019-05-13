package com.littleBee.bee.domain;

import lombok.Data;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Data
@EntityScan
public class Work {
    private int id;
    private String workName;
    private int userId;
    private int identity;
    private int needPerson;
    private String wages;
    private String workTime;
    private String city;
    private String duty;
    private String requirement;
    private String contacts;
    private String tele;
    private String remarks;
    private int status;
}
