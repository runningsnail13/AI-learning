package com.example.service;

import com.example.entity.Account;
import com.example.entity.Likes;
import com.example.mapper.LikesMapper;
import com.example.utils.TokenUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author snail
 * @version 1.0
 * @project AI-learning
 * @description 点赞service
 * @date 2024/3/14 13:53:15
 */
@Service
public class LikesService {

    @Resource
    LikesMapper likesMapper;

    /**
     * 点赞设置，如果有就删除，如果没有就新增
     * @param likes
     */
    public void set(Likes likes) {
        Account currentUser = TokenUtils.getCurrentUser();
        likes.setUserId(currentUser.getId());
        Likes dblLikes = likesMapper.selectUserLikes(likes);
        if (dblLikes == null) {
            likesMapper.insert(likes);
        } else {
            likesMapper.deleteById(dblLikes.getId());
        }
    }

    /**
     * 查询当前用户是否点过赞
     */
    public Likes selectUserLikes(Integer fid, String module) {
        Account currentUser = TokenUtils.getCurrentUser();
        Likes likes = new Likes();
        likes.setUserId(currentUser.getId());
        likes.setFid(fid);
        likes.setModule(module);
        return likesMapper.selectUserLikes(likes);
    }

    /**
     * 根据id和事物类型查询点赞数量
     * @param fid
     * @param module
     * @return
     */
    public int selectByFidAndModule(Integer fid, String module) {
        return likesMapper.selectByFidAndModule(fid, module);
    }

}

