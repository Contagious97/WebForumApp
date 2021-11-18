package com.example.webforumapp.services;

import com.example.webforumapp.models.User;
import com.example.webforumapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    private UserRepository userRepository;



    public boolean signin(String userName, String passWord) throws Exception{
        try {
            User user = userRepository.getUserByUserName(userName);
            if (user == null)
                throw new UsernameNotFoundException("User not found");
            if (BCrypt.checkpw(passWord,user.getPassword())){
                return true;
            } else throw new Exception("Wrong password");
        } catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    public User signUp(String userName, String password, String name) throws Exception {
        String hashedPassword = BCrypt.hashpw(password,BCrypt.gensalt());
        User userToAdd = new User();

        User user = userRepository.getUserByUserName(userName);
        if (user != null){
            throw new Exception("Username Taken");
        }
        userToAdd.setUserName(userName);
        userToAdd.setPassword(hashedPassword);
        userToAdd.setName(name);

        userRepository.save(userToAdd);
        return userToAdd;
    }
}
