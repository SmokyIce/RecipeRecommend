package com.douyin.dto;

import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.Data;

@Data
public class UpdateUserDTO {
    //token前端传来
    private String token;
    //昵称
    private String nickname;
    //年龄
    private int age;
}
