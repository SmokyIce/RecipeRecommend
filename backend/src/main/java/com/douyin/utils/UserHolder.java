package com.douyin.utils;

import com.douyin.entity.User;

public class UserHolder {
    /*
    将用户存入线程
     */
    private static final ThreadLocal<User> tl = new ThreadLocal<>();

    public static void saveUser(User user){
        tl.set(user);
    }

    public static User getUser(){
        return tl.get();
    }

    public static void removeUser(){
        tl.remove();
    }
}
