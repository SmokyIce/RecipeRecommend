// e:\study\dev\blank1\pages\register\register.js
Page({  
  data: {  
    phone: '',  
    nickname: '',
    password: '',
    confirmPassword: ''
  },  

  // 监听用户名输入  
  onPhoneInput(e) {  
    this.setData({  
      phone: e.detail.value  
    });  
  },  

  // 监听昵称输入  
  onNicknameInput(e) {  
    this.setData({  
      nickname: e.detail.value  
    });  
  },  

  // 监听密码输入  
  onPasswordInput(e) {  
    this.setData({  
      password: e.detail.value  
    });  
  },  

  // 监听确认密码输入  
  onConfirmPasswordInput(e) {  
    this.setData({  
      confirmPassword: e.detail.value  
    });  
  },  

  // 注册按钮点击事件  
  onRegister() {  
    const { phone, nickname, password, confirmPassword} = this.data;  

    if (!phone || !password || !nickname ||!confirmPassword) {  
      tt.showToast({  
        title: '请填写完整信息',  
        icon: 'none'  
      });  
      return;  
    }  

    if (password !== confirmPassword) {  
      tt.showToast({  
        title: '两次密码输入不一致',  
        icon: 'none'  
      });  
      return;  
    }  

    // 验证用户名和密码的合法性
    if (!/^[a-zA-Z0-9]{8,16}$/.test(password) ) {
      tt.showToast({
        title: '密码必须为8-16位字母或数字',
        icon: 'none'
      });
      return;
    }

    // 构造表单数据
    const formData = {
      phone: phone,
      password: password,
      nickname: nickname
    };
    // 发送注册请求
    tt.request({  
      url: 'http://127.0.0.1:3000/user/register',  
      method: 'POST',  
      data: formData,  
      header: {  
        'content-type': 'application/json' // 默认值  
      },  
      success: (res) => {  
        console.log('请求成功：', res.data);  
        tt.showToast({  
          title: res.data.errorMsg || '注册成功',  
          icon: 'success'  
        });
        
      },  
      fail: (err) => {  
        console.error('请求失败：', err);  
        tt.showToast({  
          title: '提交失败',  
          icon: 'error'  
        });  
      }
    });
  }  
});