package com.douyin.controller;


import com.douyin.dto.LoginFormDTO;
import com.douyin.dto.Result;
import com.douyin.dto.UpdateUserDTO;
import com.douyin.dto.UserRegisterDTO;
import com.douyin.service.IUserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author none
 * @since 2025-3-3
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;

    /**
     * 登录功能
     * @param loginForm 登录信息表，包含密码，用户名
     * @return 登录是否成功，成功返回登录token，失败返回用户信息
     */
    @PostMapping("/login")
    public Result login(@RequestBody LoginFormDTO loginForm){
        log.info("登录信息:{}", loginForm);
        // 实现登录功能
        return userService.login(loginForm);
    }

    /**
     * 登出功能
     * @return 前端删除token，自动登出成功
     */
    @PostMapping("/logout")
    public Result logout(){
        // 实现登出功能
        return userService.logout();
    }
    /**
     * 用户已经登录，则根据用户登录后带的token将user的昵称返回
     */
    @GetMapping("/user")
    public Result user(@RequestHeader(value = "authorization", required = false) String token){
        return userService.getUser(token);
    }

    /**
     * 注册功能
     * @param request 包含用户注册信息
     * @return 返回是否注册成功
     */
    @PostMapping("/register")
    public Result info2(@RequestBody UserRegisterDTO request){
        //简单验证必填字段
        if (request.getNickname() == null || request.getNickname().trim().isEmpty() ||
                request.getPassword() == null || request.getPassword().trim().isEmpty() ||
                request.getPhone() == null || request.getPhone().trim().isEmpty()
        ){
            return Result.fail("用户名、密码、电话均为必填项");
        }

        return userService.register(request);
    }

    /**
     * 更新用户信息
     * @param token 唯一定位用户
     * @param request 请求包含传来的更新信息
     * @return
     */
    @PostMapping("/updateUser")
    public Result update(@RequestHeader(value = "authorization", required = false) String token,@RequestBody UpdateUserDTO request){
        //返回修改成功与否
        return userService.updateUser(token, request);
    }
}
