package com.example.controller;

import com.example.common.Result;
import com.example.entity.Likes;
import com.example.service.LikesService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author snail
 * @version 1.0
 * @project AI-learning
 * @description 前台交互请求点赞数
 * @date 2024/3/14 13:46:26
 */
@RestController
@RequestMapping("/likes")
public class LikesController {
    @Resource
    private LikesService likesService;
    @PostMapping("/set")
    public Result set(@RequestBody Likes likes){
        likesService.set(likes);
        return Result.success();
    }
}
