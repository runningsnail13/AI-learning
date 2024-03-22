package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.User;

import java.util.List;

/**
 * @author snail
 * @version 1.0
 * @project AI-learning
 * @description userDao
 * @date 2024/3/7 20:53:59
 */
public interface UserMapper extends BaseMapper<User> {
    void M_insert(User user);

    User selectByUsername(String username);

    void deleteById(Integer id);

    void M_updateById(User user);

    User selectById(Integer id);

    List<User> selectAll(User user);
}
