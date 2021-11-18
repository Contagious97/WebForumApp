package com.example.webforumapp.controllers;

import com.example.webforumapp.models.LogInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/feed")
public class FeedController {


    @GetMapping
    public List<LogInfo> getLogs(){
        return null;
    }
}
