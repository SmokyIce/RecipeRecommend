package com.douyin.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.douyin.dto.LoginFormDTO;
import com.douyin.dto.Result;
import com.douyin.dto.UserDTO;
import com.douyin.entity.User;
import com.douyin.mapper.UserMapper;
import com.douyin.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import static com.douyin.utils.RegexUtils.isPwdInvalid;
import static com.douyin.utils.SystemConstants.USER_NAME_PREFIX;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author smkice
 * @since 2025-3-4
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public Result login(LoginFormDTO loginForm, HttpSession session) {
        //TODO 未完成
        return Result.ok();
    }

    @Override
    public Result logout() {
        //TODO 登出
        return Result.ok();
    }

    @Override
    public Result register(UserDTO request, HttpSession session) {
        //TODO 注册
        //1.查询手机号是否被占用
        User existUser = query().eq("phone", request.getPhone()).one();
        if(existUser != null){
            return Result.fail("手机号被占用");
        }
        //2.查询密码是否合理
        if (isPwdInvalid(request.getPassword())){
            return Result.fail("密码有误");
        }
        //3.生成用户
        User user = new User();
        user.setUserId(USER_NAME_PREFIX+ RandomUtil.randomNumbers(20));
        user.setPhone(request.getPhone());
        user.setPassword(request.getPassword());
        user.setNickname(request.getNickname());
        user.setAge(0);

        //4.将用户存入数据库
        System.out.println(user);
        save(user);
        return Result.fail("注册成功");
    }
}
