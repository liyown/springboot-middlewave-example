package com.lyw.controller;

import com.lyw.dto.Result;
import com.lyw.service.BlogService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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



    @GetMapping("/hot")
    public Result queryHotBlog(@RequestParam(value = "current", defaultValue = "1") Integer current)
    {
        return blogService.queryHotBlog(current);
    }
}
