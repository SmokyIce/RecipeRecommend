package com.douyin.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.douyin.dto.Result;
import com.douyin.dto.UserRecipeDTO;
import com.douyin.entity.Recipe;
import com.douyin.entity.UserRecipe;
import com.douyin.mapper.RecipeMapper;
import com.douyin.mapper.UserRecipeMapper;
import com.douyin.service.IRecipeService;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RecipeServiceImpl extends ServiceImpl<RecipeMapper, Recipe> implements IRecipeService {

    @Autowired
    private final UserRecipeMapper userRecipeMapper;

    public RecipeServiceImpl(UserRecipeMapper userRecipeMapper) {
        this.userRecipeMapper = userRecipeMapper;
    }

}
