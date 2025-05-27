// e:\douyin\miniprogram\blank1\pages\analysis\analysis.js  
Page({  
  data: {  
    recipes: [],  
    breakfastData: { totalCalories: 0, totalCarbohydrate: 0, totalSalt: 0, recipes: [], evaluation: '' },  
    lunchData: { totalCalories: 0, totalCarbohydrate: 0, totalSalt: 0, recipes: [], evaluation: '' },  
    dinnerData: { totalCalories: 0, totalCarbohydrate: 0, totalSalt: 0, recipes: [], evaluation: '' },  
    overallEvaluation: '' // 今日食谱总评  
  },  
  onLoad: function (options) {  
    tt.setNavigationBarTitle({ title: "智能分析" });  
    this.loadRecipes();  
    this.loadEvaluations();  
  },  
  // 加载用户食谱数据  
  loadRecipes() {  
    const token = tt.getStorageSync("authorization");  
    tt.request({  
      url: "http://localhost:3000/userRecipesAna/anaData",  
      method: "GET",  
      header: {  
        "authorization": token  
      },  
      success: (res) => {  
        if (res.data.data && Array.isArray(res.data.data)) {  
          this.setData({ recipes: res.data.data });  
          this.processRecipes(res.data.data);  
        } else {
          console.log("fail callback", res); 
          const sampleRecipes = [  
            {  
              recipeId: 'r001',  
              name: '番茄炒蛋',  
              image: '/images/tomato-egg.jpg',  
              cookingMethod: '炒',  
              suitableTime: '早餐',  
              featureTags: [],  
              calories: 200,  
              carbohydrate: 10,  
              salt: 5  
            },  
            {  
              recipeId: 'r002',  
              name: '麻婆豆腐',  
              image: '/images/mapo-tofu.jpg',  
              cookingMethod: '煮',  
              suitableTime: '午餐',  
              featureTags: [],  
              calories: 300,  
              carbohydrate: 5,  
              salt: 8  
            }, 
            {  
              recipeId: 'r0026',  
              name: '章霖',  
              image: '/images/mapo-tofu.jpg',  
              cookingMethod: '爆炒',  
              suitableTime: '早餐',  
              featureTags: [],  
              calories: 1300,  
              carbohydrate: 115,  
              salt: 18  
            },   
            {  
              recipeId: 'r003',  
              name: '红烧牛肉',  
              image: '/images/beef-stew.jpg',  
              cookingMethod: '炖',  
              suitableTime: '晚餐',  
              featureTags: [],  
              calories: 500,  
              carbohydrate: 15,  
              salt: 10  
            }  
          ];  
          this.setData({ recipes: sampleRecipes });  
          this.processRecipes(sampleRecipes);  
        }  
      },  
      fail: (res) => {  
        // 请求失败时使用临时示例数据  
        const sampleRecipes = [  
          {  
            recipeId: 'r001',  
            name: '番茄炒蛋',  
            image: '/images/tomato-egg.jpg',  
            cookingMethod: '炒',  
            suitableTime: '早餐',  
            featureTags: [],  
            calories: 200,  
            sugar: 10,  
            salt: 5  
          },  
          {  
            recipeId: 'r002',  
            name: '麻婆豆腐',  
            image: '/images/mapo-tofu.jpg',  
            cookingMethod: '煮',  
            suitableTime: '午餐',  
            featureTags: [],  
            calories: 300,  
            sugar: 5,  
            salt: 8  
          },  
          {  
            recipeId: 'r003',  
            name: '红烧牛肉',  
            image: '/images/beef-stew.jpg',  
            cookingMethod: '炖',  
            suitableTime: '晚餐',  
            featureTags: [],  
            calories: 500,  
            sugar: 15,  
            salt: 10  
          }  
        ];  
        this.setData({ recipes: sampleRecipes });  
        this.processRecipes(sampleRecipes);  
      }  
    });  
  },  
  // 加载评价数据：包含早餐、午餐、晚餐评价以及今日食谱总评  
  loadEvaluations() {  
    const token = tt.getStorageSync("authorization");  
    tt.request({  
      url: "http://localhost:3000/userRecipes/intellectComment",  
      method: "GET",  
      header: {  
        "authorization": token  
      },
      success: (res) => {  
        if (res.data.data) {  
          // 假设返回结构为：{ breakfast: "...", lunch: "...", dinner: "...", overall: "..." }   
          const commentData = res.data.data;  
          // 从 data 更新各食谱评价  
          this.setData({  
            "breakfastData.evaluation": commentData.breakfast || "早餐评价：暂无数据",  
            "lunchData.evaluation": commentData.lunch || "午餐评价：暂无数据",  
            "dinnerData.evaluation": commentData.dinner || "晚餐评价：暂无数据",  
            overallEvaluation: commentData.overall || "今日食谱总评：暂无数据"  
          });  
        } else {  
          // 返回数据异常时设置默认评价  
          this.setData({  
            "breakfastData.evaluation": "早餐评价：暂无数据",  
            "lunchData.evaluation": "午餐评价：暂无数据",  
            "dinnerData.evaluation": "晚餐评价：暂无数据",  
            overallEvaluation: "暂无数据"  
          });  
        }  
      },  
      fail: (res) => { 
        console.log("fail callback", res),  
        // 请求失败时设置默认评价文案  
        this.setData({  
          "breakfastData.evaluation": "暂无数据",  
          "lunchData.evaluation": "暂无数据",  
          "dinnerData.evaluation": "暂无数据",  
          overallEvaluation: "暂无数据"  
        });  
      }  
    });  
  },  
  // 根据 suitableTime 分组并统计各指标，同时初始化评价（待 loadEvaluations 更新）  
  processRecipes(recipes) {  
    let breakfast = { totalCalories: 0, totalCarbohydrate: 0, totalSalt: 0, recipes: [] };  
    let lunch = { totalCalories: 0, totalCarbohydrate: 0, totalSalt: 0, recipes: [] };  
    let dinner = { totalCalories: 0, totalCarbohydrate: 0, totalSalt: 0, recipes: [] };  

    recipes.forEach(recipe => {  
      if (recipe.suitableTime === '早餐') {  
        breakfast.totalCalories += (recipe.calories || 0);  
        breakfast.totalCarbohydrate += (recipe.carbohydrate || 0);  
        breakfast.totalSalt += (recipe.salt || 0);  
        breakfast.recipes.push(recipe);  
      } else if (recipe.suitableTime === '午餐') {  
        lunch.totalCalories += (recipe.calories || 0);  
        lunch.totalCarbohydrate += (recipe.carbohydrate || 0);  
        lunch.totalSalt += (recipe.salt || 0);  
        lunch.recipes.push(recipe);  
      } else if (recipe.suitableTime === '晚餐') {  
        dinner.totalCalories += (recipe.calories || 0);  
        dinner.totalCarbohydrate += (recipe.carbohydrate || 0);  
        dinner.totalSalt += (recipe.salt || 0);  
        dinner.recipes.push(recipe);  
      }  
    });  

    this.setData({  
      breakfastData: breakfast,  
      lunchData: lunch,  
      dinnerData: dinner  
    });  
  }  
});  