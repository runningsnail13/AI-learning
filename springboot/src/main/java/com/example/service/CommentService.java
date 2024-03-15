package com.example.service;

/**
 * @author snail
 * @version 1.0
 * @project AI-learning
 * @description
 * @date 2024/3/15 11:42:45
 */
import cn.hutool.core.date.DateUtil;
import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.entity.Comment;
import com.example.mapper.CommentMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 业务处理
 **/
@Service
public class CommentService {

    @Resource
    private CommentMapper commentMapper;

    /**
     * 新增
     */
    public void add(Comment comment) {
        Account currentUser = TokenUtils.getCurrentUser();
        if(RoleEnum.USER.name().equals(currentUser.getRole())){
            comment.setUserId(currentUser.getId());
        }
        comment.setTime(DateUtil.now());
        commentMapper.insert(comment);//插入数据拿到主键id,把自己的id当作RootId(因为是一级评论)
        // commentMapper.insert(comment);这行代码会修改传入的comment对象
        // 这个行为由 MyBatis 的useGeneratedKeys="true"设置和keyProperty="id" 说明指引
        // keyProperty="id"指的是插入数据库记录后，自动生成的键将被设置到comment对象的id属性上
        if(comment.getRootId() == null) {//如果是一个回复的评论，这里不需要将父id更新为它自己的id，因为它有父评论
            comment.setRootId(comment.getId());
            commentMapper.updateById(comment);
        }
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        commentMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            commentMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(Comment comment) {
        commentMapper.updateById(comment);
    }

    /**
     * 根据ID查询
     */
    public Comment selectById(Integer id) {
        return commentMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<Comment> selectAll(Comment comment) {
        return commentMapper.selectAll(comment);
    }

    /**
     *查一级和多级评论
     */
    public List<Comment> selectForUser(Comment comment) {
        List<Comment> commentList = commentMapper.selectForUser(comment);
        for (Comment c : commentList) {  // 查询回复列表
            Comment param = new Comment();
            param.setRootId(c.getId());
            List<Comment> children = this.selectAll(param);
            children = children.stream().filter(child -> !child.getId().equals(c.getId())).collect(Collectors.toList());  // 排除当前查询结果里最外层节点
            c.setChildren(children);
        }
        return commentList;
    }

    /**
     * 分页查询
     */
    public PageInfo<Comment> selectPage(Comment comment, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Comment> list = commentMapper.selectAll(comment);
        return PageInfo.of(list);
    }

    public Integer selectCount(Integer fid, String module) {
        return commentMapper.selectCount(fid,module);
    }
}
