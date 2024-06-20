package com.lyw.interceptor;

/**
 * @author: liuyaowen
 * @poject: UserController.java
 * @create: 2024-06-19 11:00
 * @Description:
 */
import cn.hutool.core.bean.BeanUtil;
import com.lyw.dto.UserDTO;
import com.lyw.utils.RedisConstants;
import com.lyw.utils.UserHolder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.lyw.utils.RedisConstants.LOGIN_USER_TTL;

/**
 * Project name(项目名称)：spring_boot_redis_hmdp_login_based_on_redis
 * Package(包名): mao.spring_boot_redis_hmdp.interceptor
 * Class(类名): RefreshTokenInterceptor
 * Author(作者）: mao
 * Author QQ：1296193245
 * GitHub：https://github.com/maomao124/
 * Date(创建日期)： 2022/5/13
 * Time(创建时间)： 21:20
 * Version(版本): 1.0
 * Description(描述)： 刷新token的拦截器，毕竟不需要登录的请求也需要刷新
 */

public class RefreshTokenInterceptor implements HandlerInterceptor
{
    private final StringRedisTemplate stringRedisTemplate;

    /**
     * 构造函数
     *
     * @param stringRedisTemplate StringRedisTemplate
     */
    public RefreshTokenInterceptor(StringRedisTemplate stringRedisTemplate)
    {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 拦截器刷新token的过期时间
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @param handler  Object
     * @return boolean
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        //从请求头里获取token
        String token = request.getHeader("authorization");
        //判断token是否存在
        if (token == null || token.isEmpty())
        {
            //不存在，拦截
            response.setCharacterEncoding("utf-8");
            response.getWriter().write("no token");
            return false;
        }
        //token存在, 刷新有效时间
        stringRedisTemplate.expire(RedisConstants.LOGIN_USER_KEY + token, LOGIN_USER_TTL, TimeUnit.MINUTES);
        //放行
        return true;
    }

    /**
     * 渲染之后，返回用户之前。 用户执行完毕后，销毁对应的用户信息
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @param handler  Object
     * @param ex       Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception
    {
        UserHolder.removeUser();
    }
}