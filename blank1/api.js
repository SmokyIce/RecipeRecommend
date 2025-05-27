const express = require('express');  
const mysql = require('mysql2/promise');  
const app = express();  
const port = 3000; // 监听端口，可根据需要修改  

// 数据库连接配置函数：请使用你的真实数据库信息  
async function getDbConnection() {  
  return await mysql.createConnection({  
    host: 'localhost',       // 数据库服务器，通常为本地  
    user: 'root',            // 数据库用户名  
    password: '123456',// 数据库密码  
    database: 'douyin',      // 数据库名称  
    charset: 'utf8mb4'  
  });  
}  

// 跨域配置：允许来自所有域的请求。由于本地测试时可能存在跨域问题，因此暂时允许所有域。  
app.use((req, res, next) => {  
  res.header("Access-Control-Allow-Origin", "*");   
  res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");  
  next();  
});  

// 定义 API 路由：返回数据库中 recipes 表中的所有数据  
app.get('/api/recipes', async (req, res) => {  
  try {  
    const connection = await getDbConnection();  
    const [recipes] = await connection.execute('SELECT * FROM recipes');  
    await connection.end();  

    res.json(recipes); // 将数组以 JSON 格式返回  
  } catch (error) {  
    console.error("查询数据库失败：", error);  
    res.status(500).json({ error: error.message });  
  }  
});  

// 启动服务器，监听端口  
app.listen(port, () => {  
  console.log(`API Server is running at http://localhost:${port}`);  
});