package com.LoginService.LoginService.mapper;

import com.LoginService.LoginService.dto.UserRegistrationDTO;
import com.LoginService.LoginService.model.User;
import org.springframework.stereotype.Component;


@Component
public class UserMapper {

    public User toUser(UserRegistrationDTO registrationDto) {
        return new User(
                null,
                registrationDto.getUsername(),
                registrationDto.getEmail(),
                registrationDto.getPhoneNumber(),
                registrationDto.getPassword()
        );
    }
}