package com.example.webforumapp.controllers;


import com.example.webforumapp.models.requests.CreateLogRequest;
import com.example.webforumapp.services.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/log")
public class LogController{

    @Autowired
    private LogService logService;


    @GetMapping
    public ResponseEntity<String> getLogs(){
        return null;
    }


    @PostMapping
    public ResponseEntity<String> createLog(@RequestBody CreateLogRequest logRequest){
        try {
            if (logService.createLog(logRequest)){
                return new ResponseEntity<>("Success",HttpStatus.OK);
            } else return new ResponseEntity<>("error",HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>("error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
