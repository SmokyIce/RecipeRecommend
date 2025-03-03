package com.douyin.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.json.JsonWriteFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        // 关闭 HTML 转义
        mapper.configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, false);
        // 关闭默认转义
        mapper.configure(JsonWriteFeature.ESCAPE_NON_ASCII.mappedFeature(), false);
        return mapper;
    }
}