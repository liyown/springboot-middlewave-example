package com.lyw.service;

import com.lyw.dto.Result;
import com.lyw.pojo.Blog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author liuya
* @description 针对表【tb_blog】的数据库操作Service
* @createDate 2024-06-19 09:22:40
*/
public interface BlogService extends IService<Blog> {

    Result queryHotBlog(Integer current);

    Result likeBlog(Long id);

    Result queryLikes(Long id);
}
