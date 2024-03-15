package com.example.controller;

import com.example.common.Result;
import com.example.entity.Collect;
import com.example.service.CollectService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author snail
 * @version 1.0
 * @project AI-learning
 * @description 前台交互请求收藏
 * @date 2024/3/14 13:46:26
 */
@RestController
@RequestMapping("/collect")
public class CollectController {

    @Resource
    CollectService collectService;

    // 收藏和取消
    @PostMapping("/set")
    public Result set(@RequestBody Collect collect) {
        collectService.set(collect);
        return Result.success();
    }

}
