package com.example.webforumapp.services;

import com.example.webforumapp.controllers.AuthController;
import com.example.webforumapp.models.Log;
import com.example.webforumapp.models.Message;
import com.example.webforumapp.models.User;
import com.example.webforumapp.models.replys.UserInfo;
import com.example.webforumapp.models.requests.CreateLogRequest;
import com.example.webforumapp.repositories.LogRepository;
import com.example.webforumapp.repositories.UserRepository;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

    RestTemplate restTemplate = new RestTemplate();

    public boolean createLog(CreateLogRequest logRequest) throws Exception{

        Log log =  new Log();
        logger.info("Log request username: " + logRequest.getUserName());
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

    public String getLogsForUser(String userName){

      try {
        User user = userRepository.getUserByUserName(userName);
        String url = "http://localhost:8888/logs/" + user.getId();
        return this.restTemplate.getForObject(url, String.class);
      } catch (Exception e){
        logger.info(e.getMessage());
        return null;
      }
    }

    public String getAllLogs(){
      try {
        String url = "http://localhost:8888/logs";
        return this.restTemplate.getForObject(url, String.class);
      } catch (Exception e){
        logger.info(e.getMessage());
        return null;
      }
    }

    public boolean saveLog(CreateLogRequest logRequest){
      try {
        User user = userRepository.getUserByUserName(logRequest.getUserName());
        String url = "http://localhost:8888/logs";
        JSONObject object = new JSONObject();
        object.put("userId",user.getId());
        object.put("content",logRequest.getContent());
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
