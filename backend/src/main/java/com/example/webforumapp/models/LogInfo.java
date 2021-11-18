package com.example.webforumapp.models;

import java.sql.Date;

public class LogInfo {
    private int id;

    private String content;

    private Date date;

    public LogInfo(int id, String content, Date date){
        this.id = id;
        this.content = content;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public Date getDate() {
        return date;
    }
}
