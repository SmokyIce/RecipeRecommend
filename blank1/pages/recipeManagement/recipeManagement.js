// e:\study\dev\blank2116\blank1\pages\recipeManagement\recipeManagement.js
Page({  
  data: {  
    // 合并后的菜谱数据，每项结构如下：  
    // { recipeId, name, image, cookingMethod, userRating, comment }  
    recipes: [],  
    // 控制评分和评论模态框显示  
    showRateModal: false,  
    showCommentModal: false,  
    // 当前操作的记录对应的 recipeId  
    currentRecipeId: null,  
    // 临时存储待更新的评分和评论  
    tempRating: '',  
    tempComment: ''  
  },  

  onLoad() {  
    this.loadRecipes();  
  },  

  // 加载用户所有菜谱数据（后期可使用 tt.request 联调接口）  
  loadRecipes() {  
    const token = tt.getStorageSync("authorization");  
    tt.request({  
      url: "http://localhost:3000/userRecipes/management",  
      method: "GET",  
      header: {  
        "authorization": token  
      },  
      success: (res) => {  
        if (res.data.data && Array.isArray(res.data.data)) {  
          this.setData({  
            recipes: res.data.data  
          });  
        } else {  
          this.setData({  
            errorMsg: '未获取到食谱数据'  
          });  
        }  
      },  
      fail: (res) => {  
        this.setData({  
          recipes: [  
            {  
              recipeId: 'r001',  
              name: '番茄炒蛋',  
              image: '/images/tomato-egg.jpg', // 注意实际项目中使用真实路径  
              cookingMethod: '炒',  
              userRating: 5,  
              comment: '家常味道，非常下饭'  
            },  
            {  
              recipeId: 'r002',  
              name: '麻婆豆腐',  
              image: '/images/mapo-tofu.jpg',  
              cookingMethod: '煮',  
              userRating: 4,  
              comment: '微辣，适合搭配米饭'  
            }  
          ]  
        });  
      },  
    });  
  },  

  // 新增食谱详情页跳转函数  
  goToRecipe(e) {  
  // 使用 currentTarget 替代 target  
  const recipeId = e.currentTarget.dataset.id;  
  console.log("完整事件对象：", e);  
  console.log("跳转到食谱详情页，ID:", recipeId);  

  if (recipeId) {  
    tt.navigateTo({  
      url: '/pages/recipe/recipe?id=' + recipeId,  
      success: (res) => {  
        console.log('页面跳转成功');  
      },  
      fail: (err) => {  
        console.error('页面跳转失败：', err);  
        this.setData({  
          errorMsg: '页面跳转失败'  
        });  
      }  
    });  
  } else {  
    console.error('未能获取到食谱ID');  
    this.setData({  
      errorMsg: '获取食谱信息失败'  
    });  
  }  
  },  
  // 打开评分模态框  
  onOpenRate(e) {  
    const recipeId = e.currentTarget.dataset.recipeid;  
    console.log('当前要评分的食谱：', recipeId);  
    const recipe = this.data.recipes.find(item => item.recipeId === recipeId);  
    this.setData({  
      currentRecipeId: recipeId,  
      tempRating: recipe.userRating !== undefined ? recipe.userRating.toString() : '',  
      showRateModal: true  
    });  
  },  

  // 监听评分输入  
  onRatingInput(e) {  
    this.setData({ tempRating: e.detail.value });  
  },  

  // 确认评分，更新数据并调用后端接口同步  
  confirmRate() {  
    const newRating = parseInt(this.data.tempRating);  
    if (isNaN(newRating) || newRating < 0 || newRating > 5) {  
      tt.showToast({  
        title: '请输入0-5之间的整数评分',  
        icon: 'none'  
      });  
      return;  
    }  

    // 保存当前菜谱 ID，这样 setData 后还能拿到  
    const currentRecipeId = this.data.currentRecipeId;  
    console.log('当前食谱：', currentRecipeId);  
    const recipes = this.data.recipes.map(recipe => {  
      if (recipe.recipeId === currentRecipeId) {  
        recipe.userRating = newRating;  
      }  
      return recipe;  
    });  

    this.setData({  
      recipes,  
      showRateModal: false,  
      tempRating: '',  
      currentRecipeId: null  
    });  

    tt.showToast({ title: '评分成功', icon: 'success' });  

    // 调用后端接口保存评分：向后端发送 token 与评分信息  
    const token = tt.getStorageSync("authorization");  
    tt.request({  
      url: "http://localhost:3000/userRecipes/saveRating",  
      method: "POST",  
      header: {  
        "authorization": token,  
        "content-type": "application/json"  
      },  
      data: {  
        recipeId: currentRecipeId,  
        rating: newRating  
      },  
      success: (res) => {  
        console.log('评分保存成功：', res.data);  
        // 如有需要，可在接口保存成功后做进一步处理  
      },  
      fail: (err) => {  
        console.error('评分保存失败：', err);  
        tt.showToast({  
          title: '评分提交失败，请稍后重试',  
          icon: 'none'  
        });  
      }  
    });  
  },  

  // 取消评分操作  
  cancelRate() {  
    this.setData({  
      showRateModal: false,  
      tempRating: '',  
      currentRecipeId: null  
    });  
  },  

  // 删除操作，删除用户菜谱关系数据  
  onDelete(e) {  
    const recipeId = e.currentTarget.dataset.recipeid;  
    tt.showModal({  
      title: '确认删除',  
      content: '确定要从您的菜谱中删除该食谱吗？',  
      success: (res) => {  
        if (res.confirm) {  
          const token = tt.getStorageSync("authorization");  
          // 调用后端接口删除记录  
          tt.request({  
            url: "http://localhost:3000/userRecipes/delete",  
            method: "POST", // 此处可选 DELETE 方法，这里统一使用 POST  
            header: {  
              "authorization": token,  
              "content-type": "application/json"  
            },  
            data: {  
              recipeId: recipeId  
            },  
            success: (deleteRes) => {  
              console.log("删除返回结果：", deleteRes.data);  
              // 后端返回的对象格式：{ success, errorMsg, data, total }  
              if (deleteRes.data && deleteRes.data.success) {  
                // 更新前端数据, 过滤掉已删除的记录  
                const recipes = this.data.recipes.filter(recipe => recipe.recipeId !== recipeId);  
                this.setData({ recipes });  
                tt.showToast({ title: '删除成功', icon: 'success' });  
              } else {  
                tt.showToast({ title: deleteRes.data.errorMsg || '删除失败', icon: 'none' });  
              }  
            },  
            fail: (deleteErr) => {  
              console.error("删除失败：", deleteErr);  
              tt.showToast({ title: '删除失败，请稍后重试', icon: 'none' });  
            }  
          });  
        }  
      }  
    });  
  },   

  // 打开评论模态框  
  onOpenComment(e) {  
    const recipeId = e.currentTarget.dataset.recipeid;  
    const recipe = this.data.recipes.find(item => item.recipeId === recipeId);  
    this.setData({  
      currentRecipeId: recipeId,  
      tempComment: recipe.comment || '',  
      showCommentModal: true  
    });  
  },  

  // 监听评论输入  
  onCommentInput(e) {  
    this.setData({ tempComment: e.detail.value });  
  },  

  // 确认评论更新  
  confirmComment() {  
    const newComment = this.data.tempComment.trim();  
      if (!newComment) {  
        tt.showToast({  
        title: '评论内容不能为空',  
        icon: 'none'  
        });  
        return;  
      }  
      // 更新前端数据 
      const currentRecipeId = this.data.currentRecipeId;  
      console.log('当前食谱：', currentRecipeId);
      const recipes = this.data.recipes.map(recipe => {  
      if (recipe.recipeId === this.data.currentRecipeId) {  
        recipe.comment = newComment;  
      }  
      return recipe;  
    });  
    this.setData({  
      recipes,  
      showCommentModal: false,  
      tempComment: '',  
      currentRecipeId: null  
    });  
    tt.showToast({ title: '评论成功', icon: 'success' });  
    // 调用后端接口保存评论：向后端发送 token、recipeId 和 comment  
    const token = tt.getStorageSync("authorization");  
    tt.request({  
      url: "http://localhost:3000/userRecipes/saveComment",  
      method: "POST",  
      header: {  
      "authorization": token,  
      "content-type": "application/json"  
      },  
      data: {  
        recipeId: currentRecipeId,  
        comment: newComment  
      },  
      success: (res) => {  
        console.log('评论保存成功：', res.data);  
        // 根据返回的结构可以做进一步提示或处理  
      },  
      fail: (err) => {  
        console.error('评论保存失败：', err);  
        tt.showToast({  
          title: '评论提交失败，请稍后重试',  
          icon: 'none'  
        });  
      }  
    });  
  },    

  // 取消评论操作  
  cancelComment() {  
    this.setData({  
      showCommentModal: false,  
      tempComment: '',  
      currentRecipeId: null  
    });  
  }  
});  