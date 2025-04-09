package com.douyin.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.douyin.dto.RecipeIngredientsDTO;
import com.douyin.dto.Result;
import com.douyin.entity.Recipe;
import com.douyin.mapper.RecipeMapper;
import com.douyin.mapper.UserRecipeMapper;
import com.douyin.service.IRecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.*;
import java.util.stream.Collectors;
import java.util.List;
// 关键导包！必须添加这两行
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import cn.hutool.core.collection.CollectionUtil; // 必须严格匹配
@Service
public class RecipeServiceImpl extends ServiceImpl<RecipeMapper, Recipe> implements IRecipeService {

    @Autowired
    private final RecipeMapper recipeMapper;

    public RecipeServiceImpl(RecipeMapper recipeMapper) {
        this.recipeMapper = recipeMapper;
    }


    /**
     * 根据食谱id获取全部食材
     * @param recipeId 食谱信息
     * @return 食材数组
     */
    @Override
    public Result getIngredientsByRecipeId(String recipeId) {
        if(StrUtil.isBlank(recipeId)){
            return Result.fail("获取食谱失败");
        }
        List<RecipeIngredientsDTO> ingredients = recipeMapper.getIngredientsDTO(recipeId);
        //获取到食材返回
        if(!ingredients.isEmpty()){
            return Result.ok(ingredients);
        }

        return Result.fail("获取食材信息失败");
    }

    @Override
    public List<Recipe> getDailyRecipes() {
        // 定义各餐段营养限制
        Map<String, NutritionRange> mealConfig = new LinkedHashMap<>() {{
            put("早餐", new NutritionRange(100, 600, 100));
            put("午餐", new NutritionRange(200, 900, 100));
            put("晚餐", new NutritionRange(300, 1000, 100));
        }};

        return mealConfig.keySet().stream()
                .map(mealType -> {
                    // 构建动态查询
                    QueryWrapper<Recipe> query = new QueryWrapper<>();
                    query.eq("suitable_time", mealType)
                            .between("calories",
                                    mealConfig.get(mealType).minCalories,
                                    mealConfig.get(mealType).maxCalories)
                            .le("carbohydrate", mealConfig.get(mealType).maxCarbohydrate);

                    // 获取候选列表
                    List<Recipe> candidates = baseMapper.selectList(query);

                    // 随机选择（可加入评分权重）
                    return CollectionUtil.isEmpty(candidates) ? null : randomPick(candidates);
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    // 随机选择算法
    private Recipe randomPick(List<Recipe> candidates) {
        Collections.shuffle(candidates);
        return candidates.get(0);
    }

    // 营养范围内部类
    @Data
    @AllArgsConstructor
    private static class NutritionRange {
        private Integer minCalories;
        private Integer maxCalories;
        private Integer maxCarbohydrate;
    }



}
