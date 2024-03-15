package com.example.common.enums;

/**
 * @author snail
 * @version 1.0
 * @project AI-learning
 * @description 可点赞的模块
 * @date 2024/3/14 17:17:03
 */
public enum LikesModuleEnum {
    BLOG("博客"),
    ACTIVITY("活动");

    private String value;

    public String getValue() {
        return value;
    }

    LikesModuleEnum(String value) {
        this.value = value;
    }
}
