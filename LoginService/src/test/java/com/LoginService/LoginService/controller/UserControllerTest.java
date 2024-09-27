package com.LoginService.LoginService.controller;

import com.LoginService.LoginService.dto.UserLoginDTO;
import com.LoginService.LoginService.dto.UserRegistrationDTO;
import com.LoginService.LoginService.mapper.UserMapper;
import com.LoginService.LoginService.model.User;
import com.LoginService.LoginService.reopository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    private static final String USERNAME = "aru";
    private static final String VALID_EMAIL = "aruabi@example.com";
    private static final String PASSWORD = "aruabi";
    private static final String WRONG_PASSWORD = "aru";

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserController userController;

    private User user;
    private UserRegistrationDTO userRegistrationDTO;
    private UserLoginDTO userLoginDTO;

    @BeforeEach
    public void setUp() {

        MockitoAnnotations.openMocks(this);

        user = createUser();
        userRegistrationDTO = createUserRegistrationDTO();
        userLoginDTO = createUserLoginDTO();
    }

    private User createUser() {
        User user = new User();
        user.setEmail(VALID_EMAIL);
        user.setUsername(USERNAME);
        user.setPassword(PASSWORD);
        return user;
    }

    private UserRegistrationDTO createUserRegistrationDTO() {
        UserRegistrationDTO dto = new UserRegistrationDTO();
        dto.setEmail(VALID_EMAIL);
        dto.setUsername(USERNAME);
        dto.setPassword(PASSWORD);
        return dto;
    }

    private UserLoginDTO createUserLoginDTO() {
        UserLoginDTO dto = new UserLoginDTO();
        dto.setUsername(USERNAME);
        dto.setPassword(PASSWORD);
        return dto;
    }

    @Test
    void testRegisterUser_UsernameNull() {
        UserRegistrationDTO registrationDto = new UserRegistrationDTO(null, "test@test.com", "password");

        Mono<ResponseEntity<String>> response = userController.register(registrationDto);

        assertEquals(ResponseEntity.badRequest().body("Username cannot be null or empty"), response.block());
    }


    @Test
    public void testRegisterUser_Success() {
        when(userRepository.findByEmail(VALID_EMAIL)).thenReturn(Mono.empty());
        when(userMapper.toUser(any(UserRegistrationDTO.class))).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(Mono.just(user));

        Mono<ResponseEntity<String>> result = userController.register(userRegistrationDTO);

        StepVerifier.create(result)
                .expectNextMatches(response -> response.getStatusCode() == HttpStatus.OK &&
                        response.getBody().equals("User registered successfully"))
                .verifyComplete();

        verify(userRepository, times(1)).findByEmail(VALID_EMAIL);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testLogin_Success() {
        when(userRepository.findByUsername(USERNAME)).thenReturn(Mono.just(user));

        Mono<ResponseEntity<String>> result = userController.login(userLoginDTO);

        StepVerifier.create(result)
                .expectNextMatches(response -> response.getStatusCode() == HttpStatus.OK &&
                        response.getBody().equals("Login successful"))
                .verifyComplete();

        verify(userRepository, times(1)).findByUsername(USERNAME);
    }

    @Test
    public void testLogin_InvalidPassword() {
        when(userRepository.findByUsername(USERNAME)).thenReturn(Mono.just(user));
        userLoginDTO.setPassword(WRONG_PASSWORD); // Set wrong password

        Mono<ResponseEntity<String>> result = userController.login(userLoginDTO);

        StepVerifier.create(result)
                .expectNextMatches(response -> response.getStatusCode() == HttpStatus.UNAUTHORIZED &&
                        response.getBody().equals("Invalid username or password"))
                .verifyComplete();

        verify(userRepository, times(1)).findByUsername(USERNAME);
    }

    @Test
    public void testLogin_UserNotFound() {
        when(userRepository.findByUsername(USERNAME)).thenReturn(Mono.empty());

        Mono<ResponseEntity<String>> result = userController.login(userLoginDTO);

        StepVerifier.create(result)
                .expectNextMatches(response -> response.getStatusCode() == HttpStatus.UNAUTHORIZED &&
                        response.getBody().equals("Invalid username or password"))
                .verifyComplete();

        verify(userRepository, times(1)).findByUsername(USERNAME);
    }
}
