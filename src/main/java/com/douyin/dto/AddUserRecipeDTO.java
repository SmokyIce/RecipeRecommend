package com.douyin.dto;

import lombok.Data;

@Data
public class AddUserRecipeDTO {

    // 用户 ID，由前端传入
    private String userId;

    // 食谱 ID，由前端传入
    private String recipeId;
}
