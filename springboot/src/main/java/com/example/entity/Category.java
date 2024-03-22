package com.example.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author snail
 * @version 1.0
 * @project AI-learning
 * @description 帖子分类
 * @date 2024/3/9 10:18:45
 */
@Getter
@Setter
public class Category {
    /**
     * ID
     */
    private Integer id;
    /**
     * 分类名称
     */
    private String name;
}
