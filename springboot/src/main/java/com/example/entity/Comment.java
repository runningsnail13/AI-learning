package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author snail
 * @version 1.0
 * @project AI-learning
 * @description 评论
 * @date 2024/3/15 11:30:04
 */
@Getter
@Setter
public class Comment {

    /** ID */
    @TableId(value = "id",type= IdType.AUTO)
    private Integer id;
    /** 内容 */
    private String content;
    /** 评论人 */
    private Integer userId;

    /** 父级ID */
    private Integer pid;
    /** 根节点ID */
    private Integer rootId;
    /** 评论时间 */
    private String time;

    /** 博客/活动ID */
    private Integer fid;
    /**
     * 板块分类
     */
    private String module;

    /**
     * 实体类外的附加信息
     */
    @TableField(exist = false)
    private String userName;//评论人名字

    @TableField(exist = false)
    private  String avatar;//头像

    @TableField(exist = false)
    private List<Comment> children;//所有以该评论为根的子评论

    @TableField(exist = false)
    private String replyUser;//这条评论回复的谁，只需要找到它的pid的那条评论的评论人

}



