package com.example.webforumapp.models.replys;

import java.sql.Date;

public class LogInfo {
    private int id;

    private String username;

    private String content;

    private String date;

    public LogInfo(int id, String username,String content, String date){
        this.id = id;
        this.content = content;
        this.username = username;
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return date;
    }
}
