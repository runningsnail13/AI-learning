package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * @author snail
 * @version 1.0
 * @project AI-learning
 * @description 帖子实体类
 * @date 2024/3/10 11:29:34
 */
@Getter
@Setter
public class Blog {

    /**
     * ID
     */
    private Integer id;
    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String content;
    /**
     * 简介
     */
    private String descr;
    /**
     * 封面
     */
    private String cover;
    /**
     * 标签
     */
    private String tags;
    /**
     * 发布人ID
     */
    private Integer userId;
    /**
     * 发布日期
     */
    private String date;
    /**
     * 浏览量
     */
    private Integer readCount;
    private Integer categoryId;

//      实体类外的附加信息

    /**
     * 帖子名称
     */
    @TableField(exist = false)
    private String categoryName;
    /**
     * 发布者姓名
     */

    @TableField(exist = false)
    private String userName;
    /**
     * 发布者
     */
    @TableField(exist = false)
    private User user;

    @TableField(exist = false)
    private Integer likesCount;

    /**
     * 用于判断是否被当前用户点赞
     */
    @TableField(exist = false)
    private  Boolean userLike;


    @TableField(exist = false)
    private Integer collectCount;

    /**
     * 用于判断是否被当前用户收藏
     */
    @TableField(exist = false)
    private  Boolean userCollect;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Blog)) return false;
        Blog blog = (Blog) o;
        return Objects.equals(getId(), blog.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

}
