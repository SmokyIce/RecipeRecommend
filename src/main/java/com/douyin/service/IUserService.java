package com.douyin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.douyin.dto.LoginFormDTO;
import com.douyin.dto.Result;
import com.douyin.dto.UpdateUserDTO;
import com.douyin.dto.UserRegisterDTO;
import com.douyin.entity.User;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @since 2025-3-3
 */
public interface IUserService extends IService<User> {

    Result login(LoginFormDTO loginForm);

    Result logout();

    Result register(UserRegisterDTO request);

    Result getUser(String token);

    Result updateUser(String token, UpdateUserDTO request);
}
