package com.ead.authuser.controllers;

import com.ead.authuser.dtos.UserDto;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.services.UserServices;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    public static final String USER_NOT_FOUND = "User not Found";
    private final UserServices userServices;

    @GetMapping
    public ResponseEntity<List<UserModel>> getAllUser(){
        return ResponseEntity.ok(userServices.findAll());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> getOneUser(@PathVariable(value = "userId") UUID userId){
        Optional<UserModel> userModelOptional = userServices.findById(userId);
        return userModelOptional.<ResponseEntity<Object>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(USER_NOT_FOUND));
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

    @PutMapping("/{userId}")
    public ResponseEntity<Object> putUser(@RequestBody @JsonView(UserDto.UserView.UserPut.class) UserDto userDto,
                                          @PathVariable(value = "userId") UUID userId){
        Optional<UserModel> userModelOptional = userServices.findById(userId);
        if (userModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(USER_NOT_FOUND);
        }
        BeanUtils.copyProperties(userDto, userModelOptional);
        userModelOptional.get().setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        userServices.save(userModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body(userModelOptional);
    }

    @PutMapping("/{userId}/password")
    public ResponseEntity<Object> putdatePassword(@RequestBody @JsonView(UserDto.UserView.PasswordPut.class) UserDto userDto,
                                          @PathVariable(value = "userId") UUID userId){
        Optional<UserModel> userModelOptional = userServices.findById(userId);
        if (userModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(USER_NOT_FOUND);
        }
        if (Objects.equals(userModelOptional.get().getPassword(), userDto.getOldPassword())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Mismatched old password ");
        }else {
            BeanUtils.copyProperties(userDto, userModelOptional);
            userModelOptional.get().setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
            userServices.save(userModelOptional.get());
            return ResponseEntity.status(HttpStatus.OK).body("Password updated sucessfully");
        }
    }

    @PutMapping("/{userId}/image")
    public ResponseEntity<Object> putdateImage(@RequestBody @JsonView(UserDto.UserView.ImagePut.class) UserDto userDto,
                                                  @PathVariable(value = "userId") UUID userId){
        Optional<UserModel> userModelOptional = userServices.findById(userId);
        if (userModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(USER_NOT_FOUND);
        }else {
            BeanUtils.copyProperties(userDto, userModelOptional.get());
            userModelOptional.get().setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
            userServices.save(userModelOptional.get());
            return ResponseEntity.status(HttpStatus.OK).body(userModelOptional);
        }
    }
}
