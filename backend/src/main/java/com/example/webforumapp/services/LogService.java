package com.example.webforumapp.services;

import com.example.webforumapp.models.Log;
import com.example.webforumapp.models.User;
import com.example.webforumapp.models.requests.CreateLogRequest;
import com.example.webforumapp.repositories.LogRepository;
import com.example.webforumapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
public class LogService {

    @Autowired
    private LogRepository logRepository;
    @Autowired
    private UserRepository userRepository;


    public boolean createLog(CreateLogRequest logRequest) throws Exception{

        Log log =  new Log();
        User user = userRepository.getUserByUserName(logRequest.getUserName());
        log.setContent(logRequest.getContent());
        long millis = System.currentTimeMillis();
        log.setDate(new Date(millis));
        log.setUser(user);
        logRepository.save(log);
        return true;

    }
}
