package com.example.user_microservice;

import java.sql.Timestamp;

public class Log {


  public static Log of(String content, int userId) {
    Log data = new Log();
    data.setContent(content);
    data.setUserId(userId);
    data.setDate(new Timestamp(System.currentTimeMillis()));
    return data;
  }
  public static Log of(int id, String content, Timestamp createdAt,int userId) {
    Log data = new Log();
    data.setId(id);
    data.setContent(content);
    data.setDate(createdAt);
    data.setUserId(userId);
    return data;
  }

    private int id;

    private String content;

    private Timestamp date;

    private int userId;

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public int getId() {
      return id;
    }

    public void setId(int id) {
      this.id = id;
    }

    public String getContent() {
      return content;
    }

    public void setContent(String content) {
      this.content = content;
    }

    public Timestamp getDate() {
      return date;
    }

    public void setDate(Timestamp date) {
      this.date = date;
    }

    public int getUserId() {
      return userId;
    }

  }

