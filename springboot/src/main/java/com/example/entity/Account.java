package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Getter;
import lombok.Setter;

/**
 * 角色用户父类
 */
@Getter
@Setter
public class Account {
    private Integer id;
    /** 用户名 */
    private String username;
    /** 名称 */
    private String name;
    /** 密码 */
    private String password;
    /** 角色标识 */
    private String role;

    private String newPassword;
    /** 头像 */
    private String avatar;

    private String token;

    private String occupation;

}
