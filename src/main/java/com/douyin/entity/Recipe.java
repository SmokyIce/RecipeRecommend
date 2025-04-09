package com.douyin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

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
    private BigDecimal calories;

    /**
     * 碳水化合物
     */
    private BigDecimal carbohydrate;

    /**
     * 脂肪
     */
    private BigDecimal fat;

    /**
     * 蛋白质
     */
    private BigDecimal protein;

    /**
     *
     */
    private String instructions;

    /**
     * 盐
     */
    private BigDecimal salt;

    /**
     * 推荐程度
     */
    private BigDecimal rating;

    // 将 featureTags JSON 字符串解析为 List<String>
    public List<String> getFeatureTagsList() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(featureTags, new TypeReference<List<String>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
