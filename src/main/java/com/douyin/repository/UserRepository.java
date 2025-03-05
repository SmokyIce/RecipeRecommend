package com.douyin.repository;

import com.douyin.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    // 根据电话查找用户
    User findByPhone(String phone);
}