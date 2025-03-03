package com.douyin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.douyin.dto.LoginFormDTO;
import com.douyin.dto.Result;
import com.douyin.entity.User;

import javax.servlet.http.HttpSession;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @since 2025-3-3
 */
public interface IUserService extends IService<User> {

    Result login(LoginFormDTO loginForm, HttpSession session);

    Result logout();

    Result register(LoginFormDTO loginForm, HttpSession session);
}
