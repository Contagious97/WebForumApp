package com.example.webforumapp.controllers;


import com.example.webforumapp.models.User;
import com.example.webforumapp.models.requests.UserDetailsRequest;
import com.example.webforumapp.services.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/")
public class AuthController {

    @Autowired
    LoginService loginService;
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
                return new ResponseEntity<>("Successful login",HttpStatus.ACCEPTED);
            } else return new ResponseEntity<>("Unsuccessful login",HttpStatus.UNAUTHORIZED);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserDetailsRequest user){
        logger.info(user.toString());
        try {
            if (loginService.signUp(user.getUserName(),user.getPassword(),user.getUserName()) != null){
                return new ResponseEntity<>("User created",HttpStatus.ACCEPTED);
            } else return new ResponseEntity<>("User Not created",HttpStatus.UNAUTHORIZED);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }






}
