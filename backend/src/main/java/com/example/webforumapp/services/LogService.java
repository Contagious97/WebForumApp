package com.example.webforumapp.services;

import com.example.webforumapp.controllers.AuthController;
import com.example.webforumapp.models.Log;
import com.example.webforumapp.models.Message;
import com.example.webforumapp.models.User;
import com.example.webforumapp.models.requests.CreateLogRequest;
import com.example.webforumapp.repositories.LogRepository;
import com.example.webforumapp.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Service
public class LogService {

    @Autowired
    private LogRepository logRepository;
    @Autowired
    private UserRepository userRepository;

    Logger logger = LoggerFactory.getLogger(LogService.class);


    public boolean createLog(CreateLogRequest logRequest) throws Exception{

        Log log =  new Log();
        User user = userRepository.getUserByUserName(logRequest.getUserName());
        log.setContent(logRequest.getContent());
        long millis = System.currentTimeMillis();
        java.util.Date date = new java.util.Date();
        log.setDate(new Timestamp(date.getTime()));
        log.setUser(user);
        logRepository.save(log);
        return true;
    }

    public List<Log> getLogsByUsername(String username) throws Exception{
        logger.info(username);
        User user = userRepository.getUserByUserName(username);
        logger.info(user!=null?user.toString():"null");
        if (user == null){
            throw new UsernameNotFoundException("User not found!");
        }
        List<Log> logs = logRepository.getLogsByUserOrderByDateDesc(user);

        if (logs == null || logs.size() == 0){
            throw new Exception("No messages found");
        }

        return logs;
    }
}
