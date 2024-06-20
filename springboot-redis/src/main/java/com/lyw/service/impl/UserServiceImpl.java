package com.lyw.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lyw.dto.LoginFormDTO;
import com.lyw.dto.Result;
import com.lyw.dto.UserDTO;
import com.lyw.pojo.User;
import com.lyw.service.UserService;
import com.lyw.mapper.UserMapper;
import com.lyw.utils.RedisConstants;
import com.lyw.utils.RegexUtils;
import com.lyw.utils.SystemConstants;
import com.lyw.utils.UserHolder;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.lyw.utils.RedisConstants.*;

/**
* @author liuya
* @description 针对表【tb_user】的数据库操作Service实现
* @createDate 2024-06-19 09:22:40
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService
{

    private ObjectMapper objectMapper = new ObjectMapper();

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Result sendCode(String phone, HttpSession session)
    {
        //验证手机号
        if (RegexUtils.isPhoneInvalid(phone))
        {
            //验证不通过，返回错误提示
            log.debug("验证码错误.....");
            return Result.fail("手机号错误，请重新填写");
        }
        //验证通过，生成验证码
        //6位数
        String code = RandomUtil.randomNumbers(6);
        stringRedisTemplate.opsForValue().set(LOGIN_CODE_KEY+phone, code, LOGIN_CODE_TTL, TimeUnit.MINUTES);
        //发送验证码
        log.debug("验证码发送成功," + code);
        //返回响应
        return Result.ok();
    }

    @Override
    public Result login(LoginFormDTO loginForm, HttpSession session) {
        //判断手机号格式是否正确
        String phone = loginForm.getPhone();
        if (RegexUtils.isPhoneInvalid(phone))
        {
            //如果不正确则直接返回错误
            log.debug("手机号:" + phone + "错误");
            return Result.fail("手机号格式错误");
        }
        //判断验证码是否一致，redis中对比
        String cacheCode = stringRedisTemplate.opsForValue().get(LOGIN_CODE_KEY+phone);
        String code = loginForm.getCode();
        //如果验证码为空，或者不一致，则返回验证码错误
        if (code == null || code.isEmpty())
        {
            return Result.fail("验证码不能为空");
        }
        //判断验证码是否为6位数
        if (code.length() != 6)
        {
            return Result.fail("验证码长度不正确");
        }
        //判断验证码是否正确
        if (!code.equals(cacheCode))
        {
            //验证码错误
            return Result.fail("验证码错误");
        }
        //验证码输入正确
        //判断用户是否存在
        User user = query().eq("phone", phone).one();
        //如果用户不存在则创建用户，保存到数据库
        if (user == null)
        {
            //创建用户，保存到数据库
            user = createUser(phone);
        }
        //如果用户存在，保存到redis
        UserDTO userDTO = BeanUtil.copyProperties(user, UserDTO.class);
        // 创建token
        String token = UUID.randomUUID().toString(true);
        try {
            stringRedisTemplate.opsForValue().set(LOGIN_USER_KEY+token, objectMapper.writeValueAsString(userDTO),LOGIN_USER_TTL, TimeUnit.MINUTES);
        } catch (JsonProcessingException e) {
            log.error("{}", e);
        }
        //返回响应，返回token
        return Result.ok(token);
    }

    /**
     * 创建用户，添加到数据库中
     *
     * @param phone 手机号码
     * @return user
     */
    private User createUser(String phone)
    {
        User user = new User();
        user.setPhone(phone);
        user.setNickName(SystemConstants.USER_NICK_NAME_PREFIX + RandomUtil.randomString(10));
        //将用户信息插入到 t_user表中
        this.save(user);
        //返回数据
        return user;
    }

    @Override
    public Result sign()
    {
        //获得当前登录的用户
        UserDTO user = UserHolder.getUser();
        //获得用户的id
        Long userId = user.getId();
        //获得当前的日期
        LocalDateTime now = LocalDateTime.now();
        //格式化，：年月
        String keySuffix = now.format(DateTimeFormatter.ofPattern(":yyyyMM"));
        //redis key
        String redisKey = RedisConstants.USER_SIGN_KEY + userId + keySuffix;
        //获得今天是本月的第几天
        int dayOfMonth = now.getDayOfMonth();
        //写入到redis
        stringRedisTemplate.opsForValue().setBit(redisKey, dayOfMonth - 1, true);
        //返回
        return Result.ok();
    }

    @Override
    public Result signCount()
    {
        //获得当前登录的用户
        UserDTO user = UserHolder.getUser();
        //获得用户的id
        Long userId = user.getId();
        //获得当前的日期
        LocalDateTime now = LocalDateTime.now();
        //格式化，：年月
        String keySuffix = now.format(DateTimeFormatter.ofPattern(":yyyyMM"));
        //redis key
        String redisKey = RedisConstants.USER_SIGN_KEY + userId + keySuffix;
        //获得今天是本月的第几天，日期：从 1 到 31
        int dayOfMonth = now.getDayOfMonth();
        //从redis里取签到结果
        List<Long> list = stringRedisTemplate.opsForValue()
                .bitField(redisKey,
                        BitFieldSubCommands.create()
                                .get(BitFieldSubCommands
                                        .BitFieldType
                                        .unsigned(dayOfMonth)).valueAt(0));
        //判断是否为空
        if (list == null || list.isEmpty())
        {
            //没有，返回0
            return Result.ok(0);
        }
        //取第一个，因为一个月最多有31天，小于32位，所以只有一个
        Long num = list.get(0);
        //判断第一个是否为空
        if (num == null || num == 0)
        {
            //第一个为0，返回直接0
            return Result.ok(0);
        }
        //计数器
        int count = 0;
        //循环遍历数据
        while (true)
        {
            //无符号，和1做与运算
            long result = num & 1;
            //判断是否为未签到
            if (result == 0)
            {
                //为签到，跳出循环
                break;
            }
            //不是0
            //计数器+1
            count++;
            //右移一位，左边会补0，所以不用担心会死循环
            num = num >> 1;
        }
        //返回
        return Result.ok(count);
    }

    @Override
    public Result logout(HttpServletRequest request, HttpServletResponse response)
    {
        //从请求头里获取token
        String token = request.getHeader("authorization");
        //key
        String tokenKey = LOGIN_USER_KEY + token;
        //删除redis里的相关key
        Boolean delete = stringRedisTemplate.delete(tokenKey);
        //判断
        if (delete==null||!delete)
        {
            return Result.fail("退出登录失败");
        }
        return Result.ok();
    }

}




