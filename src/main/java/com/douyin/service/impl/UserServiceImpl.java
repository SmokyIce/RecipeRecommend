package com.douyin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.douyin.dto.LoginFormDTO;
import com.douyin.dto.Result;
import com.douyin.entity.User;
import com.douyin.mapper.UserMapper;
import com.douyin.service.IUserService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;


    @Override
    public Result login(LoginFormDTO loginForm, HttpSession session) {
        return Result.ok();
    }

    @Override
    public Result logout() {
        return Result.ok();
    }

    @Override
    public Result register(LoginFormDTO loginForm, HttpSession session) {

        return Result.ok();
    }
}
