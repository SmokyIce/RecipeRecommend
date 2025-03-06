package com.douyin.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1.判断是否需要拦截
        String token = request.getHeader("Authorization");
        if (token == null) {
            // 没有用户则拦截
            response.setStatus(401);
            // 拦截
            return false;
        }
        // 有用户放行
        try{

        }catch (Exception e){

        }
        return true;
    }
}
