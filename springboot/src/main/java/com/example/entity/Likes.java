package com.example.entity;

/**
 * @author snail
 * @version 1.0
 * @project AI-learning
 * @description 点赞
 * @date 2024/3/14 13:44:08
 */
public class Likes {
    private Integer id;
    private Integer fid;
    private Integer userId;
    private String module;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }
}
