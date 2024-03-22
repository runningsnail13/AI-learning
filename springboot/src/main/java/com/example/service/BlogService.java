package com.example.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.enums.LikesModuleEnum;
import com.example.common.enums.RoleEnum;
import com.example.entity.*;
import com.example.mapper.BlogMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author snail
 * @version 1.0
 * @project AI-learning
 * @description
 * @date 2024/3/10 11:31:50
 */
@Service
public class BlogService extends ServiceImpl<BlogMapper, Blog> {
    @Resource
    private BlogMapper blogMapper;

    @Resource
    private UserService userService;

    @Resource
    private LikesService likesService;
    @Resource
    private CollectService collectService;
    @Resource
    private CommentService commentService;

    /**
     * 新增
     * @param blog
     */
    public void add(Blog blog) {
        blog.setDate(DateUtil.today());
        Account currentUser = TokenUtils.getCurrentUser();
        if(RoleEnum.USER.name().equals(currentUser.getRole())){
            blog.setUserId(currentUser.getId());
        }
        blogMapper.insert(blog);
    }

    /**
     * 按id删除 注意关联删除，要删除相关的评论点赞收藏
     * @param id
     */
    public void deleteById(Integer id) {
        likesService.removeByFid(id,"博客");//关联删除点赞
        collectService.removeByFid(id,"博客");
        commentService.removeByFid(id,"博客");
        blogMapper.deleteById(id);
    }

    /**
     * 删除用户时的关联操作，先找到用户发表的所有博客，然后删除
     */
    public void deleteByUserId(Integer userId) {
        Blog blog = new Blog();
        blog.setUserId(userId);
        List<Blog> blogList = selectAll(blog);
        for (Blog b : blogList) {
            deleteById(b.getId());
        }
    }

    /**
     * 批量删除
     * @param ids
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            deleteById(id);
        }
    }

    /**
     * 按id更新或新增
     * @param blog
     */
    public void M_updateById(Blog blog) {
        blogMapper.updateById(blog);
    }

    /**
     * 按id查询 并且把帖子相关的各种信息都加上返回出去
     * @param id
     * @return
     */
    public Blog selectById(Integer id) {
        Blog blog = blogMapper.M_selectById(id);
        User user = userService.selectById(blog.getUserId());//找到作者
        List<Blog> userBlogList=blogMapper.selectUserBlog(user.getId());//找到作者的所有作品
        user.setBlogCount(userBlogList.size());//设置作者的文章数

        int userLikesCount = 0;
        int userCollectCount = 0;
        for (Blog b : userBlogList) {
            Integer fid = b.getId();
            int likesCount = likesService.selectByFidAndModule(fid, LikesModuleEnum.BLOG.getValue());
            userLikesCount += likesCount;

            int collectCount = collectService.selectByFidAndModule(fid, LikesModuleEnum.BLOG.getValue());
            userCollectCount += collectCount;
        }
        user.setLikesCount(userLikesCount);
        user.setCollectCount(userCollectCount);

        blog.setUser(user);//这里把作者信息加上了

        int likesCount = likesService.selectByFidAndModule(id, LikesModuleEnum.BLOG.getValue());//id加上角色查询返回点赞数
        blog.setLikesCount(likesCount);
        Likes userlikes = likesService.selectUserLikes(id, LikesModuleEnum.BLOG.getValue());
        blog.setUserLike(userlikes != null);//没有找到就是false

        int collectCount = collectService.selectByFidAndModule(id, LikesModuleEnum.BLOG.getValue());//id加上角色查询返回收藏数
        blog.setCollectCount(collectCount);
        Collect userCollect = collectService.selectUserCollect(id, LikesModuleEnum.BLOG.getValue());
        blog.setUserCollect(userCollect != null);//没有找到就是false

        //更新博客浏览量 备注：不能在这里更新
//        blog.setReadCount(blog.getReadCount()+ 1 );//调用一次按id查询浏览量就加一
//        this.updateById(blog);
        return blog;
    }

    /**
     * 模糊查询和查询所有
     * @param blog
     * @return
     */
    public List<Blog> selectAll(Blog blog) {
        return blogMapper.selectAll(blog);
    }

