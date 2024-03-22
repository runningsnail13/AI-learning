package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Getter;
import lombok.Setter;

/**
 * @author snail
 * @version 1.0
 * @project AI-learning
 * @description 比赛活动信息
 * @date 2024/3/11 18:18:38
 */
@Getter
@Setter
public class Activity {

    /** ID */
    private Integer id;
    /** 活动名称 */
    private String name;
    /** 活动简介 */
    private String descr;
    /** 开始时间 */
    private String start;
    /** 结束时间 */
    private String end;
    /** 活动形式 */
    private String form;
    /** 活动地址 */
    private String address;
    /** 主办方 */
    private String host;
    /** 浏览量 */
    private Integer readCount;
    private String content;
    private String cover;
    /**
     * 实体类外的附加信息
     */
    @TableField(exist = false)
    private Boolean isEnd;//比赛是否结束

    @TableField(exist = false)
    private Boolean isSign;//报名状态

    @TableField(exist = false)
    private Integer likesCount;

    @TableField(exist = false)
    private Integer collectCount;

    private Boolean isLike;
    private Boolean isCollect;
    private Integer userId;


}
