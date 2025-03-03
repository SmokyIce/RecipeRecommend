package com.douyin.entity;

import cn.hutool.json.JSON;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

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
    @TableId(value = "id", type = IdType.AUTO)
    private String id;

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
