
// pages/recipe/recipe.js  
Page({  
  data: {  
    recipe: {},      // 存储单个食谱详情数据  
    errorMsg: '',    // 错误提示信息
    ingredients: [], // 食材列表数据
    comments: [],    // 评论列表数据
    showIngredients: false, // 是否显示食材列表
    showComments: false     // 是否显示评论列表
  },  

  onLoad(options) {  
    const recipeId = options.id;  
    console.log("加载 recipe 页面，传入的 recipeId:", recipeId);  
    if (recipeId) {  
      this.fetchRecipesAndExtract(recipeId);  
    } else {  
      this.setData({ errorMsg: '未传入食谱ID' });  
    }  
  },  

  // 请求所有食谱数据，然后从中提取指定 recipeId 的食谱详情  
  fetchRecipesAndExtract(recipeId) {  
    tt.request({  
      // 请求返回所有食谱数据的接口  
      url: 'http://localhost:3000/recipes/recipes',  
      method: 'GET',  
      success: (res) => {  
        console.log("返回的数据：", res.data.data);  
        if (res.data.data && Array.isArray(res.data.data)) {  
          // 强制将 id 转换为字符串进行比较  
          const targetRecipe = res.data.data.find(item => String(item.recipeId) === String(recipeId));  
          if (targetRecipe) {  
            console.log("找到目标食谱：", targetRecipe);  
            this.setData({ recipe: targetRecipe });
            // 获取食谱详情成功后，立即获取评论数据
            this.fetchRecipeComments(recipeId);  
          } else {  
            this.setData({ errorMsg: '没有找到对应的食谱数据' });  
            console.error("查找失败：没有匹配的 recipeId", recipeId);  
          }  
        } else {  
          this.setData({ errorMsg: '未获取到食谱数据' });  
          console.error("返回数据格式有误，期望数组类型");  
        }  
      },  
      fail: (err) => {  
        console.error('请求所有食谱数据失败：', err);  
        this.setData({ errorMsg: '数据加载失败，请稍后重试' });  
      }  
    });  
  },

  // 请求指定食谱ID对应的食材列表数据  
  fetchRecipeIngredients(recipeId) {  
    tt.request({  
      url: 'http://localhost:3000/recipes/getIngredients',  
      method: 'GET',  
      data: { recipeId : recipeId},  
      header: { "content-type": "application/json" },  
      success: (res) => {  
        console.log("食材数据：", res.data.data);  
        if (res.data.data && Array.isArray(res.data.data)) {  
          this.setData({ ingredients: res.data.data });  
        } else {  
          tt.showToast({ title: '获取食材数据格式有误', icon: 'none' });  
        }  
      },  
      fail: (err) => {  
        tt.showToast({ title: '获取食材数据失败', icon: 'none' });  
      }  
    });  
  }, 

  // 请求指定食谱ID对应的评论数据
  fetchRecipeComments(recipeId) {
    tt.request({
      url: 'http://localhost:3000/userRecipes/getCommentByRecipeId',
      method: 'GET',
      data: { recipeId: recipeId },
      header: { "content-type": "application/json" },
      success: (res) => {
        console.log("评论数据：", res.data);
        if (res.data.success && res.data.data && Array.isArray(res.data.data)) {
          this.setData({ comments: res.data.data });
        } else {
          console.log('获取评论数据格式有误或无评论数据');
          this.setData({ comments: [] });
        }
      },
      fail: (err) => {
        console.error('获取评论数据失败：', err);
        tt.showToast({ title: '获取评论数据失败', icon: 'none' });
        this.setData({ comments: [] });
      }
    });
  },

  // 切换显示/隐藏食材列表 
  toggleIngredients() { 
    // 如果当前是隐藏，则展开前重新获取食材数据 
    if (!this.data.showIngredients && this.data.recipe.recipeId) {
      this.fetchRecipeIngredients(this.data.recipe.recipeId); 
    }
    this.setData({
      showIngredients: !this.data.showIngredients 
    }); 
  },

  // 切换显示/隐藏评论列表
  toggleComments() {
    this.setData({
      showComments: !this.data.showComments
    });
  }
});
