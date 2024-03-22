package com.example.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.ActivitySign;
import org.apache.ibatis.annotations.*;

import java.util.List;
/**
 * @author snail
 * @version 1.0
 * @project AI-learning
 * @description
 * @date 2024/3/17 11:00:43
 */

@Mapper
public interface ActivitySignMapper extends BaseMapper<ActivitySign> {

    @Insert("insert into activity_sign ( activity_id, user_id, time ) values ( #{activityId}, #{userId}, #{time})")
    void M_insert(ActivitySign activitySign);

    @Select("select * from activity_sign where activity_id = #{activityId} and user_id = #{userId}")
    ActivitySign selectByActivityIdAndUserId(@Param("activityId") Integer actId, @Param("userId") Integer userId);

    @Select("select activity_sign.*, activity.name as activityName, user.name as userName from activity_sign " +
            "left join activity on activity_sign.activity_id = activity.id " +
            "left join user on activity_sign.user_id = user.id " +
            "order by id desc")
    List<ActivitySign> selectAll(ActivitySign activitySign);

    @Delete("delete from activity_sign where id = #{id}")
    void deleteById(Integer id);
}

