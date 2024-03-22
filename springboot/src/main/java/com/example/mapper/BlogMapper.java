package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.Blog;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author snail
 * @version 1.0
 * @project AI-learning
 * @description
 * @date 2024/3/10 11:33:33
 */
public interface BlogMapper extends BaseMapper<Blog> {

    /**
     * 新增
     */
    int insert(Blog blog);

    /**
     * 删除
     */
    int deleteById(Integer id);

    /**
     * 修改
     */
    int updateById(Blog blog);

    /**
     * 根据ID查询
     */
    Blog M_selectById(Integer id);

    /**
     * 查询所有
     */
    List<Blog> selectAll(Blog blog);

    /**
     *  根据用户id查询帖子
     */
    @Select("select * from blog  where user_id = #{userId}")
    List<Blog> selectUserBlog(Integer userId);

    /**
     *  更新阅读量
     */
    @Update("update blog set read_count = read_count + 1 where id = #{blogId} ")
    void updateReadCount(Integer blogId);

    /**
     * 根据用户点赞查询
     */
    List<Blog> selectLike(Blog blog);

    List<Blog> selectCollect(Blog blog);

    List<Blog> selectComment(Blog blog);
}
