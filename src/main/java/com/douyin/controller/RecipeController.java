package com.douyin.controller;

import cn.hutool.json.JSONUtil;
import com.douyin.dto.Result;
import com.douyin.entity.Recipe;
import com.douyin.service.IRecipeService;
import com.douyin.service.IUserRecipeServer;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recipes")
public class RecipeController {

    @Resource
    public IRecipeService recipeService;
    @Resource
    public IUserRecipeServer userRecipeServer;
    /**
     * 获得全部餐品的信息
     */
    @GetMapping("recipes")
    public Result getRecipes() {
        List<Recipe> typeList = recipeService
                .query().list();
        JSONUtil.toJsonStr(typeList);
        return Result.ok(typeList);
    }

    /**
     * 返回个人对食谱已完成的评价信息
     * @param token 携带用户信息
     */

    @GetMapping("/management")
    public Result getManagement(@RequestHeader(value = "authorization", required = false) String token) {
        // 返回数据
        return userRecipeServer.getRecipes(token);
    }

    @PostMapping("/rating")
    public Result setRating(@RequestHeader(value = "authorization", required = false) String token){
        return Result.fail("");
    }
}