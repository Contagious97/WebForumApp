package com.example.webforumapp.controllers;


import com.example.webforumapp.models.Log;
import com.example.webforumapp.models.LogInfo;
import com.example.webforumapp.models.Message;
import com.example.webforumapp.models.MessageInfo;
import com.example.webforumapp.models.requests.CreateLogRequest;
import com.example.webforumapp.services.LogService;
import net.minidev.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/logs")
public class LogController{

    @Autowired
    private LogService logService;

    Logger logger = LoggerFactory.getLogger(LogController.class);


    @GetMapping("/{username}")
    public ResponseEntity<String> getLogs(@PathVariable(value = "username") String username){
        logger.info("Username: " + username);
        try {
            List<Log> logs = logService.getLogsByUsername(username);
            List<LogInfo> loginfos = new ArrayList<>();
            for (Log log:
                    logs) {
                loginfos.add(new LogInfo(log.getId(),username,log.getContent(),log.getDate().toString()));
            }
            String jsonArray = JSONArray.toJSONString(loginfos);
            return new ResponseEntity<>(jsonArray,HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping
    public ResponseEntity<String> createLog(@RequestBody CreateLogRequest logRequest){
        logger.info(logRequest.toString());
        try {
            if (logService.createLog(logRequest)){
                return new ResponseEntity<>("Success",HttpStatus.OK);
            } else return new ResponseEntity<>("error",HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>("error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
