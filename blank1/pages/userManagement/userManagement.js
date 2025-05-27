Page({  
  data: {  
    age: '',                     // 年龄  
    nickname: ''                 // 昵称  
  },  

  // 页面加载时，将后端传递的预设数据赋值到页面 data 中  
  onLoad: function(options) {  
    // 示例：假设预设数据通过页面参数传入  
    this.setData({  
      age: options.age || '',  
      nickname: options.nickname || '',  
    });  
  },  

  // 年龄输入框的变化响应函数  
  onAgeChange: function(e) {  
    this.setData({  
      age: e.detail.value  
    });  
  },  

  // 昵称输入框的变化响应函数  
  onNicknameChange: function(e) {  
    this.setData({  
      nickname: e.detail.value  
    });  
  },  


  // 提交修改，调用后端更新接口  
  onSubmit: function() {  
    // 提交前从 data 中获取所需数据  
    const { age, nickname } = this.data;  
    // 构造提交的数据对象  
    const formData = {  
      age,  
      nickname,  
    };  

    // 从存储中获取 token  
    const token = tt.getStorageSync("authorization");  

    tt.request({  
      url: 'http://127.0.0.1:3000/user/updateUser',  
      method: 'POST',  
      data: formData,  
      header: {  
        'authorization': token  
      },  
      success: (res) => {  
        console.log('请求成功：', res.data);  
        if (res.data && res.data.success === true) {  
          console.log('修改成功');  
          tt.showToast({  
            title: '修改成功',  
            icon: 'success'  
          });  
        } else {  
          console.log('修改失败');   
          tt.showToast({  
            title: res.data.errorMsg || '修改失败',  
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
  }  
});  