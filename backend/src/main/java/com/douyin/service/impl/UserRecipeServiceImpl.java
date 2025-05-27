package com.douyin.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.douyin.dto.AddUserRecipeDTO;
import com.douyin.dto.Result;
import com.douyin.dto.userRecipe.CommentDTO;
import com.douyin.dto.userRecipe.UserRecipeDTO;
import com.douyin.entity.Recipe;
import com.douyin.entity.UserRecipe;
import com.douyin.mapper.RecipeMapper;
import com.douyin.mapper.UserRecipeMapper;
import com.douyin.service.IUserRecipeServer;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.douyin.utils.RecipeUtil.recommendRecipes;

@Service
public class UserRecipeServiceImpl extends ServiceImpl<UserRecipeMapper, UserRecipe> implements IUserRecipeServer{

    @Autowired
    private final UserRecipeMapper userRecipeMapper;
    @Resource
    private final RecipeMapper recipeMapper;

    @Resource
    private final ChatClient chatClient;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public UserRecipeServiceImpl(UserRecipeMapper userRecipeMapper, RecipeMapper recipeMapper, ChatClient chatClient) {
        this.userRecipeMapper = userRecipeMapper;
        this.recipeMapper = recipeMapper;
        this.chatClient = chatClient;
    }

    @Override
    public Result deleteByUserAndRecipe(String token, AddUserRecipeDTO request) {
        if(StrUtil.isEmpty(token) || StrUtil.isEmpty(request.getRecipeId())){
            return Result.fail("传入错误数据");
        }
        int i = userRecipeMapper.deleteAUserRecipe(token, request.getRecipeId());
        return i == 1 ? Result.ok("删除成功！") : Result.fail("删除失败，请确认是否已删除");
    }

    @Override
    public Result saveComment(String token, AddUserRecipeDTO request) {
        if(StrUtil.isEmpty(token) || request.getRecipeId() == null || request.getComment().isEmpty()){
            return Result.fail("修改失败！数据提交异常");
        }
        int i = userRecipeMapper.updateComment(token, request.getComment(), request.getRecipeId());
        return i == 1 ? Result.ok("修改成功") : Result.fail("修改失败");
    }

    @Override
    public Result getReferenceRecipes(String token) {
        //食谱获取
        List<Recipe> recipes = recipeMapper.getRecipes();
        //用户获取
        List<UserRecipe> userRecipes = query().list();

        // 根据用户已选记录，做推荐
        List<Recipe> recommendations = recommendRecipes(token, recipes, userRecipes);

        if (recommendations.isEmpty()) {
            Result.fail("没有合适的推荐");
        }
        return Result.ok(recommendations);
    }

    @Override
    public Result getCommentByRecipeId(String recipeId) {
        if (StrUtil.isBlank(recipeId)) {
            return Result.fail("请输入正确的参数");
        }
        List<CommentDTO> comment = userRecipeMapper.getComment(recipeId);
        if (comment.isEmpty()) {
            return Result.fail("没有找到评论");
        }
        return Result.ok(comment);
    }

    @Override
    public Result getIntellectComment(String token) {
        if (StrUtil.isBlank(token)) {
            return Result.fail("请重新登录");
        }
        List<Recipe> data =(List<Recipe>) getReferenceRecipes(token).getData();
        String s = stringRedisTemplate.opsForValue().get("intellectComment" + token);
        if(StrUtil.isNotBlank(s)) {
            return Result.ok(s);
        }

        if(Boolean.TRUE.equals(stringRedisTemplate.opsForValue().setIfAbsent("lockInte", "1"))){
            stringRedisTemplate.expire("lockInte", 5, TimeUnit.MINUTES);
            new Thread(() -> {
                try{
                    if(data.isEmpty()) throw new RuntimeException("数据获取失败");
                    System.out.println(data);
                    String content = chatClient.prompt()
                            .user("介绍" + data.get(0).getName()  + "，" + data.get(1).getName()  + "，" + data.get(2).getName()  + "，" + "这几个食谱的优点")
                            .call()
                            .content();
                    if (StrUtil.isNotBlank(content)) {
                        stringRedisTemplate.opsForValue().set("intellectComment" + token, content);
                        stringRedisTemplate.expire("intellectComment" + token, 12, TimeUnit.HOURS);
                    }
                }finally {
                    stringRedisTemplate.delete("lockInte");
                }
            }).start();
        }
        return Result.ok("正在获取中...请稍后");
    }


    @Override
    public Result getUserRecipes(String token) {
        //1.根据token查询是否存在用户
        if (StrUtil.isBlank(token)) {
            //2.不存在返回请登录
            return Result.fail("请重新登录");
        }
        //3.获取，用list<UserRecipeDTO>接收
        List<UserRecipeDTO> userRecipes = userRecipeMapper.getUserRecipes(token);
        return Result.ok(userRecipes);
    }


    /**
     * 修改用户评分
     * @param token 用户id
     * @param request 食谱信息和评分
     * @return 是否成功
     */
    @Override
    public Result saveRating(String token, AddUserRecipeDTO request) {
        if (StrUtil.isBlank(token)) {
            return Result.fail("未登录");
        }
        if (request == null) {
            return Result.fail("信息未提交");
        }
        int i = userRecipeMapper.updateRating(request.getRecipeId(), token, request.getRating());

        return i == 1 ? Result.ok("修改成功") : Result.fail("修改失败，或许未添加此食谱");
    }

    @Override
    public Result addUserRecipe(String token, AddUserRecipeDTO request) {
        if(request.getRecipeId() == null || token == null) {
            return Result.fail("获取不到食谱id或未登录");
        }
        UserRecipe one = query().eq("user_id", request.getUserId()).eq("recipe_id", request.getRecipeId()).one();
        if(one != null){
            return Result.fail("食谱已经添加过了！");
        }
        UserRecipe userRecipe = new UserRecipe()
                .setRecipeId(request.getRecipeId())
                .setUserId(request.getUserId())
                .setUserRating(0)
                .setComment("");
        int insert = userRecipeMapper.insert(userRecipe);
        return Result.ok(insert == 1 ? "添加成功" : "添加失败");
    }
}
