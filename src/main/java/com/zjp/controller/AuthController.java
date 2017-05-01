package com.zjp.controller;

import com.zjp.entity.User;
import com.zjp.service.AuthService;
import com.zjp.utils.TokenAuthenticationRequest;
import com.zjp.utils.TokenAuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by 万鹏 on 2017/4/28.
 */
@RestController
public class AuthController {
    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private AuthService authService;

    @RequestMapping(value = "${jwt.route.authentication.path}", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(
            @RequestBody TokenAuthenticationRequest authenticationRequest) throws AuthenticationException{
        final String token = authService.login(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        // Return the token
        return ResponseEntity.ok(new TokenAuthenticationResponse(token));
    }

    @RequestMapping(value = "${jwt.route.authentication.refresh}", method = RequestMethod.GET)
    public ResponseEntity<?> refreshAndGetAuthenticationToken(
            HttpServletRequest request) throws AuthenticationException{
        String token = request.getHeader(tokenHeader);
        String refreshedToken = authService.refresh(token);
        if(refreshedToken == null) {
            return ResponseEntity.badRequest().body(null);
        } else {
            return ResponseEntity.ok(new TokenAuthenticationResponse(refreshedToken));
        }
    }

    @RequestMapping(value = "${jwt.route.authentication.register}", method = RequestMethod.POST)
    public ResponseEntity<?> register(@RequestBody User user) throws AuthenticationException {
        User addedUser = authService.register(user);
        HttpHeaders headers = new HttpHeaders();

        //return new ResponseEntity<>(addedUser, headers, (addedUser==null)?HttpStatus.INTERNAL_SERVER_ERROR:HttpStatus.OK);
        if(addedUser == null) {
            headers.setContentType(MediaType.TEXT_PLAIN);
            return new ResponseEntity<>("Register fail", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
            return new ResponseEntity<>(addedUser, headers, HttpStatus.OK);
        }
    }
}
