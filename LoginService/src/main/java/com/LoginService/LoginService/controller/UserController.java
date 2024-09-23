package com.LoginService.LoginService.controller;


import com.LoginService.LoginService.model.User;
import com.LoginService.LoginService.reopository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // Registration endpoint
    @PostMapping("/register")
    public Mono<ResponseEntity<String>> register(@RequestBody User newUser) {
        return userRepository.findByEmail(newUser.getEmail())
                .flatMap(existingUser -> {
                    if (existingUser != null) {
                        return Mono.just(ResponseEntity.badRequest().body("User already exists with this email"));
                    } else {
                        return userRepository.save(newUser)
                                .map(savedUser -> ResponseEntity.ok("User registered successfully"));
                    }
                })
                .switchIfEmpty(
                        userRepository.save(newUser)
                                .map(savedUser -> ResponseEntity.ok("User registered successfully"))
                );
    }

    // Login endpoint
    @PostMapping("/login")
    public Mono<ResponseEntity<String>> login(@RequestBody User loginUser) {
        return userRepository.findByUsername(loginUser.getUsername())
                .flatMap(user -> {
                    if (user != null && user.getPassword().equals(loginUser.getPassword())) {
                        return Mono.just(ResponseEntity.ok("Login successful"));
                    } else {
                        return Mono.just(ResponseEntity.status(401).body("Invalid username or password"));
                    }
                })
                .switchIfEmpty(Mono.just(ResponseEntity.status(401).body("Invalid username or password")));
    }
}
