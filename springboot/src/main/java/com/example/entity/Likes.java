package com.example.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author snail
 * @version 1.0
 * @project AI-learning
 * @description 点赞
 * @date 2024/3/14 13:44:08
 */
@Getter
@Setter
public class Likes {
    private Integer id;
    private Integer fid;
    private Integer userId;
    private String module;
}
