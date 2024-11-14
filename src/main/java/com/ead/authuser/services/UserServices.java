package com.ead.authuser.services;

import com.ead.authuser.models.UserModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserServices {
    List<UserModel> findAll();

    Optional<UserModel> findById(UUID userId);

    void deleteById(UserModel userModel6);

    void save(UserModel userModel);

    boolean existsUsername(String username);

    boolean existsEmail(String email);
}
