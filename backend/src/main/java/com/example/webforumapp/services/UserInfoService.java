package com.example.webforumapp.services;

import com.example.webforumapp.models.Message;
import com.example.webforumapp.models.MessageInfo;
import com.example.webforumapp.models.User;
import com.example.webforumapp.models.UserInfo;
import com.example.webforumapp.repositories.UserRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserInfoService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserInfo loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.getUserByUserName(userName);

        if (user == null){
            throw new UsernameNotFoundException("User not found with username" + userName);
        }

        return new UserInfo(user);
    }

    public List<User> getUsers() throws NotFoundException {
        List<User> users = userRepository.getUsers();

        if (users.size() == 0){
            throw new NotFoundException("No users were found!");
        }

        return users;
    }
}
