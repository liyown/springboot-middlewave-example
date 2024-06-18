package com.lyw.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lyw.pojo.User;

import java.util.List;

/**
 * @author: liuyaowen
 * @poject: springboot
 * @create: 2024-06-06 15:31
 * @Description:
 */
public interface UserService extends IService<User> {

    List<User> selectPage(Long current, Long size);
}
