package com.douyin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.douyin.dto.AddUserRecipeDTO;
import com.douyin.dto.Result;
import com.douyin.dto.UserRecipeAnaDTO;
import com.douyin.entity.UserRecipe;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

public interface IUserRecipeAnaService extends IService<UserRecipeAnaDTO>{
    public Result getUserRecipesAna(@RequestHeader(value = "authorization", required = false) String token);
}
