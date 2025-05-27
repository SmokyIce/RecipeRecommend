// e:\douyin\miniprogram\VER0.11\blank1\pages\preferencesManagement\preferencesManagement.js
Page({  
  data: {  
    // 身体健康偏好（共10个）  
    healthPreferences: [  
      { label: "糖尿病适宜", selected: false },  
      { label: "高血压适宜", selected: false },  
      { label: "高血脂适宜", selected: false },  
      { label: "痛风适宜", selected: false },  
      { label: "肥胖风险低", selected: false },  
      { label: "骨质疏松预防", selected: false },  
      { label: "消化系统友好", selected: false },  
      { label: "心血管保健", selected: false },  
      { label: "肾脏保护", selected: false },  
      { label: "免疫调节", selected: false }  
    ],  
    // 食谱特征偏好（共20个）  
    recipeFeaturePreferences: [  
      { label: "少油", selected: false },  
      { label: "少盐", selected: false },  
      { label: "美味", selected: false },  
      { label: "咸香", selected: false },  
      { label: "酸甜", selected: false },  
      { label: "高纤维", selected: false },  
      { label: "低糖", selected: false },  
      { label: "低脂", selected: false },  
      { label: "高蛋白", selected: false },  
      { label: "清淡", selected: false },  
      { label: "辛辣", selected: false },  
      { label: "鲜香", selected: false },  
      { label: "微辣", selected: false },  
      { label: "浓郁", selected: false },  
      { label: "爽口", selected: false },  
      { label: "多样化", selected: false },  
      { label: "低热量", selected: false },  
      { label: "高维生素", selected: false },  
      { label: "天然", selected: false },  
      { label: "纯素", selected: false }  
    ],  
    // 最终提交时需要封装的偏好数据  
    recipePreferences: []  
  },  

  onLoad() {  
    // 在页面加载后发送请求，获取当前用户偏好配置数据  
    const token = tt.getStorageSync("authorization");  
    tt.request({  
      url: "http://localhost:3000/user/getPreferences",  
      method: "GET",  
      header: {  
        "authorization": token,  
        "content-type": "application/json"  
      },  
      success: (res) => {  
        // 假设返回的数据格式为 { data: ["高蛋白", "少油", ...] }  
        const userPrefs = res.data.data || [];  
              
        // 更新身体健康偏好选中状态  
        const newHealthPreferences = this.data.healthPreferences.map(item => ({  
          ...item,  
          selected: userPrefs.includes(item.label)  
        }));  
        // 更新食谱特征偏好选中状态  
        const newRecipeFeaturePreferences = this.data.recipeFeaturePreferences.map(item => ({  
          ...item,  
          selected: userPrefs.includes(item.label)  
        }));  
        this.setData({  
          healthPreferences: newHealthPreferences,  
          recipeFeaturePreferences: newRecipeFeaturePreferences,  
          recipePreferences: userPrefs  
        });  
      },  
      fail: (err) => {  
        tt.showToast({  
          title: '获取偏好数据失败',  
          icon: 'none'  
        });  
      }  
    });  
  }, 
  
  // 切换身体健康偏好选中状态  
  toggleHealthPreference(e) {  
    const index = e.currentTarget.dataset.index;  
    let newArr = this.data.healthPreferences;  
    newArr[index].selected = !newArr[index].selected;  
    this.setData({ healthPreferences: newArr });  
  },  

  // 切换食谱特征偏好选中状态  
  toggleFeaturePreference(e) {  
    const index = e.currentTarget.dataset.index;  
    let newArr = this.data.recipeFeaturePreferences;  
    newArr[index].selected = !newArr[index].selected;  
    this.setData({ recipeFeaturePreferences: newArr });  
  },  

  // 提交偏好设置，将所有选中的按钮文本封装为数组并发送到后端  
  submitPreferences() {  
    let selectedPrefs = [];  
    this.data.healthPreferences.forEach(item => {   
      if (item.selected) selectedPrefs.push(item.label);  
    });  
    this.data.recipeFeaturePreferences.forEach(item => {   
      if (item.selected) selectedPrefs.push(item.label);  
    });  

    this.setData({  
      recipePreferences: selectedPrefs  
    });  

    const token = tt.getStorageSync("authorization");  
    const requestData = {  
      recipePreferences: selectedPrefs  
    }; 
    console.log("Sending data package to backend:", requestData);  

    tt.request({  
      url: "http://localhost:3000/user/savePreferences",  
      method: "POST",  
      header: {  
        "authorization": token,  
        "content-type": "application/json"  
      },  
      data: {  
        recipePreferences: selectedPrefs  
      },   
      success: (res) => {  
        tt.showToast({  
          title: res.data.data,  
          icon: 'success'  
        });  
      },  
      fail: (err) => {  
        tt.showToast({  
          title: '提交失败，请稍后重试',  
          icon: 'none'  
        });  
      }  
    });  
  },  

  // 清空所有选择  
  clearPreferences() {  
    this.setData({  
      healthPreferences: this.data.healthPreferences.map(item => ({ ...item, selected: false })),  
      recipeFeaturePreferences: this.data.recipeFeaturePreferences.map(item => ({ ...item, selected: false })),  
      recipePreferences: []  
    });  
  }  
});  