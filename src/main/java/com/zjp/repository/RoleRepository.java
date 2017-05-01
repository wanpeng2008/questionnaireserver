package com.zjp.repository;

import com.zjp.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Created by 万鹏 on 2017/4/27.
 */
public interface RoleRepository extends JpaRepository<Role, UUID> {
    Role findByName(String roleName);
}
