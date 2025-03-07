package com.douyin.controller;

import cn.hutool.json.JSONUtil;
import com.douyin.dto.Result;
import com.douyin.entity.Recipe;
import com.douyin.service.IRecipeService;
import com.douyin.service.IUserRecipeServer;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/recipes")
public class RecipeController {

    @Resource
    public IRecipeService recipeService;
    @Resource
    public IUserRecipeServer userRecipeServer;
    /**
     * 获得全部餐品
     */
    @GetMapping("recipes")
    public Result getRecipes() {
        List<Recipe> typeList = recipeService
                .query().list();
        JSONUtil.toJsonStr(typeList);
        return Result.ok(typeList);
    }

    @GetMapping("/management")
    public Result getManagement(@RequestHeader(value = "authorization", required = false) String token) {
        // 返回数据
        return userRecipeServer.getRecipes(token);
    }
}