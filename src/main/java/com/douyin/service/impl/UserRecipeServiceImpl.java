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
public class UserRecipeServiceImpl extends ServiceImpl<UserRecipeMapper, UserRecipe> implements IUserRecipeServer{

    @Autowired
    private final UserRecipeMapper userRecipeMapper;
    //执行sql查询创建的mpjLambdaWrapper对象
    private final MPJLambdaWrapper<UserRecipe> mpjLambdaWrapper = new MPJLambdaWrapper();

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
        //3.根据token查找连接的数据表，UserRecipe表和Recipe表连接查询。
        mpjLambdaWrapper.select(UserRecipe::getComment,UserRecipe::getUserRating,UserRecipe::getId)
                .select(Recipe::getName, Recipe::getImage, Recipe::getCookingMethod,Recipe::getRecipeId)
                .leftJoin(Recipe.class,Recipe::getRecipeId,UserRecipe::getRecipeId)
                .eq(UserRecipe::getUserId,token);
        //将返回数据存入的对象和mpjLambdaWrapper对象传入，用list<UserRecipeDTO>接收
        List<UserRecipeDTO> userRecipes = userRecipeMapper.selectJoinList(UserRecipeDTO.class, mpjLambdaWrapper);
        return Result.ok(userRecipes);
    }
}
