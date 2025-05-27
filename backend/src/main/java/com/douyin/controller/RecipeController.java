package com.douyin.controller;

import cn.hutool.json.JSONUtil;
import com.douyin.dto.RecipeIngredientsDTO;
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
        return recipeService.getAllRecipes();
    }

    @GetMapping("/daily")
    public Result getDailyRecommendations() {
        try {
            List<Recipe> recommendations = recipeService.getDailyRecipes();
            return recommendations.isEmpty()
                    ? Result.fail("今日推荐暂不可用")
                    : Result.ok(recommendations);
        } catch (Exception e) {
            return Result.fail("推荐服务异常: " + e.getMessage());
        }
    }
    // 获取食谱食材
    @GetMapping("getIngredients")
    public Result getIngredients(@RequestParam("recipeId") String recipeId){

        return recipeService.getIngredientsByRecipeId(recipeId);
    }
}