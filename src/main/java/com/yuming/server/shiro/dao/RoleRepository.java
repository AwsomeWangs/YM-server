package com.yuming.server.shiro.dao;

import com.yuming.server.shiro.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author 大誌
 * @Date 2019/3/30 22:05
 * @Version 1.0
 */
public interface RoleRepository extends JpaRepository<Role, Integer> {
}
