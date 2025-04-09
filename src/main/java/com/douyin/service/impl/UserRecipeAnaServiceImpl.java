package com.douyin.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.douyin.dto.AddUserRecipeDTO;
import com.douyin.dto.Result;
import com.douyin.dto.UserRecipeAnaDTO;
import com.douyin.dto.UserRecipeDTO;
import com.douyin.entity.User;
import com.douyin.entity.UserRecipe;
import com.douyin.mapper.UserRecipeAnaMapper;
import com.douyin.service.IUserRecipeAnaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class UserRecipeAnaServiceImpl extends ServiceImpl<UserRecipeAnaMapper, UserRecipeAnaDTO> implements IUserRecipeAnaService{

    @Autowired
    private final UserRecipeAnaMapper userRecipeAnaMapper;

    public UserRecipeAnaServiceImpl(UserRecipeAnaMapper userRecipeAnaMapper) {
        this.userRecipeAnaMapper = userRecipeAnaMapper;
    }

    @Override
    public Result getUserRecipesAna(String token) {
        //1.根据token查询是否存在用户
        if (StrUtil.isBlank(token)) {
            //2.不存在返回请登录
            return Result.fail("请重新登录");
        }
        //3.获取，用list<UserRecipeDTO>接收
        List<UserRecipeAnaDTO> userRecipes = userRecipeAnaMapper.getUserRecipesAna(token);
        return Result.ok(userRecipes);
    }

}
