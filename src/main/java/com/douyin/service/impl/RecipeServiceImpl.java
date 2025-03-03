package com.douyin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.douyin.entity.Recipe;
import com.douyin.mapper.RecipeMapper;
import com.douyin.service.IRecipeService;
import org.springframework.stereotype.Service;

@Service
public class RecipeServiceImpl extends ServiceImpl<RecipeMapper, Recipe> implements IRecipeService {

}
