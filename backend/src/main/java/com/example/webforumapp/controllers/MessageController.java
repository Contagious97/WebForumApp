package com.example.webforumapp.controllers;


import com.example.webforumapp.models.Message;
import com.example.webforumapp.models.MessageInfo;
import com.example.webforumapp.models.User;
import com.example.webforumapp.models.UserInfo;
import com.example.webforumapp.models.requests.MessageRequest;
import com.example.webforumapp.repositories.UserRepository;
import com.example.webforumapp.services.MessageService;
import com.example.webforumapp.services.UserInfoService;
import net.minidev.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private MessageService messageService;

    @GetMapping
    public ResponseEntity<String> getMessages(@RequestBody Integer userId){
        try {
            List<Message> messages = messageService.getMessagesById(userId);
            List<MessageInfo> messageInfos = new ArrayList<>();
            for (Message message:
                 messages) {
                messageInfos.add(new MessageInfo(message.getMessage(), message.getSender().getUserName()));
            }
            String jsonArray = JSONArray.toJSONString(messageInfos);
            return new ResponseEntity<>(jsonArray,HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<String> postMessage(@RequestBody MessageRequest request){
        try {
            if (messageService.sendMessage(request.getMessage(), request.getSenderId(), request.getReceiverId())){
                return new ResponseEntity<>("Message Sent!", HttpStatus.OK);
            } else return new ResponseEntity<>("Couldn't send message", HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>("error:" + e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

}
