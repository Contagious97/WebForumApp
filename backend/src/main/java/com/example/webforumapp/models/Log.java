package com.example.webforumapp.models;


import javax.persistence.*;
import java.sql.Date;

@Entity
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String content;
    @Column
    private Date date;

    @ManyToOne
    @JoinColumn()
    private User user;

}
