package com.douyin.mapper;

import com.douyin.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @since 2025-3-3
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Update("UPDATE users set recipe_preferences = #{recipe_preferences} where user_id = #{user_id}")
    int updatePreference(@Param("recipe_preferences")String recipe_preferences, @Param("user_id") String user_id);

}
