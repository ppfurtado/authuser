package com.ead.authuser.controllers;

import com.ead.authuser.models.UserModel;
import com.ead.authuser.services.UserServices;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserServices userServices;

    @GetMapping
    public ResponseEntity<List<UserModel>> getAllUser(){
        return ResponseEntity.ok(userServices.findAll());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> getOneUser(@PathVariable(value = "userId") UUID userId){
        Optional<UserModel> userModelOptional = userServices.findById(userId);
        return userModelOptional.<ResponseEntity<Object>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not Found"));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> deleteUSer(@PathVariable(value = "userId") UUID userId){
        Optional<UserModel> userModelOptional = userServices.findById(userId);
        if (userModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not fount");
        }
        userServices.deleteById(userModelOptional.get());
        return ResponseEntity.ok("User deleted sucessfully");
    }
}
