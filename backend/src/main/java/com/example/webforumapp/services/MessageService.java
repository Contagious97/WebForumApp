package com.example.webforumapp.services;

import com.example.webforumapp.models.Message;
import com.example.webforumapp.models.User;
import com.example.webforumapp.models.requests.MessageRequest;
import com.example.webforumapp.repositories.MessageRepository;
import com.example.webforumapp.repositories.UserRepository;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MessageRepository messageRepository;

    Logger logger = LoggerFactory.getLogger(MessageService.class);

    RestTemplate restTemplate = new RestTemplate();

    public List<Message> getMessagesById(int userId) throws Exception{
        User user = userRepository.getUserById(userId);
        if (user == null){
            throw new UsernameNotFoundException("User not found!");
        }
        List<Message> messages = messageRepository.getMessagesByReceiver(user);

        if (messages == null || messages.size() == 0){
            throw new Exception("No messages found");
        }

        return messages;
    }

    public boolean sendMessage(String message, String sender, String receiver) throws Exception{

        logger.info("Message: " + message + "sender: " + sender + "receiver: " + receiver);

        User userSender = userRepository.getUserByUserName(sender);
        User userReceiver = userRepository.getUserByUserName(receiver);
        if (userReceiver == null || userSender == null)
            throw new Exception("Users not found");

        Message messageToSend = new Message();
        messageToSend.setMessage(message);
        messageToSend.setSender(userRepository.getUserByUserName(sender));
        messageToSend.setReceiver(userRepository.getUserByUserName(receiver));
        messageRepository.save(messageToSend);
        return true;
    }


    public String getMessagesByUserName(String username){
      try {
        User user = userRepository.getUserByUserName(username);
        String url = "http://localhost:8889/messages/" + user.getId();
        logger.info(url);
        String result = this.restTemplate.getForObject(url, String.class);
        logger.info("Messages: " + result);
        return result;
      } catch (Exception e){
        logger.info(e.getMessage());
        return null;
      }
    }

    public boolean sendMessage(MessageRequest messageRequest){
      try {
        User sender = userRepository.getUserByUserName(messageRequest.getSenderName());
        User receiver = userRepository.getUserByUserName(messageRequest.getReceiverName());
        String url = "http://localhost:8889/messages";
        JSONObject object = new JSONObject();
        object.put("content",messageRequest.getMessage());
        object.put("receiverId",receiver.getId());
        object.put("senderId",sender.getId());

        logger.info(object.toJSONString());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(object.toJSONString(),headers);
        ResponseEntity<String> result = this.restTemplate.postForEntity(url,request, String.class);
        logger.info(result.toString());
        if (result.getStatusCode() == HttpStatus.OK){
          return true;
        } else return false;

      } catch (Exception e){
        logger.info(e.getMessage());
        return false;
      }
    }


}
