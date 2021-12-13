package com.example.webforumapp.AuthTest;


import com.example.webforumapp.repositories.LogRepository;
import com.example.webforumapp.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.shaded.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;


import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class AuthTests {

    @Autowired
    LogRepository logRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void signIn() throws Exception {

        JSONObject user1 = new JSONObject();
        user1.put("userName", "Username");
        user1.put("password", "Password");
        user1.put("name","Name");


        //User Created
        mvc.perform(post("/signup")
                        .header("Content-Type", "application/json")
                        .content(user1.toString()))
                .andExpect(status().isOk())
                .andExpect(content().string("User created"));

        JSONObject userSignin = new JSONObject();
        userSignin.put("userName","Username");
        userSignin.put("password","Password");

        JSONObject response = new JSONObject();
        response.put("username","Username");
        response.put("name","Name");

        //User logged in
        mvc.perform(post("/login")
                        .header("Content-Type", "application/json")
                        .content(userSignin.toString()))
                .andExpect(status().isOk())
                .andExpect(content().json(response.toJSONString()));


        //Invalid credentials
        userSignin.put("password","WrongPassword");
        mvc.perform(post("/login")
                        .header("Content-Type", "application/json")
                        .content(userSignin.toString()))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Wrong password"));


    }

}


