package com.example.service;

import cn.hutool.core.util.ObjectUtil;
import com.example.common.Constants;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.entity.Admin;
import com.example.entity.User;
import com.example.exception.CustomException;
import com.example.mapper.UserMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author snail
 * @version 1.0
 * @project AI-learning
 * @description user的服务层
 * @date 2024/3/7 20:49:27
 */
@Service
public class UserService {
    @Resource
    private UserMapper userMapper;

    /**
     * 新增
     * @param user
     */
    public void add(User user){
        //业务方法
        //判断账号是否重复，密码是否为空，用户名是否为空，用户角色
        User dbUser = userMapper.selectByUsername(user.getUsername());
        if(dbUser != null){
            throw new CustomException(ResultCodeEnum.USER_EXIST_ERROR);//账号已经存在
        }
        if(ObjectUtil.isEmpty(user.getPassword())){
            user.setPassword(Constants.USER_DEFAULT_PASSWORD);//默认密码123
        }
        if(ObjectUtil.isEmpty(user.getName())){
            user.setName(user.getUsername());
        }
        user.setRole(RoleEnum.USER.name());//设置角色为用户
        userMapper.insert(user);
    }

    public void deleteById(Integer id) {
        userMapper.deleteById(id);
    }

    public void deleteBatch(List<Integer> ids) {
        for(Integer id : ids) {
            this.deleteById(id);
        }
    }

    public void updateById(User user) {
        userMapper.updateById(user);
    }

    public User selectById(Integer id) {
        return userMapper.selectById(id);
    }

    public List<User> selectAll(User user) {
        return userMapper.selectAll(user);
    }

    public PageInfo<User> selectPage(User user, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);//这个静态方法告诉PageHelper插件接下来的查询要进行分页。
        // 传入页码pageNum和每页大小pageSize。它影响接下来执行的第一个MyBatis查询，让这次查询的结果是被分页的。
        List<User> list = userMapper.selectAll(user);
        return PageInfo.of(list);
    }

    /**
     * 登录
     * @param account
     * @return
     */
    public Account login(Account account) {
        Account dbUser = userMapper.selectByUsername(account.getUsername());
        if (ObjectUtil.isNull(dbUser)) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        if (!account.getPassword().equals(dbUser.getPassword())) {
            throw new CustomException(ResultCodeEnum.USER_ACCOUNT_ERROR);
        }
        // 生成token
        String tokenData = dbUser.getId() + "-" + RoleEnum.USER.name();
        String token = TokenUtils.createToken(tokenData, dbUser.getPassword());
        dbUser.setToken(token);
        return dbUser;

    }

    /**
     * 注册
     * @param account
     */
    public void register(Account account) {
        User user = new User();
        BeanUtils.copyProperties(account, user);
        add(user);
    }

    public void updatePassword(Account account) {
        User dbUser = userMapper.selectByUsername(account.getUsername());
        if (ObjectUtil.isNull(dbUser)) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        if (!account.getPassword().equals(dbUser.getPassword())) {
            throw new CustomException(ResultCodeEnum.PARAM_PASSWORD_ERROR);
        }
        dbUser.setPassword(account.getNewPassword());
        userMapper.updateById(dbUser);
    }
}
