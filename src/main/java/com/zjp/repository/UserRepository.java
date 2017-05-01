package com.zjp.repository;

import com.zjp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Created by 万鹏 on 2017/4/27.
 */
public interface UserRepository extends JpaRepository<User, UUID> {
    User findByUsername(String username);

    //User insert(User userToAdd);
}
