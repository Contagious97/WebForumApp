package com.example.webforumapp.controllers;

import com.example.webforumapp.models.User;
import com.example.webforumapp.models.replys.UserInfo;
import com.example.webforumapp.models.replys.UserDetails;
import com.example.webforumapp.services.UserInfoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserInfoService userInfoService;

    @GetMapping("/{username}")
    public ResponseEntity<String> getUser(@PathVariable(value = "username") String username){

        ObjectMapper mapper = new ObjectMapper();
        try {
            UserInfo user = userInfoService.loadUserByUsername(username);
            UserDetails userDetails = new UserDetails();
            userDetails.setUserName(user.getUsername());
            userDetails.setName(user.getName());
            userDetails.setUserId(user.getId());
            String reply = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(userDetails);
            return new ResponseEntity<>(reply,HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping()
    public ResponseEntity<String> getUsers() {

        try {
            List<User> users = userInfoService.getUsers();
            List<UserDetails> userInfos = new ArrayList<>();
            for (User user : users) {
                userInfos.add(new UserDetails(user));
            }
            String jsonArray = JSONArray.toJSONString(userInfos);
            return new ResponseEntity<>(jsonArray, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


}
