package com.example.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.Account;
import com.example.entity.Likes;
import com.example.entity.Notice;
import com.example.mapper.LikesMapper;
import com.example.mapper.NoticeMapper;
import com.example.utils.TokenUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author snail
 * @version 1.0
 * @project AI-learning
 * @description 点赞service
 * @date 2024/3/14 13:53:15
 */
@Service
public class LikesService extends ServiceImpl<LikesMapper, Likes> {

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
            likesMapper.M_insert(likes);
        } else {
            likesMapper.M_deleteById(dblLikes.getId());
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
     * 根据作品id和作品类型查询点赞数量
     * @param fid
     * @param module
     * @return
     */
    public int selectByFidAndModule(Integer fid, String module) {
        return likesMapper.selectByFidAndModule(fid, module);
    }

    /**
     * 关联删除，用户销户时进行操作
     */
    public Boolean removeByUserId(Integer userId){
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("user_id",userId);
        return removeByMap(columnMap);
    }
    /**
     * 关联删除，帖子被删除时进行操作
     */
    public Boolean removeByFid(Integer fid,String module){
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("fid",fid);
        columnMap.put("module",module);//匹配板块
        return removeByMap(columnMap);
    }

}

