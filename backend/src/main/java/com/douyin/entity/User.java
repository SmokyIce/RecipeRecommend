package com.douyin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author smkice
 * @since 2025-3-3
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("users")
public class User implements Serializable {


    /**
     * 用户名
     */
    @TableId(value = "user_id")
    @Id
    private String userId = "noname";

    /**
     * 密码
     */
    @Column(nullable = false)
    private String password = null;

    /**
     * 昵称
     */
    @Column(nullable = false)
    private String nickname = null;

    /**
     * 用户年龄
     */
    @Column(nullable = false)
    private Integer age = 0;

    /**
     * 餐品偏好
     */
    @Column(nullable = false)
    @JsonRawValue
    private String recipePreferences = null;

    /**
     * 手机
     */
    @Column(unique = true, nullable = false)
    private String phone = null;

}
