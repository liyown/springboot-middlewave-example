package com.lyw.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyw.dto.Result;
import com.lyw.dto.UserDTO;
import com.lyw.pojo.Blog;
import com.lyw.pojo.User;
import com.lyw.service.BlogService;
import com.lyw.service.FollowService;
import com.lyw.service.UserService;
import com.lyw.utils.SystemConstants;
import com.lyw.utils.UserHolder;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: liuyaowen
 * @poject: UserController.java
 * @create: 2024-06-19 10:39
 * @Description:
 */
@RestController
@RequestMapping("/blog")
public class BlogController {

    @Resource
    private BlogService blogService;
    @Resource
    private UserService userService;

    @Resource
    private FollowService followService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @PostMapping
    public Result saveBlog(@RequestBody Blog blog) {
        // 获取登录用户
        UserDTO user = UserHolder.getUser();
        blog.setUserId(user.getId());
        // 保存探店博文
        blogService.save(blog);
        // 查询粉丝
        List<Long> longs = followService.myFollows(user.getId());
        // 推送博文id
        longs.forEach(aLong -> stringRedisTemplate.opsForList().leftPush("blog:push:" + aLong, blog.getId().toString()));
        // 返回id
        return Result.ok(blog.getId());
    }

    @PutMapping("/like/{id}")
    public Result likeBlog(@PathVariable("id") Long id) {
        return blogService.likeBlog(id);
    }

    @GetMapping("likes/{id}")
    public Result queryLikes(@PathVariable("id") Long id) {
        return blogService.queryLikes(id);
    }

    @GetMapping("/of/me")
    public Result queryMyBlog(@RequestParam(value = "current", defaultValue = "1") Integer current) {
        // 获取登录用户
        UserDTO user = UserHolder.getUser();
        // 根据用户查询
        Page<Blog> page = blogService.query()
                .eq("user_id", user.getId()).page(new Page<>(current, SystemConstants.MAX_PAGE_SIZE));
        // 获取当前页数据
        List<Blog> records = page.getRecords();
        return Result.ok(records);
    }
    @GetMapping("/of/follow")
    public Result queryFollowBlog(@RequestParam(value = "current", defaultValue = "1") Integer current) {
        // 获取登录用户
        UserDTO user = UserHolder.getUser();
        // 查询推送博文id
        List<String> range = stringRedisTemplate.opsForList().range("blog:push:" + user.getId(), 0, -1);

        // 查询博文
        List<Blog> records = blogService.query().in("id", range).orderByDesc("create_time").list();

        Map<String, Object> map = new HashMap<>();
        map.put("list", records);
        map.put("minTime", 0);
        map.put("offset", 0);

        return Result.ok(map);
    }

    @GetMapping("of/user")
    public Result queryUserBlog(@RequestParam(value = "current", defaultValue = "1") Integer current, @RequestParam("id") Long userId) {
        // 根据用户查询
        Page<Blog> page = blogService.query()
                .eq("user_id", userId).page(new Page<>(current, SystemConstants.MAX_PAGE_SIZE));
        // 获取当前页数据
        List<Blog> records = page.getRecords();
        return Result.ok(records);
    }

    @GetMapping("/hot")
    public Result queryHotBlog(@RequestParam(value = "current", defaultValue = "1") Integer current) {
        // 根据用户查询
        Page<Blog> page = blogService.query()
                .orderByDesc("liked")
                .page(new Page<>(current, SystemConstants.MAX_PAGE_SIZE));
        // 获取当前页数据
        List<Blog> records = page.getRecords();
        UserDTO userInfo = UserHolder.getUser();

        // 查询用户
        records.forEach(blog ->{
            Long userId = blog.getUserId();
            User user = userService.getById(userId);
            Boolean member = stringRedisTemplate.opsForZSet().score("blog:liked:" + blog.getId(), userInfo.getId().toString()) != null;
            blog.setIsLike(member);
            blog.setName(user.getNickName());
            blog.setIcon(user.getIcon());
        });
        return Result.ok(records);
    }

    @GetMapping("/{id}")
    public Result queryBlog(@PathVariable("id") Long id) {
        // 查询博文
        Blog blog = blogService.getById(id);
        // 查询用户
        User user = userService.getById(blog.getUserId());
        blog.setName(user.getNickName());
        blog.setIcon(user.getIcon());

        Boolean member = stringRedisTemplate.opsForZSet().score("blog:liked:" + blog.getId(), UserHolder.getUser().getId().toString()) != null;
        blog.setIsLike(member);

        return Result.ok(blog);
    }

}
