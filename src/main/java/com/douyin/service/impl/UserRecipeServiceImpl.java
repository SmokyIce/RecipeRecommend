package com.douyin.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.douyin.dto.Result;
import com.douyin.dto.UserRecipeDTO;
import com.douyin.entity.Recipe;
import com.douyin.entity.UserRecipe;
import com.douyin.mapper.UserRecipeMapper;
import com.douyin.service.IRecipeService;
import com.douyin.service.IUserRecipeServer;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserRecipeServiceImpl extends ServiceImpl<UserRecipeMapper, UserRecipeDTO> implements IUserRecipeServer{

    @Autowired
    private final UserRecipeMapper userRecipeMapper;

    private final MPJLambdaWrapper<UserRecipeDTO> mpjLambdaWrapper = new MPJLambdaWrapper();

    public UserRecipeServiceImpl(UserRecipeMapper userRecipeMapper) {
        this.userRecipeMapper = userRecipeMapper;
    }

    @Override
    public Result getRecipes(String token) {
        //1.根据token查询是否存在用户
        if (StrUtil.isBlank(token)) {
            //2.不存在返回请登录
            return Result.fail("请重新登录");
        }
        //3.根据token查找连接的数据表
        mpjLambdaWrapper.select(UserRecipe::getComment,UserRecipe::getUserRating,UserRecipe::getId)
                .select(Recipe::getName, Recipe::getImage, Recipe::getCookingMethod)
                .leftJoin(Recipe.class,Recipe::getRecipeId,UserRecipe::getRecipeId)
                .eq(UserRecipe::getUserId,token);
        List<UserRecipeDTO> userRecipes = userRecipeMapper.selectJoinList(UserRecipeDTO.class, mpjLambdaWrapper);
        System.out.println(userRecipes);
        return Result.ok(userRecipes);
    }
}
