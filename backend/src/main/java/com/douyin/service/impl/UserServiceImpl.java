package com.douyin.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.douyin.dto.LoginFormDTO;
import com.douyin.dto.Result;
import com.douyin.dto.UpdateUserDTO;
import com.douyin.dto.userRecipe.UserRegisterDTO;
import com.douyin.entity.User;
import com.douyin.mapper.UserMapper;
import com.douyin.service.IUserService;
import com.douyin.utils.UserHolder;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.douyin.utils.RegexUtils.isPhoneInvalid;
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

    @Autowired
    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

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
        }
        //3.不匹配则返回错误信息
        if(user == null) return Result.fail("用户名密码错误");

        //4.匹配则生成token
        String token = user.getUserId();
        UserHolder.saveUser(user);

        //5.返回token
        return Result.ok(token);
    }

    /**
     * 登出
     * @return 实际从前端实现登出
     */
    @Override
    public Result logout() {
        //登出
        return Result.ok("登出成功");
    }

    /**
     * 注册信息
     * @param request 包含用户注册提交的信息
     * @return 手机号被占用、密码不合理、注册成功
     */
    @Override
    public Result register(UserRegisterDTO request) {
        //注册
        //1.查询手机号是否合理
        if (isPhoneInvalid(request.getPhone())) {
            return Result.fail("手机号错误");
        }
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
    public Result getUser(String token) {
        // 1.检测token对应的用户
        User user = query().eq("user_id", token).one();
        // 2.未检测到登录信息，返回错误信息
        if(user == null){
            return Result.fail("用户错误");
        }
        // 3.获取到用户信息，返回用户信息
        return Result.ok(user.getNickname());
    }

    @Override
    public Result updateUser(String token, UpdateUserDTO request) {
        //1.根据token查用户信息
        User user = query().eq("user_id", token).one();
        //2.没查到，返回错误
        if(user == null){
            return Result.fail("用户不存在，请重新登录");
        }
        //3.查到了，修改数据库
        int age = request.getAge();
        String nickname = request.getNickname();
        if(age >= 150 || age < 0){
            return Result.fail("年龄异常，请重新修改");
        }
        user.setAge(age);
        if(StrUtil.isNotBlank(nickname)){
            user.setNickname(nickname);
        }
        //4.修改数据库
        updateById(user);
        //5.返回数据
        return Result.ok("修改成功！");
    }

    @Override
    public Result savePreferences(String userId, String request) {
        if(StrUtil.isEmpty(userId)){
            return Result.fail("请登录");
        }
        System.out.println(request);
        return userMapper.updatePreference(request, userId) == 1 ? Result.ok("修改成功！") : Result.ok("修改失败！");
    }

    @Override
    public Result getPreference(String token) {
        User user = query().eq("user_id", token).one();
        if(user == null){
            return Result.fail("请登录");
        }
        String preferences = StrUtil.subBetween(user.getRecipePreferences(), "{\"recipePreferences\":", "}");
        return Result.ok(preferences);
    }
}
