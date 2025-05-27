package com.douyin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.douyin.dto.userRecipe.UserRecipeAnaDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper

public interface UserRecipeAnaMapper extends BaseMapper<UserRecipeAnaDTO> {
    List<UserRecipeAnaDTO> getUserRecipesAna(@Param("userId") String userId);
}