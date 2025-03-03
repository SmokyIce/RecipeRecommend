package com.douyin.dto;

import cn.hutool.json.JSON;
import lombok.Data;

@Data
public class UserDTO {
    private String user_id;
    private String nickname;
    private int age;
    private JSON recipePreferences;}
