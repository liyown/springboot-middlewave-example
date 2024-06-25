package com.lyw.controller;

import com.lyw.dto.Result;
import com.lyw.dto.UserDTO;
import com.lyw.service.FollowService;
import com.lyw.utils.UserHolder;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * @author: liuyaowen
 * @poject: UserController.java
 * @create: 2024-06-24 22:04
 * @Description:
 */
@RestController
@RequestMapping("/follow")
public class FollowController {

    @Resource
    private FollowService followService;

    @PutMapping("/{id}/{type}")
    public Result follow(@PathVariable("id") Long id, @PathVariable("type") Boolean type) {
        // 获取登录用户
        UserDTO user = UserHolder.getUser();
        // 关注
        return followService.follow(user.getId(), id, type);
    }

    @GetMapping("/or/not/{id}")
    public Result isFollow(@PathVariable("id") Long id) {
        // 获取登录用户
        UserDTO user = UserHolder.getUser();
        // 是否关注
        return followService.isFollow(user.getId(), id);
    }

    @GetMapping("/common/{id}")
    public Result commonFollow(@PathVariable("id") Long id) {
        // 获取登录用户
        UserDTO user = UserHolder.getUser();
        // 是否关注
        return followService.commonFollow(user.getId(), id);
    }
}