    /**
     * 分页查询
     * @param blog
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PageInfo<Blog> selectPage(Blog blog, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Blog> list = blogMapper.selectAll(blog);

        for (Blog b : list) {
            int likesCount = likesService.selectByFidAndModule(b.getId(), LikesModuleEnum.BLOG.getValue());
            b.setLikesCount(likesCount);
        }
        return PageInfo.of(list);
    }

    /**
     * 查询热度榜单
     * @param blog
     * @return
     */
    public List<Blog> selectTop(Blog blog) {
        List<Blog> blogList = this.selectAll(null);
        blogList = blogList.stream().sorted((b1,b2)->b2.getReadCount()-b1.getReadCount())
                .limit(20)
                .collect(Collectors.toList());
        return blogList;

    }

    /**
     * 按标签查询其他帖子
     * @param blogId
     * @return
     */
    public Set<Blog> selectRecommend(Integer blogId) {
        Blog blog = this.selectById(blogId);
        String tags = blog.getTags();
        Set<Blog> blogSet = new HashSet<>();
        if (ObjectUtil.isNotEmpty(tags)) {
            List<Blog> blogList = this.selectAll(null);
            JSONArray tagsArr = JSONUtil.parseArray(tags);
            for (Object tag : tagsArr) {
                // 筛选出包含当前博客标签的其他的博客列表
                Set<Blog> collect = blogList.stream().filter(b -> b.getTags().contains(tag.toString()) && !blogId.equals(b.getId()))
                        .collect(Collectors.toSet());
                blogSet.addAll(collect);
            }
        }
        blogSet = blogSet.stream().limit(5).collect(Collectors.toSet());
        blogSet.forEach(b -> {
            int likesCount = likesService.selectByFidAndModule(b.getId(), LikesModuleEnum.BLOG.getValue());
            b.setLikesCount(likesCount);
        });
        return blogSet;
    }
    /**
     * 单独调用更新阅读量的接口
     */
    public void updateReadCount(Integer blogId) {
        blogMapper.updateReadCount(blogId);
    }

    /**
     * 返回该用户创作的博客的分页
     */
    public PageInfo<Blog> selectUser(Blog blog, Integer pageNum, Integer pageSize) {
        Account currentUser = TokenUtils.getCurrentUser();
        if(RoleEnum.USER.name().equals(currentUser.getRole())){
            blog.setUserId(currentUser.getId());
        }
        return this.selectPage(blog,pageNum,pageSize);
    }

    /**
     * 返回用户喜欢的博客分页
     */
    public PageInfo<Blog> selectLike(Blog blog, Integer pageNum, Integer pageSize) {
        Account currentUser = TokenUtils.getCurrentUser();
        if(RoleEnum.USER.name().equals(currentUser.getRole())){
            blog.setUserId(currentUser.getId());
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Blog> list = blogMapper.selectLike(blog);
        list = list.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        PageInfo<Blog> pageInfo = PageInfo.of(list);
        List<Blog> blogList = pageInfo.getList();
        for (Blog b : blogList) {
            int likesCount = likesService.selectByFidAndModule(b.getId(), LikesModuleEnum.BLOG.getValue());
            b.setLikesCount(likesCount);
        }
        return pageInfo;
    }
    /**
     * 返回用户收藏的博客分页
     */
    public PageInfo<Blog> selectCollect(Blog blog, Integer pageNum, Integer pageSize) {
        Account currentUser = TokenUtils.getCurrentUser();
        if(RoleEnum.USER.name().equals(currentUser.getRole())){
            blog.setUserId(currentUser.getId());
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Blog> list = blogMapper.selectCollect(blog);
        list = list.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        PageInfo<Blog> pageInfo = PageInfo.of(list);
        List<Blog> blogList = pageInfo.getList();
        for (Blog b : blogList) {
            int likesCount = likesService.selectByFidAndModule(b.getId(), LikesModuleEnum.BLOG.getValue());
            b.setLikesCount(likesCount);
        }
        return pageInfo;
    }
    /**
     * 返回用户评论的博客分页
     */
    public PageInfo<Blog> selectComment(Blog blog, Integer pageNum, Integer pageSize) {
        Account currentUser = TokenUtils.getCurrentUser();
        if(RoleEnum.USER.name().equals(currentUser.getRole())){
            blog.setUserId(currentUser.getId());
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Blog> list = blogMapper.selectComment(blog);
        list = list.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        PageInfo<Blog> pageInfo = PageInfo.of(list);
        List<Blog> blogList = pageInfo.getList();
        for (Blog b : blogList) {
            if (b != null){
                int likesCount = likesService.selectByFidAndModule(b.getId(), LikesModuleEnum.BLOG.getValue());
                b.setLikesCount(likesCount);
            }
        }
        return pageInfo;
    }

}
