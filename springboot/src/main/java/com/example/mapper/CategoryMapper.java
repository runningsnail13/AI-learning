package com.example.mapper;

import com.example.entity.Category;
import org.apache.ibatis.annotations.Insert;

import java.util.List;

/**
 * @author snail
 * @version 1.0
 * @project AI-learning
 * @description
 * @date 2024/3/9 10:36:25
 */
public interface CategoryMapper {

    void insert(Category category);

    void deleteById(Integer id);

    void updateById(Category category);

    Category selectById(Integer id);

    List<Category> selectAll(Category category);
}
