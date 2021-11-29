package com.example.webforumapp.UserTest;


import com.example.webforumapp.models.Log;
import com.example.webforumapp.models.replys.LogInfo;
import com.example.webforumapp.models.User;
import com.example.webforumapp.models.replys.UserDetails;
import com.example.webforumapp.repositories.LogRepository;
import com.example.webforumapp.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.shaded.json.JSONObject;
import net.minidev.json.JSONArray;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;


import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class UserTests {

    @Autowired
    LogRepository logRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void create() throws Exception {
        JSONObject user1 = new JSONObject();
        user1.put("userName", "Username");
        user1.put("password", "Password");


        mvc.perform(post("/signup")
                        .header("Content-Type", "application/json")
                        .content(user1.toString()))
                .andExpect(status().isOk())
                .andExpect(content().string("User created"));


        //Duplicate username - Not allowed
        mvc.perform(post("/signup")
                        .header("Content-Type", "application/json")
                        .content(user1.toString()))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Username Taken"));


    }

    @Test
    void feed() throws Exception {

        User user = new User();
        user.setUserName("Momo");
        user.setName("Momo");
        user.setPassword("Password");

        userRepository.save(user);

        Log log = new Log();
        log.setContent("Test log");
        log.setUser(user);

        long millis = System.currentTimeMillis();
        java.util.Date date = new java.util.Date();
        log.setDate(new Timestamp(date.getTime()));


        Log log2 =  new Log();
        log2.setUser(user);
        log2.setContent("Second test log");
        log2.setDate(new Timestamp(date.getTime()));

        Log post1 = logRepository.save(log);
        Log post2 = logRepository.save(log2);

        List<LogInfo> loginfos = new ArrayList<>();

        loginfos.add(new LogInfo(log.getId(),user.getUserName(),log.getContent(),log.getDate().toString()));
        loginfos.add(new LogInfo(log2.getId(),user.getUserName(),log2.getContent(),log2.getDate().toString()));


        String jsonArray = JSONArray.toJSONString(loginfos);

        mvc.perform(get("/logs/" + user.getUserName()))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonArray));
    }

    @Test
    void getUser() throws Exception {

        User user1 = new User();
        User user2 = new User();
        user1.setUserName("TEST");
        user2.setUserName("TEST2");
        user1.setName("TESTNAME");
        user2.setName("TESTNAME2");
        user1.setPassword("Password1");
        user2.setPassword("Password2");
        var john = userRepository.save(user1);
        var doe = userRepository.save(user2);

        UserDetails user1details = new UserDetails();
        user1details.setName(user1.getName());
        user1details.setUserName(user1.getUserName());

        String responseUser1 = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(user1details);

        UserDetails user2details = new UserDetails();
        user2details.setName(user2.getName());
        user2details.setUserName(user2.getUserName());
        String responseUser2 = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(user2details);


        mvc.perform(get("/user/" + user1.getUserName()))
                .andExpect(status().isOk())
                .andExpect(content().json(responseUser1));

        mvc.perform(get("/user/" + user2.getUserName()))
                .andExpect(status().isOk())
                .andExpect(content().json(responseUser2));
    }


    @Test
    public void createMessage() throws Exception {

        User user1 = new User();
        User user2 = new User();
        user1.setUserName("TEST");
        user2.setUserName("TEST2");
        user1.setName("TESTNAME");
        user2.setName("TESTNAME2");
        user1.setPassword("Password1");
        user2.setPassword("Password2");
        userRepository.save(user1);
        userRepository.save(user2);

        JSONObject createMessage = new JSONObject();
        createMessage.put("senderName",user1.getUserName());
        createMessage.put("receiverName",user2.getUserName());
        createMessage.put("message","TEST MESSAGE");

        mvc.perform(post("/message")
                        .header("Content-Type", "application/json")
                        .content(createMessage.toString()))
                .andExpect(status().isOk())
                .andExpect(content().string("Message Sent!"));
    }
}


