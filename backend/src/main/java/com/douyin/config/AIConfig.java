package com.douyin.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author smokingice
 */
@Configuration
public class AIConfig {
//    private final ChatClient.Builder chatClientBuilder;
//
//    public AIConfig(ChatClient.Builder chatClientBuilder) {
//        this.chatClientBuilder = chatClientBuilder;
//    }
//
//    @Bean
//    public ChatMemoryRepository chatMemoryRepository(){
//        return new InMemoryChatMemoryRepository();
//    }
//    @Bean
//    public ChatMemory chatMemory(){
//        return MessageWindowChatMemory.builder()
//                .chatMemoryRepository(chatMemoryRepository())
//                .maxMessages(2)
//                .build();
//    }

    @Bean
    public ChatClient chatClient(OllamaChatModel model) {

        return ChatClient.builder(model)
                .defaultSystem("根据食谱信息做说明")
                .build();
    }
}
