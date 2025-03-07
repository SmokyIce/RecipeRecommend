package com.douyin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Id;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user_recipes")
public class UserRecipe {
    //餐品id
    @Id
    private int id;
    //餐品名称
    private String userId;
    //图片地址
    private String recipeId;
    //用户评分
    private int userRating;
    //评论
    private String comment;
}
