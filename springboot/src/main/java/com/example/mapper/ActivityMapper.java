package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.Activity;
import com.example.entity.ActivitySign;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;
/**
 * @author snail
 * @version 1.0
 * @project AI-learning
 * @description
 * @date 2024/3/11 18:22:48
 */

public interface ActivityMapper extends BaseMapper<Activity> {

    /**
     * 新增
     */
    int insert(Activity activity);

    /**
     * 删除
     */
    int deleteById(Integer id);

    /**
     * 修改
     */
    int updateById(Activity activity);

    /**
     * 根据ID查询
     */
    Activity selectById(Integer id);

    /**
     * 查询所有
     */
    List<Activity> selectAll(Activity activity);

    @Update("update activity set read_count = read_count + 1 where id = #{activityId}")
    void updateReadCount(Integer activityId);

    List<Activity> selectUser(Activity activity);

    List<Activity> selectLike(Activity activity);

    List<Activity> selectCollect(Activity activity);

    List<Activity> selectComment(Activity activity);
}
