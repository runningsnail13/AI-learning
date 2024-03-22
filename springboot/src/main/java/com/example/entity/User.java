package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Getter;
import lombok.Setter;

/**
 * @author snail
 * @version 1.0
 * @project AI-learning
 * @description 普通用户
 * @date 2024/3/7 20:38:33
 */
@Getter
@Setter
public class User extends Account{
    private Integer id;
    private String username;
    private String password;
    private String name;
    private String avatar;
    private String role;
    private String sex;
    private String phone;
    private String email;
    private String info;
    private String birth;
    private String occupation;

    /**
     * 额外信息
     */
    @TableField(exist = false)
    private Integer blogCount;
    @TableField(exist = false)
    private Integer likesCount;
    @TableField(exist = false)
    private Integer collectCount;

}
