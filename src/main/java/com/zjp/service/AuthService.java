package com.zjp.service;

import com.zjp.entity.User;

/**
 * Created by 万鹏 on 2017/4/28.
 */
public interface AuthService {
    User register(User userToAdd) throws Exception;
    String login(String username, String password);
    String refresh(String oldToken);
}
