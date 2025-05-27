Page({  
  data: {  
    isLoggedIn: false,  
    userInfo: {  
      token: "", // 用户名称，将从 token 中获取  
      age: 28,
      nickname: ""
    }  
  },
  onLoad() {  
    // 从存储中获取 token  
    const token = tt.getStorageSync("authorization");  
    if (token) {  
      this.setData({  
        isLoggedIn: true,  
        userInfo: {  
          ...this.data.userInfo,  
          token: token  
        }  
      });
      tt.request({  
        url: 'http://127.0.0.1:3000/user/user',  
        method: 'GET',  
        header: {  
          'authorization': token
        },  
        success: (res) => {  
          console.log('请求成功：', res.data);  
          if (res.data && res.data.success === true && res.data.data) {  
            //传来的data为nickname
            this.setData({  
              "userInfo.nickname": res.data.data  
            });  
          } else {  
            console.log('登录失败：'); 
            tt.showToast({  
              title: res.data.errorMsg || '登录失败',  
              icon: 'none'  
            });  
          }  
        },  
        fail: (err) => {  
          console.error('请求失败：', err);  
          tt.showToast({  
            title: '提交失败',  
            icon: 'error'  
          });  
        },  
        complete: () => {  
          tt.hideLoading();  
        }  
      });
    } else {  
      // 未登录状态：不自动跳转，而是在页面显示登录/注册按钮  
      this.setData({  
        isLoggedIn: false,  
        userInfo: {  
          nickname: "",  
          age: 28  
        }  
      });  
    }  
    console.log("用户页面加载", this.data);  
  },  

  // 点击“管理我的食谱”按钮，跳转到食谱管理页面  
  handleManageRecipes() {  
    tt.navigateTo({  
      url: '/pages/recipeManagement/recipeManagement'  
    });  
  },  

  // 点击 管理用户信息
  handleUserInfo() {  
    tt.navigateTo({
      url: '/pages/userManagement/userManagement'
    });  
  },

  // 点击“管理用户偏好”按钮，跳转到管理用户偏好页面 
  handleUserPreferences() {  
    tt.navigateTo({  
      url: "/pages/preferencesManagement/preferencesManagement" 
    });  
  }, 
  // 点击“登出”按钮，清除 token 并恢复到未登录状态  
  handleLogout() {  
    tt.removeStorageSync("authorization");  
    tt.showToast({  
      title: '已登出',  
      icon: 'success'  
    });  
    // 清空登录状态，本页展示为未登录状态  
    this.setData({  
      isLoggedIn: false,  
      userInfo: {  
        nickname: "",   
      }  
    });  
  },  

  // 点击“登录”按钮，进入 login 界面  
  handleLogin() {  
    tt.navigateTo({  
      url: '/pages/login/login'  
    });
  },  

  // 点击“注册”按钮，进入 register 界面  
  handleRegister() {  
    tt.navigateTo({  
      url: '/pages/register/register'  
    });  
  }  
});  