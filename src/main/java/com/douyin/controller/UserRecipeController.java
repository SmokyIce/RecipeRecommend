package com.douyin.controller;

import com.douyin.dto.AddUserRecipeDTO;
import com.douyin.dto.Result;
import com.douyin.service.IUserRecipeServer;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("userRecipes")
public class UserRecipeController {


    @Resource
    public IUserRecipeServer userRecipeServer;

    /**
     * 返回个人对食谱已完成的评价信息
     * @param token 携带用户信息
     */

    @GetMapping("/management")
    public Result getManagement(@RequestHeader(value = "authorization", required = false) String token) {
        // 返回数据
        return userRecipeServer.getUserRecipes(token);
    }

    @PostMapping("/addUserRecipe")
    public Result addUserRecipe(@RequestHeader(value = "authorization", required = false) String token,
                                @RequestBody AddUserRecipeDTO request) {
        return userRecipeServer.addUserRecipe(token, request);
    }


    //TODO 评分系统
    @PostMapping("/rating")
    public Result setRating(@RequestHeader(value = "authorization", required = false) String token){
        return Result.fail("");
    }
}
