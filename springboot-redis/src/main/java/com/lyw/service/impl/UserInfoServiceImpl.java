package com.lyw.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyw.pojo.UserInfo;
import com.lyw.service.UserInfoService;
import com.lyw.mapper.UserInfoMapper;
import org.springframework.stereotype.Service;

/**
* @author liuya
* @description 针对表【tb_user_info】的数据库操作Service实现
* @createDate 2024-06-19 09:22:40
*/
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo>
    implements UserInfoService{

}




