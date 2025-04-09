package com.douyin.dto;

import lombok.Data;

@Data
public class AddUserRecipeDTO {

    // 用户 ID，由前端传入
    private String userId;

    // 食谱 ID，由前端传入
    private String recipeId;

    // 食谱评分
    private Integer rating;

    // 食谱评价
    private String comment;
}
