package com.example.webforumapp.repositories;

import com.example.webforumapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer> {
    User getUserByUserName(String userName);
    User getUserById(int id);
    List<User> getUsers();
    boolean findByUserName(String userName);

}
