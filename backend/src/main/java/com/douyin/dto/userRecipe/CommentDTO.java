package com.douyin.dto.userRecipe;

import lombok.Data;

@Data
public class CommentDTO {
    private String recipeId;
    private String comment;
    private String userId;
    private String userName;
}
