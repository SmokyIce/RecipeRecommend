package com.douyin.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.douyin.dto.RecipeIngredientsDTO;
import com.douyin.dto.Result;
import com.douyin.entity.Recipe;
import com.douyin.mapper.RecipeMapper;
import com.douyin.mapper.UserRecipeMapper;
import com.douyin.service.IRecipeService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
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

    @Resource
    private final RecipeMapper recipeMapper;

    @Resource
    private final StringRedisTemplate stringRedisTemplate;

    public RecipeServiceImpl(RecipeMapper recipeMapper, StringRedisTemplate stringRedisTemplate) {
        this.recipeMapper = recipeMapper;
        this.stringRedisTemplate = stringRedisTemplate;
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

    @Override
    public Result getAllRecipes() {
        //先到redis中查找
        List<Recipe> typeList = JSONUtil.toList(stringRedisTemplate.opsForValue().get("recipes"), Recipe.class) ;
        //没有则查询数据库
        if (typeList == null || typeList.isEmpty()) {
            typeList = query().list();
            //将数据存入redis中
            stringRedisTemplate.opsForValue().set("recipes", JSONUtil.toJsonStr(typeList));
        }

        return typeList == null || typeList.isEmpty() ? Result.fail("获取所有食谱失败") : Result.ok(typeList);
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
