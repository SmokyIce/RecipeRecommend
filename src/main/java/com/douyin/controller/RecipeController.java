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

}