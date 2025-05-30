package com.douyin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.douyin.dto.LoginFormDTO;
import com.douyin.dto.Result;
import com.douyin.dto.UpdateUserDTO;
import com.douyin.dto.userRecipe.UserRegisterDTO;
import com.douyin.entity.User;
import jakarta.servlet.http.HttpSession;


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

    Result register(UserRegisterDTO request);

    Result getUser(String token);

    Result updateUser(String token, UpdateUserDTO request);

    Result savePreferences(String token, String request);

    Result getPreference(String token);
}
