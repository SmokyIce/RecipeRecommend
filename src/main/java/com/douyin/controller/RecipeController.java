package com.douyin.controller;

import com.douyin.dto.Result;
import com.douyin.entity.Recipe;
import com.douyin.service.IRecipeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/index")
public class RecipeController {

    @Resource
    public IRecipeService recipeService;
    /**
     * 获得全部餐品
     */
    @GetMapping("recipes")
    public Result getRecipes() {
        List<Recipe> typeList = recipeService
                .query().list();
        for (Recipe recipe : typeList) {
            recipe.setFeatureTags(recipe.getFeatureTags().replace("\\",""));
        }
        return Result.ok(typeList);
    }

}