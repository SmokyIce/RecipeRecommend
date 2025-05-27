package com.douyin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.douyin.dto.Result;
import com.douyin.entity.Recipe;

import java.util.List;

public interface IRecipeService extends IService<Recipe>{

    Result getIngredientsByRecipeId(String recipeId);
    List<Recipe> getDailyRecipes();

    Result getAllRecipes();
}
