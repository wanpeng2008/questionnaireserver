package com.zjp.utils;

import com.zjp.entity.Role;
import com.zjp.domain.TokenUser;
import com.zjp.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by 万鹏 on 2017/4/27.
 */
public final class TokenUserFactory {
    private TokenUserFactory() {
    }

    public static TokenUser create(User user) {
        return new TokenUser(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                mapToGrantedAuthorities(user.getRoles()),
                user.getLastPasswordResetDate()
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(Collection<Role> authorities) {
        return authorities.stream()
                .map(Role::getName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
