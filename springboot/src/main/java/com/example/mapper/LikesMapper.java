package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.Likes;
import org.apache.ibatis.annotations.Param;

/**
 * @author snail
 * @version 1.0
 * @project AI-learning
 * @description
 * @date 2024/3/14 13:54:04
 */


public interface LikesMapper extends BaseMapper<Likes> {

    void M_insert(Likes likes);

    Likes selectUserLikes(Likes likes);

    void M_deleteById(Integer id);

    int selectByFidAndModule(@Param("fid") Integer fid, @Param("module") String module);
}

