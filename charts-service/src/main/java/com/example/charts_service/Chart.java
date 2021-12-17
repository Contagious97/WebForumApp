package com.example.charts_service;

import java.sql.Timestamp;

public class Chart {


  public static Chart of(String content) {
    Chart data = new Chart();
    data.setData(content);
    data.setDate(new Timestamp(System.currentTimeMillis()));
    return data;
  }
  public static Chart of(int id, String content, Timestamp createdAt) {
    Chart data = new Chart();
    data.setId(id);
    data.setData(content);
    data.setDate(createdAt);
    return data;
  }
  public static Chart of(int id, String content) {
    Chart data = new Chart();
    data.setId(id);
    data.setData(content);
    return data;
  }

    private int id;

    private String data;

    private Timestamp date;


  public int getId() {
      return id;
    }

    public void setId(int id) {
      this.id = id;
    }

    public String getData() {
      return data;
    }

    public void setData(String content) {
      this.data = content;
    }

    public Timestamp getDate() {
      return date;
    }

    public void setDate(Timestamp date) {
      this.date = date;
    }

  }

