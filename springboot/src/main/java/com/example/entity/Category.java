package com.example.entity;

/**
 * @author snail
 * @version 1.0
 * @project AI-learning
 * @description 帖子分类
 * @date 2024/3/9 10:18:45
 */
public class Category {
    /**
     * ID
     */
    private Integer id;
    /**
     * 分类名称
     */
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
