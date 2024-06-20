package com.lyw.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyw.dto.Result;
import com.lyw.pojo.Blog;
import com.lyw.pojo.User;
import com.lyw.service.BlogService;
import com.lyw.mapper.BlogMapper;
import com.lyw.service.UserService;
import com.lyw.utils.SystemConstants;
import com.lyw.utils.UserHolder;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author liuya
* @description 针对表【tb_blog】的数据库操作Service实现
* @createDate 2024-06-19 09:22:40
*/
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog>
    implements BlogService{

    @Resource
    private UserService userService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Result queryHotBlog(Integer current)
    {
        // 根据用户查询
        Page<Blog> page = query()
                .orderByDesc("liked")
                .page(new Page<>(current, SystemConstants.MAX_PAGE_SIZE));
        // 获取当前页数据
        List<Blog> records = page.getRecords();
        // 查询用户
        records.forEach(blog ->
        {
            Long userId = blog.getUserId();
            User user = userService.getById(userId);
            //判断用户是否已经点赞(检查设置在key是否包含value)
            String key = "blog:liked:" + blog.getId();
//            Double score = stringRedisTemplate.opsForZSet().score(key, UserHolder.getUser().getId().toString());
            blog.setName(user.getNickName());
            blog.setIcon(user.getIcon());
            blog.setIsLike(true);
        });
        return Result.ok(records);
    }

}




