package com.example.webforumapp.models;


import javax.persistence.*;
import java.util.Set;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    private String userName;

    @Column
    private String name;


    @Column
    private String password;

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @OneToMany()
    private Set<Log> logs;

    @Override
    public String toString(){
        return "userName: " + userName+"\n"+
                "password: " + password+"\n"+
                "Name: " + name+"\n";
    }

}
