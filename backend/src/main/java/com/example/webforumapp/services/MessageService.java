package com.example.webforumapp.services;

import com.example.webforumapp.models.Message;
import com.example.webforumapp.models.User;
import com.example.webforumapp.repositories.MessageRepository;
import com.example.webforumapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MessageRepository messageRepository;

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

    public boolean sendMessage(String message, int senderId, int receiverId) throws Exception{
        if (!userRepository.existsById(senderId)|| !userRepository.existsById(receiverId))
            throw new Exception("Users not found");

        System.out.println("Message: " + message + "senderId: " + senderId + "receiverId: " + receiverId);
        Message messageToSend = new Message();
        messageToSend.setMessage(message);
        messageToSend.setSender(userRepository.getUserById(senderId));
        messageToSend.setReceiver(userRepository.getUserById(receiverId));
        messageRepository.save(messageToSend);
        return true;
    }
}
