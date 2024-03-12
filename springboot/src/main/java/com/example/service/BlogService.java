package com.example.service;

import cn.hutool.core.date.DateUtil;
import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.entity.Blog;
import com.example.mapper.BlogMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author snail
 * @version 1.0
 * @project AI-learning
 * @description
 * @date 2024/3/10 11:31:50
 */
@Service
public class BlogService {
    @Resource
    private BlogMapper blogMapper;

    public void add(Blog blog) {
        blog.setDate(DateUtil.today());
        Account currentUser = TokenUtils.getCurrentUser();
        if(RoleEnum.USER.name().equals(currentUser.getRole())){
            blog.setUserId(currentUser.getId());
        }
        blogMapper.insert(blog);
    }

    public void deleteById(Integer id) {
        blogMapper.deleteById(id);
    }

    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            blogMapper.deleteById(id);
        }
    }

    public void updateById(Blog blog) {
        blogMapper.updateById(blog);
    }

    public Blog selectById(Integer id) {
        return blogMapper.selectById(id);
    }

    public List<Blog> selectAll(Blog blog) {
        return blogMapper.selectAll(blog);
    }

    public PageInfo<Blog> selectPage(Blog blog, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Blog> list = blogMapper.selectAll(blog);
        return PageInfo.of(list);
    }

    public List<Blog> selectTop(Blog blog) {
        List<Blog> blogList = this.selectAll(null);
        blogList = blogList.stream().sorted((b1,b2)->b2.getReadCount()-b1.getReadCount())
                .limit(10)
                .collect(Collectors.toList());
        return blogList;

    }
}
