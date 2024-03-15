package com.example.mapper;

import com.example.entity.Likes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author snail
 * @version 1.0
 * @project AI-learning
 * @description
 * @date 2024/3/14 13:54:04
 */


public interface LikesMapper {

    void insert(Likes likes);

    Likes selectUserLikes(Likes likes);

    void deleteById(Integer id);

    int selectByFidAndModule(@Param("fid") Integer fid, @Param("module") String module);
}

