package com.lyw.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyw.pojo.BlogComments;
import com.lyw.service.BlogCommentsService;
import com.lyw.mapper.BlogCommentsMapper;
import org.springframework.stereotype.Service;

/**
* @author liuya
* @description 针对表【tb_blog_comments】的数据库操作Service实现
* @createDate 2024-06-19 09:22:40
*/
@Service
public class BlogCommentsServiceImpl extends ServiceImpl<BlogCommentsMapper, BlogComments>
    implements BlogCommentsService{

}




