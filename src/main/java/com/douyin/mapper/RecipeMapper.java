package com.douyin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.douyin.entity.Recipe;
import org.apache.ibatis.annotations.Mapper;

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
}
