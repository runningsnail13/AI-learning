package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Getter;
import lombok.Setter;

/**
 * @author snail
 * @version 1.0
 * @project AI-learning
 * @description 比赛报名信息
 * @date 2024/3/17 10:54:14
 */
@Getter
@Setter
public class ActivitySign {
    private Integer id;
    private Integer activityId;
    private Integer userId;
    private String time;
    /**
     * 实体类外的附加信息
     */
    @TableField(exist = false)
    private String activityName;

    @TableField(exist = false)
    private String userName;
}
