package com.lyw.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyw.dto.Result;
import com.lyw.mapper.FollowMapper;
import com.lyw.pojo.Follow;
import com.lyw.pojo.User;
import com.lyw.service.FollowService;
import com.lyw.utils.RedisIDGenerator;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
* @author liuya
* @description 针对表【tb_follow】的数据库操作Service实现
* @createDate 2024-06-19 09:22:40
*/
@Service
public class FollowServiceImpl extends ServiceImpl<FollowMapper, Follow>
    implements FollowService{

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private RedisIDGenerator redisIDGenerator;

    @Resource
    private UserServiceImpl userServiceImpl;

    @Override
    public Result follow(Long id, Long id1, Boolean type) {
        if (type) {
            Follow follow = new Follow();
            follow.setId(redisIDGenerator.nextID("follow"));
            follow.setUserId(id);
            follow.setFollowUserId(id1);
            follow.setCreateTime(LocalDateTime.now());
            save(follow);
            stringRedisTemplate.opsForZSet().add("follow:" + id, id1.toString(), System.currentTimeMillis());
        } else {
            LambdaUpdateWrapper<Follow> LambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            LambdaUpdateWrapper.eq(Follow::getUserId, id).eq(Follow::getFollowUserId, id1);
            remove(LambdaUpdateWrapper);
            stringRedisTemplate.opsForZSet().remove("follow:" + id, id1.toString());
        }
        return Result.ok();
    }

    @Override
    public Result isFollow(Long id, Long id1) {
        LambdaUpdateWrapper<Follow> LambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        LambdaUpdateWrapper.eq(Follow::getUserId, id).eq(Follow::getFollowUserId, id1);
        return Result.ok(count(LambdaUpdateWrapper) > 0);
    }

    @Override
    public Result commonFollow(Long id, Long id1) {
        Set<String> intersect = stringRedisTemplate.opsForZSet().intersect("follow:" + id, "follow:" + id1);
        if (intersect == null || intersect.isEmpty()) {
            return Result.ok();
        }
        List<User> collect = intersect.stream().map(userServiceImpl::getById).collect(Collectors.toList());
        return Result.ok(collect);
    }

    @Override
    public List<Long> myFollows(Long id) {
        return this.lambdaQuery().eq(Follow::getUserId, id).list()
                .stream()
                .map(Follow::getFollowUserId)
                .collect(Collectors.toList());
    }

}




