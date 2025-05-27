// pages/login/login.js  
Page({  
  data: {  
    phone: '',  
    password: ''  
  },  

  // 监听手机号输入  
  onPhoneInput(e) {  
    this.setData({  
      phone: e.detail.value  
    });  
  },  

  // 监听密码输入  
  onPasswordInput(e) {  
    this.setData({  
      password: e.detail.value  
    });  
  },  

  // 登录按钮点击事件  
  onLogin() {  
    const { phone, password } = this.data;  

    if (!phone || !password) {  
      tt.showToast({  
        title: '请输入手机号和密码',  
        icon: 'none'  
      });  
      return;  
    }  

    tt.showLoading({  
      title: '登录中...'  
    });  

    // 使用 POST 请求向后端发送数据  
    const formData = {  
      phone: phone,  
      password: password,  
    };  

    tt.request({  
      url: 'http://127.0.0.1:3000/user/login',  
      method: 'POST',  
      data: formData,  
      header: {  
        'content-type': 'application/json'  
      },  
      success: (res) => {  
        console.log('请求成功：', res.data);  
        if (res.data && res.data.success === true && res.data.data) {  
          const token = res.data.data;  
          // 使用 tt.setStorageSync 保存 token  
          tt.setStorageSync("authorization", token);  
          tt.showToast({  
            title: '登录成功',  
            icon: 'success'  
          });  
          // 登录成功后跳转到用户页面  
          tt.navigateBack({ delta: 0 }),
          tt.reLaunch({
            url: '/pages/user/user',
          });
        } else {  
          console.log('登录失败：'); 
          setTimeout(() => {  
            tt.showToast({  
              title: res.data.errorMsg,  
              icon: 'none',  // 改成 none 尝试一下  
              duration: 1500  
            });  
          }, 300);
        }  
      },  
      fail: (err) => {  
        console.error('请求失败：', err);  
        setTimeout(() => {  
          tt.showToast({  
            title: '提交失败',  
            icon: 'none',  // 改成 none 尝试一下  
            duration: 1500  
          });  
        }, 300);  
      },  
      complete: () => {  
        tt.hideLoading();  
      }  
    });  
  },  

  // 注册按钮点击事件  
  onRegister() {  
    tt.navigateTo({  
      url: '/pages/register/register'  
    });  
  }  
});  
