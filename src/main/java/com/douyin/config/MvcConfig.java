package com.douyin.config;

import com.douyin.utils.LoginInterceptor;
import com.douyin.utils.RefreshTokenInterception;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class MvcConfig implements WebMvcConfigurer {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/user/login",
                        "/user/register",
                        "/user/logout",
                        "/user/getUserInfo",
                        "/user/getUserList",
                        "/user/getUserById",
                        "/user/getUserByName",
                        "/user/getUserByEmail"
                ).order(1);
        registry.addInterceptor(
                new RefreshTokenInterception(stringRedisTemplate))
                .addPathPatterns("/**").order(0);
    }
}
