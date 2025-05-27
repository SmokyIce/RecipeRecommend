package com.douyin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.douyin.dto.userRecipe.CommentDTO;
import com.douyin.dto.userRecipe.UserRecipeDTO;
import com.douyin.entity.User;
import com.douyin.entity.UserRecipe;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserRecipeMapper extends BaseMapper<UserRecipe> {
    /**
     * 根据用户ID查询用户食谱关联信息（两表关联查询）
     * @param userId 用户ID
     * @return 用户对应的菜谱记录集合
     */
    List<UserRecipeDTO> getUserRecipes(@Param("userId") String userId);

    @Update("UPDATE user_recipes set user_rating = #{rating} where recipe_id = #{recipeId} and user_id = #{userId}")
    int updateRating(@Param("recipeId")String recipeId,@Param("userId") String userId,@Param("rating") Integer rating);

    @Delete("DELETE from user_recipes where user_id = #{userId} and recipe_id = #{recipeId}")
    int deleteAUserRecipe(@Param("userId") String userId, @Param("recipeId") String recipeId);

    @Update("UPDATE user_recipes set comment = #{comment} where recipe_id = #{recipeId} and user_id = #{userId}")
    int updateComment(@Param("userId") String userId, @Param("comment") String comment, @Param("recipeId") String recipeId);

    @Select("SELECT * FROM user")
    User getUser(String token);


    List<CommentDTO> getComment(String recipeId);
}