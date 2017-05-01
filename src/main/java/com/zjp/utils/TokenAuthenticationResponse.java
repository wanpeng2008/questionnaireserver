package com.zjp.utils;

/**
 * Created by 万鹏 on 2017/4/28.
 */
public class TokenAuthenticationResponse {
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public TokenAuthenticationResponse(String token) {
        this.token = token;
    }
}
