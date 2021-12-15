package com.example.message_microservice;

import java.sql.Timestamp;

public class Message {


  public static Message of(String content, int  receiverId, int senderId) {
    Message data = new Message();
    data.setContent(content);
    data.setReceiverId(receiverId);
    data.setSenderId(senderId);
    data.setDate(new Timestamp(System.currentTimeMillis()));
    return data;
  }
  public static Message of(int id, String content, Timestamp createdAt, int  receiverId, int senderId) {
    Message data = new Message();
    data.setId(id);
    data.setContent(content);
    data.setDate(createdAt);
    data.setSenderId(senderId);
    data.setReceiverId(receiverId);
    return data;
  }

    private int id;

    private String content;

    private Timestamp date;


    private int senderId;
    private int receiverId;

  public int getSenderId() {
    return senderId;
  }

  public void setSenderId(int senderId) {
    this.senderId = senderId;
  }

  public int getReceiverId() {
    return receiverId;
  }

  public void setReceiverId(int receiverId) {
    this.receiverId = receiverId;
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

  }

