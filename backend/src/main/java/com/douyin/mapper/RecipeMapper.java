package com.douyin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.douyin.dto.RecipeIngredientsDTO;
import com.douyin.entity.Recipe;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *     Mapper 接口
 * </p>
 *
 * @since 2025-3-3
 */
@Mapper
public interface RecipeMapper extends BaseMapper<Recipe> {
    //自动提供CRUD
    List<RecipeIngredientsDTO> getIngredientsDTO(@Param("recipeId") String recipeId);

    List<Recipe> getRecipes();
}
