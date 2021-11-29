package com.example.webforumapp.models.replys;

import com.example.webforumapp.models.User;

public class UserDetails {

    private String name;
    private String userName;

    public UserDetails(User user){
        this.name = user.getName();
        this.userName = user.getUserName();    }
    public UserDetails(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
