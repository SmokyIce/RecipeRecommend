package com.douyin.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.douyin.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.douyin.utils.RedisConstants.LOGIN_USER_TTL;
import static com.douyin.utils.RedisConstants.LOGOUT_PATH;

public class RefreshTokenInterception implements HandlerInterceptor {
    private StringRedisTemplate stringRedisTemplate;
    public RefreshTokenInterception(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1.获取请求头中的token
        String token = request.getHeader("authorization");
        if (StrUtil.isBlank(token)) {
            return true;
        }
        //2.从redis中获取用户
        String tokenKey = RedisConstants.LOGIN_USER_KEY + token;
        //登出时删除token
        String pathInfo = request.getRequestURI();
        if (pathInfo.equals(LOGOUT_PATH)) {
            stringRedisTemplate.delete(tokenKey);
            return true;
        }
        Map<Object, Object> userMap = stringRedisTemplate.opsForHash().entries(tokenKey);
        //3.判断用户是否存在
        if (userMap.isEmpty()) {
            return true;
        }
        //5.将查询到的Hash数据转为UserDTO对象
        User user = BeanUtil.fillBeanWithMap(userMap, new User(), false);
        //6.存在：保存用户到ThreadLocal
        UserHolder.saveUser(user);
        //7.刷新token有效期
        stringRedisTemplate.expire(tokenKey, LOGIN_USER_TTL, TimeUnit.MINUTES);
        //8.放行
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserHolder.removeUser();
    }
}
