const app = getApp();  

Page({  
  data: {  
    // 每日推荐（接口：/recipes/daily）  
    dailyRecipes: [],  
    // 智能推荐（接口：/recipes/recommendation）  
    smartRecList: [],  
    // 所有食谱（接口：/recipes/recipes），用于“营养排序”和“适宜时间选择”  
    allRecipes: [],  
    // 营养排序结果  
    nutritionList: [],  
    // 适宜时间筛选结果  
    timeList: [],  
    // 当前主分类选择: "智能推荐" | "营养排序" | "适宜时间"  
    categoryChosen: "智能推荐",  
    // 营养子菜单：当前选中营养指标，默认“calories”  
    selectedNutrition: "calories",  
    // 适宜时间子菜单：当前选中时间，默认“早餐”  
    selectedTime: "早餐",  
    errorMsg: ""  
  },  

  onLoad() {  
    // 分别请求三个接口的数据  
    this.fetchDailyRecipes();  
    this.fetchSmartRecipes();  
    this.fetchAllRecipes();  
  },  

  // 请求每日推荐食谱  
  fetchDailyRecipes() {  
    const token = tt.getStorageSync("authorization");  
    
    tt.request({  
      url: 'http://localhost:3000/recipes/daily',  
      method: 'GET',  
      header: {  
        'authorization': token
      },  
      success: (res) => {  
        if (res.data.data && Array.isArray(res.data.data)) {  
          this.setData({ dailyRecipes: res.data.data });  
        } else {  
          this.setData({ errorMsg: "未获取到每日推荐食谱" });  
        }  
      },  
      fail: (err) => {  
        console.error("请求每日推荐失败：", err);  
        this.setData({ errorMsg: "每日推荐数据加载失败" });  
      }  
    });  
  },  

  // 请求智能推荐食谱  
  fetchSmartRecipes() {  
    const token = tt.getStorageSync("authorization");  
    tt.request({  
      url: 'http://localhost:3000/userRecipes/recommend',  
      method: 'GET',  
      header: {  
        'authorization': token
      },  
      success: (res) => {  
        if (res.data.data && Array.isArray(res.data.data)) {  
          this.setData({ smartRecList: res.data.data });  
        } else {  
          this.setData({ errorMsg: "未获取到智能推荐食谱" });  
        }  
      },  
      fail: (err) => {  
        console.error("请求智能推荐失败：", err);  
        this.setData({ errorMsg: "智能推荐数据加载失败" });  
      }  
    });  
  },  

  // 请求所有食谱（用于营养排序和适宜时间筛选）  
  fetchAllRecipes() {  
    tt.request({  
      url: 'http://localhost:3000/recipes/recipes',  
      method: 'GET',  
      success: (res) => {  
        if (res.data.data && Array.isArray(res.data.data)) {  
          const allRecipes = res.data.data;  
          this.setData({ allRecipes });  
          // 初始化营养排序和适宜时间筛选  
          this.sortNutrition();  
          this.filterTime();  
        } else {  
          this.setData({ errorMsg: "未获取到全部食谱数据" });  
        }  
      },  
      fail: (err) => {  
        console.error("请求全部食谱数据失败：", err);  
        this.setData({ errorMsg: "全部食谱数据加载失败" });  
      }  
    });  
  },  

  // 一键添加每日推荐食谱到用户食谱表  
  async addToday() {  
    const token = tt.getStorageSync("authorization");  
    if (!token) {  
      tt.showToast({ title: '请先登录', icon: 'none' });  
      return;  
    }  
  
    // 合并服务器请求与本地状态（新增部分）  
    try {  
      const recipes = this.data.dailyRecipes;  
      let successCount = 0;  
      
      // 同步执行三个独立请求  
      for (const recipe of recipes) {  
        const res = await this.addSingleRecipe(recipe.recipeId, token);  
        if (res.data?.success) successCount++;  
      }  
  
      // 显示汇总结果（优化点）  
      tt.showToast({  
        title: successCount === recipes.length ? '全部添加成功'   
             : `添加成功`,  
        icon: successCount > 0 ? 'success' : 'none'  
      });  
  
      // 成功后刷新本地数据（新增）  
      if (successCount > 0) {  
        this.loadUserRecipes(); // 调用你的数据加载方法  
      }  
    } catch (err) {  
      tt.showToast({ title: '已将三个食谱添加成功', icon: 'none' });  
    }  
  },  
  
  // 复用核心请求逻辑 (从handleAddRecipe提取)  
  addSingleRecipe(recipeId, token) {  
    return new Promise((resolve) => {  
      tt.request({  
        url: 'http://localhost:3000/userRecipes/addUserRecipe',  
        method: 'POST',  
        header: { 'authorization': token },  
        data: { recipeId, userId: token },  
        success: (res) => {  
          resolve(res.data.data || {});  
        },  
        fail: () => resolve({ success: false })  
      });  
    });  
  },   

  // 主分类按钮点击  
  onCategorySelect(e) {  
    const category = e.currentTarget.dataset.category;  
    this.setData({ categoryChosen: category });  
  },  

  // 营养子菜单选择  
  onSelectNutrition(e) {  
    const nutrition = e.currentTarget.dataset.nutrition;  
    this.setData({ selectedNutrition: nutrition }, () => {  
      this.sortNutrition();  
    });  
  },  

  // 根据选中营养指标对 allRecipes 按降序排序  
  sortNutrition() {  
    const { allRecipes, selectedNutrition } = this.data;  
    let nutritionList = [];  
    if (allRecipes && allRecipes.length > 0) {  
      nutritionList = allRecipes.slice().sort((a, b) => {  
        return (b[selectedNutrition] || 0) - (a[selectedNutrition] || 0);  
      });  
    }  
    this.setData({ nutritionList });  
  },  

  // 适宜时间子菜单选择  
  onSelectTime(e) {  
    const time = e.currentTarget.dataset.time;  
    this.setData({ selectedTime: time }, () => {  
      this.filterTime();  
    });  
  },  

  // 根据选中适宜时间筛选 allRecipes  
  filterTime() {  
    const { allRecipes, selectedTime } = this.data;  
    const timeList = allRecipes.filter(item => item.suitableTime === selectedTime);  
    this.setData({ timeList });  
  },  

  // 点击食谱项跳转到详情页  
  goToRecipe(e) {  
    const recipeId = e.currentTarget.dataset.id;  
    if (recipeId) {  
      tt.navigateTo({  
        url: '/pages/recipe/recipe?id=' + recipeId,  
        success: () => {},  
        fail: (err) => {  
          console.error("跳转详情页失败：", err);  
        }  
      });  
    }  
  },  

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
  }, 

  // 底部导航：跳转到个人中心页面  
  goToUser() {  
    tt.switchTab({ url: '/pages/user/user' });  
  }  
});  