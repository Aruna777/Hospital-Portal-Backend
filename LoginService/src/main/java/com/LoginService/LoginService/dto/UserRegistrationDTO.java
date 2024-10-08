package com.LoginService.LoginService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationDTO {
    private Integer userId;
    private String username;
    private String email;
    private String phoneNumber;
    private String password;
}
