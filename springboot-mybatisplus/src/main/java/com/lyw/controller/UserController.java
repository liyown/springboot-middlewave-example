package com.lyw.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyw.mapper.UserMapper;
import com.lyw.pojo.User;
import com.lyw.service.UserService;
import jakarta.annotation.Resource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: liuyaowen
 * @poject: springboot
 * @create: 2024-06-05 15:45
 * @Description:
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private UserMapper userMapper;

    @PostMapping("/{id}")
    public String selectById(@PathVariable("id") Integer id) {
        return userMapper.selectById(1).toString();
    }

    @PutMapping("/")
    public String update(@RequestBody User user) {
        userMapper.update(user, null);
        return "update success";
    }

    @GetMapping("/select")
    public String select() {
        IPage<User> page = new Page<>(1, 1);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", "lyw");
        User user = new User();
        user.setName("lyw");
        user.setAge(18);
        IPage<User> userIPage = userMapper.selectPage(page, queryWrapper);
        return userIPage.getRecords().toString();
    }

    @RequestMapping("/test1")
    public String test1() {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getName, "lyw")
                .ge(User::getAge, 18)
                .last("limit 10");
        List<User> users = userMapper.selectList(lambdaQueryWrapper);
        return users.toString();
    }

    @RequestMapping("/test2")
    public String test2() {
        LambdaUpdateWrapper<User> userLambdaQueryWrapper = new LambdaUpdateWrapper<>();
        userLambdaQueryWrapper.eq(User::getName, "lyw")
                .set(User::getAge, 18);
        int update = userMapper.update(userLambdaQueryWrapper);
        return update + "";
    }

    @RequestMapping("/test3")
    public String test3() {
        User user = new User();
        user.setName("lyw");
        user.setAge(18);
        int insert = userMapper.insert(user);
        return insert + "";
    }


    @RequestMapping("/test4")
    public String test4() {
        User user = new User();
        user.setName("lyw");
        user.setAge(18);
        int insert = userMapper.delete(new QueryWrapper<User>().eq("name", "lyw"));
        return insert + "";
    }

    @RequestMapping("/test5")
    @Transactional
    public String test5() {
        User user = new User();
        user.setName("lyw");
        user.setAge(18);
        // user.setDeleted(1);
        int insert = userMapper.insert(user);
        return insert + "";
    }

    @RequestMapping("/test6")
    public String test6() {
        User user = userMapper.selectById(5);
        User user1 = userMapper.selectById(5);

        user.setAge(20);
        userMapper.updateById(user);
        user1.setAge(30);
        userMapper.updateById(user1);
        return "success";
    }
}
