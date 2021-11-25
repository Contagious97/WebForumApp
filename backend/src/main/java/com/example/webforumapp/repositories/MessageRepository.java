package com.example.webforumapp.repositories;

import com.example.webforumapp.models.Message;
import com.example.webforumapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface MessageRepository extends JpaRepository<Message,Integer> {
    public List<Message> getMessagesByReceiver(User user);
    public List<Message> getMessagesBySender(User user);

}
