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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author smkice
 * @since 2025-3-3
 */
@Setter
@Getter
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("users")
@Entity
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    @TableId(value = "user_id")
    @Id
    private String userId = "noname";

    /**
     * 密码，加密存储
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
    private int age = 0;

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
