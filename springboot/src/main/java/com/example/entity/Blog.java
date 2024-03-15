package com.example.entity;

import java.util.Objects;

/**
 * @author snail
 * @version 1.0
 * @project AI-learning
 * @description 帖子实体类
 * @date 2024/3/10 11:29:34
 */
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
    /**
     * 帖子名称
     */
    private String categoryName;
    /**
     * 发布者姓名
     */
    private String userName;
    /**
     * 发布者
     */
    private User user;
    private Integer likesCount;

    /**
     * 用于判断是否被当前用户点赞
     */
    private  Boolean userLike;

    private Integer collectCount;

    public Integer getCollectCount() {
        return collectCount;
    }

    public void setCollectCount(Integer collectCount) {
        this.collectCount = collectCount;
    }

    public Boolean getUserCollect() {
        return userCollect;
    }

    public void setUserCollect(Boolean userCollect) {
        this.userCollect = userCollect;
    }

    /**
     * 用于判断是否被当前用户收藏
     */
    private  Boolean userCollect;
    public Integer getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(Integer likesCount) {
        this.likesCount = likesCount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getReadCount() {
        return readCount;
    }

    public void setReadCount(Integer readCount) {
        this.readCount = readCount;
    }

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

    public Boolean getUserLike() {
        return userLike;
    }

    public void setUserLike(Boolean userLike) {
        this.userLike = userLike;
    }
}
