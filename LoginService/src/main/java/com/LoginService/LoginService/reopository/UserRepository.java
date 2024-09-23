package com.LoginService.LoginService.reopository;

import com.LoginService.LoginService.model.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserRepository  extends ReactiveCrudRepository<User, Integer> {

    Mono<User> findByUsername(String username);

    Mono<User> findByEmail(String email);
}
