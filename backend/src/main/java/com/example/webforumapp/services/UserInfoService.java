package com.example.webforumapp.services;

import com.example.webforumapp.models.User;
import com.example.webforumapp.models.UserInfo;
import com.example.webforumapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserInfoService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.getUserByUserName(userName);

        if (user == null){
            throw new UsernameNotFoundException("User not found with username" + userName);
        }

        return new UserInfo(user);
    }
}
