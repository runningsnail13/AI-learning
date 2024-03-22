package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.Category;

import java.util.List;

/**
 * @author snail
 * @version 1.0
 * @project AI-learning
 * @description
 * @date 2024/3/9 10:36:25
 */
public interface CategoryMapper extends BaseMapper<Category> {

    void M_insert(Category category);

    void deleteById(Integer id);

    void M_updateById(Category category);

    Category selectById(Integer id);

    List<Category> selectAll(Category category);
}
