package com.lyw.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyw.mapper.UserMapper;
import com.lyw.pojo.User;
import com.lyw.service.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.Synchronized;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: liuyaowen
 * @poject: springboot
 * @create: 2024-06-06 15:31
 * @Description:
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Synchronized
    @PostConstruct
    public void initMethod() {
        System.out.println("UserServiceImpl init");
    }

    public List<User> selectPage(Long current, Long size) {
        Page<User> page = new Page<>(current, size);
        Page<User> selectPage = userMapper.selectPage(page, null);
        return selectPage.getRecords();

    }
}
