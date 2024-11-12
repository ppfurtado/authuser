package com.ead.authuser.services;

import com.ead.authuser.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServicesImpl implements UserServices {

    private final UserRepository userRepository;

    public UserServicesImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
