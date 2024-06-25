package com.lyw.service;

import com.lyw.dto.Result;
import com.lyw.pojo.Follow;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author liuya
* @description 针对表【tb_follow】的数据库操作Service
* @createDate 2024-06-19 09:22:40
*/
public interface FollowService extends IService<Follow> {

    Result follow(Long id, Long id1, Boolean type);

    Result isFollow(Long id, Long id1);

    Result commonFollow(Long id, Long id1);
}
