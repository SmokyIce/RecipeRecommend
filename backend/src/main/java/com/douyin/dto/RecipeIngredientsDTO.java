package com.douyin.dto;

import lombok.Data;

@Data
public class RecipeIngredientsDTO {
    //食谱id
    private String recipeId;
    //食材份数
    private String servingCount;
    //食材单位
    private String unitName;
    //食材名称
    private String ingredientName;
}
