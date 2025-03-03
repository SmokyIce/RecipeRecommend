package com.douyin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author smkice
 * @since
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long userId;

    /**
     * 密码，加密存储
     */
    private String password;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 用户年龄
     */
    private int age;

    /**
     * 餐品偏好
     */
    private String recipePreferences = "none";

    /**
     * 手机
     */
    private String phone = "none";

}
