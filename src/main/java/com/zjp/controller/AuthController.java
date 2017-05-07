package com.zjp.controller;

import com.zjp.entity.User;
import com.zjp.service.AuthService;
import com.zjp.utils.TokenAuthenticationRequest;
import com.zjp.utils.TokenAuthenticationResponse;
import com.zjp.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by 万鹏 on 2017/4/28.
 */
@RestController
public class AuthController {
    protected Logger logger = Logger.getLogger(AuthController.class.getName());
    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private AuthService authService;

    @RequestMapping(value = "${jwt.route.authentication.path}", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(
            @RequestBody TokenAuthenticationRequest authenticationRequest) throws AuthenticationException{
        logger.info(String.format("createAuthenticationToken() invoked: %s for %s ", authService.getClass().getName(), authenticationRequest.getUsername()));
        try {
            final String token = authService.login(authenticationRequest.getUsername(), authenticationRequest.getPassword());
            // Return the token
            return ResponseEntity.ok(new TokenAuthenticationResponse(token));
        } catch (Exception ex) {
            logger.log(Level.WARNING, "Exception raised user login REST Call "+ex);
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
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
    public ResponseEntity<?> register(@RequestBody UserVO userVO) throws AuthenticationException {
        logger.info(String.format("register() invoked: %s for %s ", authService.getClass().getName(), userVO.getUsername()));
        User user = new User();
        BeanUtils.copyProperties(userVO,user);

/*        User addedUser = authService.register(user);
        HttpHeaders headers = new HttpHeaders();
        //return new ResponseEntity<>(addedUser, headers, (addedUser==null)?HttpStatus.INTERNAL_SERVER_ERROR:HttpStatus.OK);
        if(addedUser == null) {
            headers.setContentType(MediaType.TEXT_PLAIN);
            return new ResponseEntity<>("Register fail", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
            return new ResponseEntity<>(addedUser, headers, HttpStatus.OK);
        }*/
        try {
            authService.register(user);
        }catch (Exception ex){
            logger.log(Level.WARNING, "Exception raised register User REST Call "+ex);
            return new ResponseEntity<User>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return new ResponseEntity<User>(HttpStatus.CREATED);
    }
}
