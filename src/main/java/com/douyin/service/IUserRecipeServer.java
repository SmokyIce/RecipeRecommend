package com.douyin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.douyin.dto.AddUserRecipeDTO;
import com.douyin.dto.Result;
import com.douyin.dto.UserRecipeDTO;
import com.douyin.entity.Recipe;
import com.douyin.entity.UserRecipe;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

public interface IUserRecipeServer  extends IService<UserRecipe> {
    public Result getUserRecipes(@RequestHeader(value = "authorization", required = false) String token);

    public Result addUserRecipe(@RequestHeader(value = "authorization", required = false) String token,
                                @RequestBody AddUserRecipeDTO request);
    
    Result saveRating(String token, AddUserRecipeDTO request);

    Result deleteByUserAndRecipe(String token, AddUserRecipeDTO request);

    Result saveComment(String token, AddUserRecipeDTO request);


    Result getReferenceRecipes(String token);
}
