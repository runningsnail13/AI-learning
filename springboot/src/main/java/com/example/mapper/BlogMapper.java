package com.example.mapper;

import com.example.entity.Blog;

import java.util.List;

/**
 * @author snail
 * @version 1.0
 * @project AI-learning
 * @description
 * @date 2024/3/10 11:33:33
 */
public interface BlogMapper {

    /**
     * 新增
     */
    int insert(Blog blog);

    /**
     * 删除
     */
    int deleteById(Integer id);

    /**
     * 修改
     */
    int updateById(Blog blog);

    /**
     * 根据ID查询
     */
    Blog selectById(Integer id);

    /**
     * 查询所有
     */
    List<Blog> selectAll(Blog blog);

}