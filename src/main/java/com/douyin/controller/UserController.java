package com.douyin.controller;


import com.douyin.dto.LoginFormDTO;
import com.douyin.dto.Result;
import com.douyin.dto.UpdateUserDTO;
import com.douyin.dto.UserRegisterDTO;
import com.douyin.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

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

    @PostMapping("/login")
    public Result login(@RequestBody LoginFormDTO loginForm, HttpSession session){
        // TODO 实现登录功能
        return userService.login(loginForm, session);
    }

    /**
     * 登出功能
     * @return 无
     */
    @PostMapping("/logout")
    public Result logout(){
        // TODO 实现登出功能
        
        return userService.logout();
    }

    @GetMapping("/user")
    public Result user(@RequestHeader(value = "authorization", required = false) String token){
        // 获取token并返回nickname
        return userService.getUser(token);
    }

    @PostMapping("/register")
    public Result info2(@RequestBody UserRegisterDTO request, HttpSession session){
        //简单验证必填字段
        if (request.getNickname() == null || request.getNickname().trim().isEmpty() ||
                request.getPassword() == null || request.getPassword().trim().isEmpty() ||
                request.getPhone() == null || request.getPhone().trim().isEmpty()
        ){
            return Result.fail("用户名、密码、电话均为必填项");
        }

        return userService.register(request, session);
    }

    @PostMapping("/updateUser")
    public Result update(@RequestHeader(value = "authorization", required = false) String token,@RequestBody UpdateUserDTO request){
        //返回修改成功与否
        return userService.updateUser(token, request);
    }
}
