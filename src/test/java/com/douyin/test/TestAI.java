package com.douyin.test;

import com.douyin.service.impl.UserRecipeServiceImpl;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestAI {

    @Resource
    ChatClient c;


    @Test
    public void testOllama(){
        Object content = c.prompt().user("请给我一个10个字").call().content();
        System.out.println(content);
    }
}
