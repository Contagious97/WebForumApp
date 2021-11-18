package com.example.webforumapp.repositories;

import com.example.webforumapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
    User getUserByUserName(String userName);
    boolean findByUserName(String userName);

}
