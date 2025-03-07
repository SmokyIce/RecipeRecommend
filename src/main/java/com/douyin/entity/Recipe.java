package com.douyin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 
 * </p>
 *
 * @since 2025-3-3
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("recipes")
public class Recipe implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "recipe_id", type = IdType.AUTO)
    private String recipeId;

    /**
     * 菜品名称
     */
    private String name;

    /**
     * 图像地址
     */
    private String image;

    /**
     * 制作方式
     */
    private String cookingMethod;

    /**
     * 适用时间
     */
    private String suitableTime;

    /**
     * 特点
     */
    @JsonRawValue
    private String featureTags;

    /**
     * 卡路里
     */
    private int calories;

    /**
     * 糖分
     */
    private int sugar;

    /**
     * 盐
     */
    private BigDecimal salt;

    /**
     * 推荐程度
     */
    private BigDecimal rating;


    /**
     * 建议
     */
    private String instructions;


}
