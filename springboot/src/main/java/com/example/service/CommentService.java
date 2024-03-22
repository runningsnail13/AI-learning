package com.example.service;

/**
 * @author snail
 * @version 1.0
 * @project AI-learning
 * @description
 * @date 2024/3/15 11:42:45
 */
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.entity.Comment;
import com.example.mapper.CommentMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 业务处理
 **/
@Service
public class CommentService extends ServiceImpl<CommentMapper, Comment> {

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
        commentMapper.M_insert(comment);//插入数据拿到主键id,把自己的id当作RootId(因为是一级评论)
        // commentMapper.insert(comment);这行代码会修改传入的comment对象
        // 这个行为由 MyBatis 的useGeneratedKeys="true"设置和keyProperty="id" 说明指引
        // keyProperty="id"指的是插入数据库记录后，自动生成的键将被设置到comment对象的id属性上
        if(comment.getRootId() == null) {//如果是一个回复的评论，这里不需要将父id更新为它自己的id，因为它有父评论
            comment.setRootId(comment.getId());
            commentMapper.updateById(comment);
        }
    }

    /**
     * 删除,如果仅仅是子评论则删除它自己，如果是父级评论则删除它和它的子评论
     */
    public void deleteById(Integer id) {
        Comment comment = getById(id);//先把要删除的评论查出来
        if(comment.getRootId().equals(id)){ //是否为根评论
            Map<String, Object> columnMap = new HashMap<>();
            columnMap.put("root_Id",id);
            removeByMap(columnMap);//rootId为此id的全部删除，其中包括它自己
        }
        else {
            commentMapper.deleteById(id);//否则仅仅删除它自己
        }
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void M_updateById(Comment comment) {
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
        List<Comment> commentList = commentMapper.selectForUser(comment);//拿到一级评论(找pid等于null的评论)
        for (Comment c : commentList) {  // 查询回复列表
            Comment param = new Comment();
            param.setRootId(c.getId());
            List<Comment> children = this.selectAll(param);//根据rootId找到一级评论下的次级评论，放到一级评论的children里
            //这里有两次左连接user表，第一次根据userId查userName,第二次根据pid查回复的谁
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

    /**
     * 关联删除，用户销户时进行操作
     */
    public Boolean removeByUserId(Integer userId){
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("user_id",userId);
        return removeByMap(columnMap);
    }
    /**
     * 关联删除，帖子/赛事被删除时进行操作
     */
    public Boolean removeByFid(Integer fid,String module){ //要加板块
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("fid",fid);
        columnMap.put("module",module);//匹配板块
        return removeByMap(columnMap);
    }
}
