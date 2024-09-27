package com.LoginService.LoginService.controller;

import com.LoginService.LoginService.dto.UserLoginDTO;
import com.LoginService.LoginService.dto.UserRegistrationDTO;
import com.LoginService.LoginService.mapper.UserMapper;
import com.LoginService.LoginService.reopository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserController(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @PostMapping("/register")
    public Mono<ResponseEntity<String>> register(@RequestBody UserRegistrationDTO registrationDto) {
        if (registrationDto.getUsername() == null || registrationDto.getUsername().isEmpty()) {
            return Mono.just(ResponseEntity.badRequest().body("Username cannot be null or empty"));
        }

        return userRepository.findByEmail(registrationDto.getEmail())
                .flatMap(existingUser ->
                        Mono.just(ResponseEntity.badRequest().body("User already exists with this email")))
                .switchIfEmpty(
                        userRepository.save(userMapper.toUser(registrationDto))
                                .map(savedUser -> ResponseEntity.ok("User registered successfully"))
                                .onErrorReturn(ResponseEntity.internalServerError().body("Error occurred while registering user"))
                );
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<String>> login(@RequestBody UserLoginDTO loginDto) {
        return userRepository.findByUsername(loginDto.getUsername())
                .filter(user -> user.getPassword().equals(loginDto.getPassword()))
                .map(user -> ResponseEntity.ok("Login successful"))
                .switchIfEmpty(Mono.just(ResponseEntity.status(401).body("Invalid username or password")));
    }
}
