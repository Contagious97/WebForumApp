package com.example.webforumapp.controllers;


import com.example.webforumapp.models.User;
import com.example.webforumapp.models.replys.UserDetails;
import com.example.webforumapp.models.requests.UserDetailsRequest;
import com.example.webforumapp.services.LoginService;
import com.example.webforumapp.services.UserInfoService;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.jsonwebtoken.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/")
public class AuthController {

    @Autowired
    LoginService loginService;
    @Autowired
    UserInfoService userInfoService;

    Logger logger = LoggerFactory.getLogger(AuthController.class);


    @PostMapping("login/oauth2/code/github")
    public ResponseEntity<String> gitHubLogin(String code, HttpServletResponse e){
        System.out.println("LOgged in");
        return new ResponseEntity<>("LOGGED IN", HttpStatus.ACCEPTED);
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDetailsRequest user){
        logger.info(user.toString());
        try {
            if (loginService.signin(user.getUserName(), user.getPassword())){
                JSONObject resp = new JSONObject();
                resp.put("username",user.getUserName());

                resp.put("name",userInfoService.loadUserByUsername(user.getUserName()).getName());
                UserDetails userDetails = new UserDetails();
                userDetails.setUserName(user.getUserName());
                userDetails.setName(user.getName());
                logger.info(resp.toJSONString());
                return new ResponseEntity<>(resp.toJSONString(),HttpStatus.ACCEPTED);
            } else return new ResponseEntity<>("Unsuccessful login",HttpStatus.UNAUTHORIZED);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserDetailsRequest user){
        logger.info(user.toString());
        try {
            if (loginService.signUp(user.getUserName(),user.getPassword(),user.getName()) != null){
                return new ResponseEntity<>("User created",HttpStatus.ACCEPTED);
            } else return new ResponseEntity<>("User Not created",HttpStatus.UNAUTHORIZED);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }






}
