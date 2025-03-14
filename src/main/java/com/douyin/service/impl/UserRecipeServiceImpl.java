package com.douyin.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.douyin.dto.AddUserRecipeDTO;
import com.douyin.dto.Result;
import com.douyin.dto.UserRecipeDTO;
import com.douyin.entity.UserRecipe;
import com.douyin.mapper.UserRecipeMapper;
import com.douyin.service.IUserRecipeServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRecipeServiceImpl extends ServiceImpl<UserRecipeMapper, UserRecipe> implements IUserRecipeServer{

    @Autowired
    private final UserRecipeMapper userRecipeMapper;

    public UserRecipeServiceImpl(UserRecipeMapper userRecipeMapper) {
        this.userRecipeMapper = userRecipeMapper;
    }

    @Override
    public Result getUserRecipes(String token) {
        //1.根据token查询是否存在用户
        if (StrUtil.isBlank(token)) {
            //2.不存在返回请登录
            return Result.fail("请重新登录");
        }
        //3.获取，用list<UserRecipeDTO>接收
        List<UserRecipeDTO> userRecipes = userRecipeMapper.getUserRecipes(token);
        return Result.ok(userRecipes);
    }

    @Override
    public Result addUserRecipe(String token, AddUserRecipeDTO request) {
        if(request.getRecipeId() == null || token == null) {
            return Result.fail("获取不到食谱id或未登录");
        }
        UserRecipe userRecipe = new UserRecipe()
                .setRecipeId(request.getRecipeId())
                .setUserId(request.getUserId())
                .setUserRating(0)
                .setComment("");
        int insert = userRecipeMapper.insert(userRecipe);
        return Result.ok(insert == 1 ? "添加成功" : "添加失败");
    }
}
