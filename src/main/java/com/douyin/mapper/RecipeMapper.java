package com.douyin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.douyin.dto.UserRecipeDTO;
import com.douyin.entity.Recipe;
import com.github.yulichang.base.MPJBaseMapper;

/**
 * <p>
 *     Mapper 接口
 * </p>
 *
 * @since 2025-3-3
 */
public interface RecipeMapper extends BaseMapper<Recipe> {
    //自动提供CRUD
}
