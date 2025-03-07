package com.douyin.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.douyin.dto.Result;
import com.douyin.dto.UserRecipeDTO;
import com.douyin.dto.UserRegisterDTO;
import com.douyin.entity.Recipe;
import com.douyin.entity.UserRecipe;
import com.douyin.mapper.UserRecipeMapper;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

public interface IRecipeService extends IService<Recipe>{

    }
