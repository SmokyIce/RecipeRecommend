package com.douyin.controller;


import com.douyin.dto.LoginFormDTO;
import com.douyin.dto.Result;
import com.douyin.dto.UserDTO;
import com.douyin.service.IUserService;
import com.douyin.utils.UserHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
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
        //1.校验用户名密码
        String name = loginForm.getName();
        String password = loginForm.getPassword();
        //2.一致则根据用户名查询信息
        //使用
        String pwd = userService.getById(name).getPassword();
        if(password.equals(pwd)){

        }
        //3.存储token
        //4.返回token
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

    @GetMapping("/me")
    public Result me(){
        // TODO获取当前登录的用户并返回
        UserDTO user = UserHolder.getUser();
        return Result.ok(user);
    }

    @GetMapping("/info/{id}")
    public Result info(@PathVariable("id") Long userId){
        // TODO 查询详情
        return Result.ok();
    }

}
