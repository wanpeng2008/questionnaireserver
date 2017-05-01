package com.zjp.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 万鹏 on 2017/4/28.
 */
@Component
public class TokenUtil {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    public String getUsernameFromToken(String authToken) {
        return this.getClaimsFromToken(authToken).getSubject();
    }

    public boolean validateToken(String authToken, UserDetails userDetails) {
        return true;
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", userDetails.getUsername());
        return this.tokenHead+generateToken(claims);
    }
    public boolean canTokenBeRefreshed(String token, Date lastPasswordResetDate) {
        return true;
    }
    public String refreshToken(String token) {
        return "";
    }
    /////////////
    String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secret) //采用什么算法是可以自己选择的，不一定非要采用HS512
                .compact();
    }

    private Date generateExpirationDate() {
        Date expirationDate = new Date(System.currentTimeMillis() + this.expiration);
        return expirationDate;
    }

    Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }



}
