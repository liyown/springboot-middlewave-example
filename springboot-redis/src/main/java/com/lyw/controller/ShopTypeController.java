package com.lyw.controller;

import com.lyw.dto.Result;
import com.lyw.pojo.ShopType;
import com.lyw.service.ShopTypeService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: liuyaowen
 * @poject: UserController.java
 * @create: 2024-06-19 09:53
 * @Description:
 */
@RestController
@RequestMapping("/shop-type")
public class ShopTypeController
{
    @Resource
    private ShopTypeService typeService;

    @GetMapping("list")
    public Result queryTypeList() {
        return typeService.queryTypeList();
    }
}