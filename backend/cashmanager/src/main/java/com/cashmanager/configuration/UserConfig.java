package com.cashmanager.configuration;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.cashmanager.model.User;
import com.cashmanager.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.cashmanager.configuration.SecurityConstants.*;

@Configuration
public class UserConfig {

  private final UserRepository userRepository;

  public UserConfig(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User getUserFromToken(String token) {
    String username = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
        .build()
        .verify(token.replace(TOKEN_PREFIX, ""))
        .getSubject();
    return userRepository.findByUsername(username);
  }
}
