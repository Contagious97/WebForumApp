package com.example.webforumapp.models.requests;

public class UserDetailsRequest {

    private String name;
    private String userName;
    private String password;

    public String getUserName() {
        return userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    @Override
    public String toString(){
        return "userName: " + userName+"\n"+
                "password: " + password+"\n";
    }
}
