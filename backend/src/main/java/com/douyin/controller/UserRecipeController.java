package com.douyin.controller;

import com.douyin.dto.AddUserRecipeDTO;
import com.douyin.dto.Result;
import com.douyin.service.IUserRecipeServer;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/getCommentByRecipeId")
    public Result getCommentByRecipeId(@RequestParam("recipeId") String recipeId) {
        return userRecipeServer.getCommentByRecipeId(recipeId);
    }

    /**
     * 
     * @param token 用户名称
     * @param request 修改的信息
     * @return 修改结果
     */
    @PostMapping("/addUserRecipe")
    public Result addUserRecipe(@RequestHeader(value = "authorization", required = false) String token,
                                @RequestBody AddUserRecipeDTO request) {
        return userRecipeServer.addUserRecipe(token, request);
    }


    //评分系统
    @PostMapping("/saveRating")
    public Result setRating(@RequestHeader(value = "authorization", required = false) String token,
                            @RequestBody AddUserRecipeDTO request){
        return userRecipeServer.saveRating(token, request);
    }

    //评分系统
    @PostMapping("/saveComment")
    public Result saveComment(@RequestHeader(value = "authorization", required = false) String token,
                            @RequestBody AddUserRecipeDTO request){
        return userRecipeServer.saveComment(token, request);
    }


    @PostMapping("/delete")
    public Result deleteUserRecipe(@RequestHeader(value = "authorization", required = false) String token,
                                   @RequestBody AddUserRecipeDTO request){
        return userRecipeServer.deleteByUserAndRecipe(token, request);
    }

    @GetMapping("/recommend")
    public Result getReference(@RequestHeader(value = "authorization", required = false) String token){
        return userRecipeServer.getReferenceRecipes(token);
    }

    @GetMapping("/intellectComment")
    public Result getIntellectComment(@RequestHeader(value = "authorization", required = false) String token){
        return userRecipeServer.getIntellectComment(token);
    }
}
