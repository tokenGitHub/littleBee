package com.littleBee.bee.dto;

import lombok.Data;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Data
@EntityScan
public class ListAllReleaseWorkData {
    private int userId;
    private int identity;
}
