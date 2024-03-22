package com.example.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.Account;
import com.example.entity.Collect;
import com.example.entity.Comment;
import com.example.mapper.CollectMapper;
import com.example.mapper.CommentMapper;
import com.example.utils.TokenUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author snail
 * @version 1.0
 * @project AI-learning
 * @description 收藏service
 * @date 2024/3/14 13:53:15
 */
@Service
public class CollectService extends ServiceImpl<CollectMapper, Collect> {

    @Resource
    CollectMapper collectMapper;

    public void set(Collect collect) {
        Account currentUser = TokenUtils.getCurrentUser();
        collect.setUserId(currentUser.getId());
        Collect dblCollect = collectMapper.selectUserCollect(collect);
        if (dblCollect == null) {
            collectMapper.M_insert(collect);
        } else {
            collectMapper.deleteById(dblCollect.getId());
        }
    }

    /**
     * 查询当前用户是否收藏过
     */
    public Collect selectUserCollect(Integer fid, String module) {
        Account currentUser = TokenUtils.getCurrentUser();
        Collect collect = new Collect();
        collect.setUserId(currentUser.getId());
        collect.setFid(fid);
        collect.setModule(module);
        return collectMapper.selectUserCollect(collect);
    }

    public int selectByFidAndModule(Integer fid, String module) {
        return collectMapper.selectByFidAndModule(fid, module);
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
    public Boolean removeByFid(Integer fid,String module){ //要加板块
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("fid",fid);
        columnMap.put("module",module);//匹配板块
        return removeByMap(columnMap);
    }

}

