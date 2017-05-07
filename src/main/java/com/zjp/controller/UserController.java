package com.zjp.controller;

/**
 * Created by 万鹏 on 2017/4/28.
 */

import com.zjp.entity.User;
import com.zjp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Logger;

/**
 * 在 @PreAuthorize 中我们可以利用内建的 SPEL 表达式：比如 'hasRole()' 来决定哪些用户有权访问。
 * 需注意的一点是 hasRole 表达式认为每个角色名字前都有一个前缀 'ROLE_'。所以这里的 'ADMIN' 其实在
 * 数据库中存储的是 'ROLE_ADMIN' 。这个 @PreAuthorize 可以修饰Controller也可修饰Controller中的方法。
 **/
@RestController
@RequestMapping("/users")

public class UserController {
    protected Logger logger = Logger.getLogger(UserController.class.getName());
    @Autowired
    private UserRepository userRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(method = RequestMethod.GET)
    public List<User> getUsers() {
        logger.info(String.format("getUsers() invoked: %s ", userRepository.getClass().getName()));
        return userRepository.findAll();
    }

    @PostAuthorize("returnObject.username == principal.username OR hasRole('ADMIN')")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public User getUserByUsername(@RequestParam(value="username") String username) {
        logger.info(String.format("getUserByUsername() invoked: %s for %s ", userRepository.getClass().getName(), username));
        return userRepository.findByUsername(username);
    }
}
