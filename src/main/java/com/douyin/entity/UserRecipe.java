package com.douyin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user_recipes")
public class UserRecipe {
    //评价id
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    //餐品名称
    @TableField(value = "user_id")
    private String userId;
    //图片地址
    @TableField(value = "recipe_id")
    private String recipeId;
    //用户评分
    @TableField(value = "user_rating")
    private Integer userRating;
    //评论
    @TableField(value = "comment")
    private String comment;
}
