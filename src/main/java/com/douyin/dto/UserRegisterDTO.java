package com.douyin.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserRegisterDTO {
    private String password;
    private String nickname;
    private String phone;

}
