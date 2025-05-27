// pages/search/search.js  
Page({  
  data: {  
    searchText: "",           // 搜索框输入的内容  
    recipes: [],              // 从数据库获取的所有食谱数据  
    filteredRecipes: [],      // 根据搜索内容过滤后的食谱数据  
    errorMsg: ""              // 存储错误信息  
  },  

  onLoad() {  
    this.fetchRecipes();  
  },  

  // 从数据库接口获取食谱数据  
  fetchRecipes() {  
    tt.request({  
      // 请确保在开发者工具或真机中该域名已添加到安全域名白名单  
      url: 'http://localhost:3000/recipes/recipes',  
      method: 'GET',  
      success: (res) => {  
        if (res.data.data && Array.isArray(res.data.data)) {  
          this.setData({  
            recipes: res.data.data,  
            filteredRecipes: res.data.data  
          });  
        } else {  
          this.setData({  
            errorMsg: '未获取到食谱数据'  
          });  
        }  
      },  
      fail: (err) => {  
        console.error('请求 API 数据失败：', err);  
        this.setData({  
          errorMsg: '数据加载失败，请稍后重试'  
        });  
      }  
    });  
  },  

  // 监听搜索输入框的内容  
  onSearchInput(e) {  
    const searchText = e.detail.value.toLowerCase();  
    this.setData({ searchText }, () => {  
      this.filterRecipes();  
    });  
  },  

  // 根据搜索文本过滤展示数据  
  filterRecipes() {  
    const { recipes, searchText } = this.data;  
    if (!searchText) {  
      this.setData({ filteredRecipes: recipes });  
      return;  
    }  
    const filtered = recipes.filter(recipe => {  
      let match = false;  
      // 检查 id、name、cookingMethod、suitableTime  
      match = match || String(recipe.recipeId).toLowerCase().includes(searchText);  
      match = match || recipe.name.toLowerCase().includes(searchText);  
      match = match || recipe.cookingMethod.toLowerCase().includes(searchText);  
      match = match || recipe.suitableTime.toLowerCase().includes(searchText);  
      // 检查 featureTags（假设 featureTags 为数组）  
      if (recipe.featureTags && Array.isArray(recipe.featureTags)) {  
        match = match || recipe.featureTags.some(tag =>  
          tag.toLowerCase().includes(searchText)  
        );  
      }  
      // 检查营养信息  
      match = match || (recipe.calories && String(recipe.calories).toLowerCase().includes(searchText));  
      match = match || (recipe.carbohydrate && String(recipe.carbohydrate).toLowerCase().includes(searchText));  
      match = match || (recipe.salt && String(recipe.salt).toLowerCase().includes(searchText));  

      return match;  
    });  
    this.setData({ filteredRecipes: filtered });  
  },  

  // 点击食谱项时跳转到详情页面  
  goToRecipe(e) {  
    const recipeId = e.currentTarget.dataset.id;  
    tt.navigateTo({  
      url: `/pages/recipe/recipe?id=${recipeId}`  
    });  
  },  

  // 点击“添加到我的食谱”按钮，将该食谱添加到用户的食谱表中  
  handleAddRecipe(e) {  
    // 防止触发父元素的跳转事件  
    const recipeId = e.currentTarget.dataset.id;  
    const token = tt.getStorageSync("authorization");  
    if (!token) {  
      tt.showToast({  
        title: '请先登录',  
        icon: 'none'  
      });  
      return;  
    }  
    tt.request({  
      url: 'http://localhost:3000/userRecipes/addUserRecipe',  
      method: 'POST',  
      header: {  
        'authorization': token  
      },  
      data: {  
        recipeId: recipeId,  
        userId: token  
      },  
      success: (res) => {  
        if (res.data && res.data.success) {  
          tt.showToast({  
            title: '添加成功',  
            icon: 'success'  
          });  
        } else {  
          tt.showToast({  
            title: res.data.errorMsg || '添加失败',  
            icon: 'none'  
          });  
        }  
      },  
      fail: (err) => {  
        console.error("添加到我的食谱请求失败：", err);  
        tt.showToast({  
          title: '请求失败',  
          icon: 'error'  
        });  
      }  
    });  
  }  
});  