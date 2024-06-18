package com.lyw.mapper;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.lyw.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;


/**
 * @author: liuyaowen
 * @poject: springboot
 * @create: 2024-06-04 16:12
 * @Description:
 */
// @Mapper
public interface UserMapper extends BaseMapper<User> {


}