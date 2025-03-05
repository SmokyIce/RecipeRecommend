package com.douyin.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDTO {
    private String password;
    private String nickname;
    private String phone;

}
