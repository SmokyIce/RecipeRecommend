package com.douyin.utils;

import com.douyin.entity.Recipe;
import com.douyin.entity.UserRecipe;

import java.util.*;
import java.util.stream.Collectors;

public class RecipeUtil {
    /**
     * 计算两组 feature_tags 之间的 Jaccard 相似度
     */
    public static double calculateJaccardSimilarity(List<String> tags1, List<String> tags2) {
        if(tags1.isEmpty() || tags2.isEmpty()){
            return 0.0;
        }
        Set<String> set1 = new HashSet<>(tags1);
        Set<String> set2 = new HashSet<>(tags2);
        Set<String> intersection = set1.stream()
                .filter(set2::contains)
                .collect(Collectors.toSet());
        Set<String> union = new HashSet<>(set1);
        union.addAll(set2);
        return (double) intersection.size() / union.size();
    }

    /**
     * 为用户推荐食谱：基于用户选过的食谱 feature_tags，计算候选食谱相似度
     *
     * @param userId            用户ID
     * @param allRecipes        所有食谱数据列表
     * @param userRecipeRecords 用户选择记录列表
     * @return 推荐的食谱列表
     */
    public static List<Recipe> recommendRecipes(String userId, List<Recipe> allRecipes, List<UserRecipe> userRecipeRecords) {
        // 筛选出该用户已经选择的食谱ID
        Set<String> userSelectedRecipeIds = userRecipeRecords.stream()
                .filter(ur -> ur.getUserId().equals(userId))
                .map(UserRecipe::getRecipeId)
                .collect(Collectors.toSet());

        // 获取该用户选中过的所有食谱的 feature_tags 列表
        List<List<String>> userFeatureTagsList = allRecipes.stream()
                .filter(recipe -> userSelectedRecipeIds.contains(recipe.getRecipeId()))
                .map(Recipe::getFeatureTagsList)
                .collect(Collectors.toList());

        // 筛选出候选的食谱（排除已选食谱）
        List<Recipe> candidateRecipes = allRecipes.stream()
                .filter(recipe -> !userSelectedRecipeIds.contains(recipe.getRecipeId()))
                .collect(Collectors.toList());

        // 为每个候选食谱计算与用户选中食谱的最大相似度
        Map<Recipe, Double> recipeSimilarityMap = new HashMap<>();
        for (Recipe candidate : candidateRecipes) {
            List<String> candidateTags = candidate.getFeatureTagsList();
            double bestSimilarity = 0.0;
            for (List<String> userTags : userFeatureTagsList) {
                double similarity = calculateJaccardSimilarity(candidateTags, userTags);
                if (similarity > bestSimilarity) {
                    bestSimilarity = similarity;
                }
            }
            recipeSimilarityMap.put(candidate, bestSimilarity);
        }

        // 根据相似度降序排序，只推荐相似度大于 0 的食谱（阈值可根据需要调整）
        List<Recipe> recommendations = recipeSimilarityMap.entrySet().stream()
                .filter(entry -> entry.getValue() > 0)
                .sorted(Map.Entry.<Recipe, Double>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        return recommendations;
    }
}
