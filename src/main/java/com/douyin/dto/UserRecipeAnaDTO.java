package com.douyin.dto;

import lombok.Data;


@Data
public class UserRecipeAnaDTO {
    //评价id
    private Integer id;
    //餐品id
    private String recipeId;
    //餐品名称
    private String name;
    private String image;  // 图片路径
    //制作方法
    private String cookingMethod;
    //适合时间
    private String suitableTime;
    //卡路里
    private Double calories;
    //碳水化合物
    private Double carbohydrate;
    //盐分
    private Double salt;
}
