package com.douyin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.douyin.dto.UserRecipeDTO;
import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserRecipeMapper extends MPJBaseMapper<UserRecipeDTO> {
    /**
     * 根据用户ID查询用户食谱关联信息（两表关联查询）
     * @param userId 用户ID
     * @return 用户对应的菜谱记录集合
     */

    List<UserRecipeDTO> getUserRecipes(@Param("userId") String userId);
}