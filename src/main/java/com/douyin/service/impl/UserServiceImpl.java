package com.douyin.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.jwt.JWT;
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

import java.util.Date;

import static com.douyin.utils.RedisConstants.LOGIN_USER_TTL;
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

    /**
     * 登录
     * @return 登录用户id
     */
    @Override
    public Result login(LoginFormDTO loginForm, HttpSession session) {
        //1.校验手机密码
        String phone = loginForm.getPhone();
        String password = loginForm.getPassword();
        //2.获取数据库密码
        User user = null;
        if(StrUtil.isNotBlank(phone) && StrUtil.isNotBlank(password)){
            user = query().eq("phone", phone).eq("password", password).one();
            System.out.println(user);
        }
        //3.不匹配则返回错误信息
        if(user == null){
            return Result.fail("用户名密码错误");
        }
        //4.匹配则生成token
        String token = user.getUserId();
        //5.返回token
        return Result.ok(token);
    }

    @Override
    public Result logout() {
        //TODO 登出
        return Result.ok();
    }

    @Override
    public Result register(UserDTO request, HttpSession session) {
        //注册
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
        save(user);
        return Result.fail("注册成功");
    }

    @Override
    public Result getUser() {
        // 1.未检测到登录信息，返回错误信息

        // 2.获取到用户信息，返回用户信息

        return null;
    }
}
