package com.ead.authuser.dtos;

import lombok.Data;

import java.util.UUID;

@Data
public class UserDto {
    private UUID userId;
    private String username;
    private String email;
    private String password;
    private String outPassword;
    private String fullName;
    private String phoneNumber;
    private String cpf;
    private String imageUrl;
}
