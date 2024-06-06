package com.lyw.mapper;

import com.lyw.pojo.User;

/**
* @author liuya
* @description 针对表【user】的数据库操作Mapper
* @createDate 2024-06-02 18:47:01
* @Entity com.lyw.pojo.User
*/
public interface UserMapper {

    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

}
