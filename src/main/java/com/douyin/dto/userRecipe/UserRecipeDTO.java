package com.douyin.dto.userRecipe;

import lombok.Data;


@Data
public class UserRecipeDTO {
    //评价id
    private Integer id;
    //餐品id
    private String recipeId;
    //餐品名称
    private String name;
    //图片地址
    private String image;
    //烹饪方法
    private String cookingMethod;
    //用户评分
    private int userRating;
    //评论
    private String comment;
}
