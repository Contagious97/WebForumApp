package com.example.webforumapp.repositories;

import com.example.webforumapp.models.Log;
import com.example.webforumapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;


public interface LogRepository extends JpaRepository<Log,Integer> {

    List<Log> getLogsByUser(User user);
    List<Log> getLogsByUserOrderByDateDesc(User user);

}
