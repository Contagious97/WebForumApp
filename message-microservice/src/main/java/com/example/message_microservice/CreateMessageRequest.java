package com.example.message_microservice;

public class CreateMessageRequest {

  private int senderId;

  private int receiverId;

  private String content;


  public int getReceiverId() {
    return receiverId;
  }

  public void setReceiverId(int receiverId) {
    this.receiverId = receiverId;
  }

  public int getSenderId() {
    return senderId;
  }

  public void setSenderId(int senderId) {
    this.senderId = senderId;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }


}
