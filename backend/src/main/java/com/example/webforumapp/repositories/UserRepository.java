package com.example.webforumapp.repositories;

import com.example.webforumapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer> {
    User getUserByUserName(String userName);
    User getUserById(int id);
    boolean findByUserName(String userName);
    List<User> findAll();

}
