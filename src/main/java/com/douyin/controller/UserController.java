package com.douyin.controller;


import com.douyin.dto.LoginFormDTO;
import com.douyin.dto.Result;
import com.douyin.dto.UserDTO;
import com.douyin.entity.User;
import com.douyin.repository.UserRepository;
import com.douyin.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.UUID;

import static com.douyin.utils.RegexUtils.isPwdInvalid;

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
    public Result user(){
        // TODO 获取当前登录的用户并返回
        return userService.getUser();
    }

    @PostMapping("/register")
    public Result info2(@RequestBody UserDTO request, HttpSession session){
        //简单验证必填字段
        if (request.getNickname() == null || request.getNickname().trim().isEmpty() ||
                request.getPassword() == null || request.getPassword().trim().isEmpty() ||
                request.getPhone() == null || request.getPhone().trim().isEmpty()
        ){
            return Result.fail("用户名、密码、电话均为必填项");
        }

        return userService.register(request, session);
    }

}
