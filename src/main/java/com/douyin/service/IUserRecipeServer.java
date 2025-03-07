package com.douyin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.douyin.dto.Result;
import com.douyin.dto.UserRecipeDTO;
import com.douyin.entity.Recipe;
import com.douyin.entity.UserRecipe;
import org.springframework.web.bind.annotation.RequestHeader;

public interface IUserRecipeServer  extends IService<UserRecipe> {
    public Result getRecipes(@RequestHeader(value = "authorization", required = false) String token);

}
