package com.cashmanager.controller;

import com.cashmanager.model.User;
import com.cashmanager.repository.UserRepository;
import com.cashmanager.configuration.UserConfig;
import com.cashmanager.services.mapper.ResponseTemplate;
import com.cashmanager.services.mapper.UserMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
public class UserController {

  private final UserRepository userRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;
  private final UserConfig userConfig;
  private final UserMapper mapper;

  public UserController(UserRepository userRepository,
                        BCryptPasswordEncoder bCryptPasswordEncoder,
                        UserConfig userConfig, UserMapper mapper) {
    this.userRepository = userRepository;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    this.userConfig = userConfig;
    this.mapper = mapper;
  }

  @PostMapping(path = "/register", consumes = "application/json", produces = "application/json")
  ResponseEntity<?> register(@Valid @RequestBody User user) {
    if (userRepository.findByEmail(user.getEmail()) != null)
      return ResponseTemplate.badRequest("email already used.");

    user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    try {
      User returned = userRepository.save(user);
      return ResponseTemplate.created(mapper.mapOne(returned));
    } catch (Exception | Error error) {
      return ResponseTemplate.badRequest(error.getLocalizedMessage());
    }
  }

  @PutMapping("/user")
  ResponseEntity<?> update(@RequestHeader("Authorization") String authorization, @Valid @RequestBody User user) {
    try {
      User returned = userConfig.getUserFromToken(authorization);
      returned.setEmail(user.getEmail());
      returned.setUsername(user.getUsername());
      returned.setPoints(user.getPoints());
      userRepository.save(returned);
      return ResponseTemplate.success(mapper.mapOne(returned));
    } catch (Exception | Error error) {
      return ResponseTemplate.badRequest(error.getLocalizedMessage());
    }
  }

  @GetMapping("/user")
  @ResponseBody
  ResponseEntity<?> getUser(@RequestHeader("Authorization") String authorization) {
    User user = this.userConfig.getUserFromToken(authorization);
    return ResponseTemplate.success(mapper.mapOne(user));
  }

}
