package com.douyin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.douyin.entity.Recipe;
import com.douyin.mapper.RecipeMapper;
import com.douyin.mapper.UserRecipeMapper;
import com.douyin.service.IRecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RecipeServiceImpl extends ServiceImpl<RecipeMapper, Recipe> implements IRecipeService {

    @Autowired
    private final UserRecipeMapper userRecipeMapper;

    public RecipeServiceImpl(UserRecipeMapper userRecipeMapper) {
        this.userRecipeMapper = userRecipeMapper;
    }

}
