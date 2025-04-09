package com.douyin.controller;

import com.douyin.dto.AddUserRecipeDTO;
import com.douyin.dto.Result;
import com.douyin.service.IUserRecipeAnaService;
import com.douyin.service.IUserRecipeServer;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("userRecipesAna")
public class UserRecipeAnaController {

    @Resource
    public IUserRecipeAnaService userRecipeAnaService;

    /**
     * 返回个人对食谱已完成的评价信息
     * @param token 携带用户信息
     */

    @GetMapping("/anaData")
    public Result getAnaData(@RequestHeader(value = "authorization", required = false) String token) {
        // 返回数据
        return userRecipeAnaService.getUserRecipesAna(token);
    }

}
