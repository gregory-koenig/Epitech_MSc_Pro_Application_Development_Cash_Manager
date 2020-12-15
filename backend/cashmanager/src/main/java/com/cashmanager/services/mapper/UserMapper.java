package com.cashmanager.services.mapper;

import com.cashmanager.model.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserMapper {

  public Map<?, ?>mapOne(User user) {
    Map<Object, Object> result = new HashMap<>();

    result.put("id", user.getId());
    result.put("username", user.getUsername());
    result.put("email", user.getEmail());
    result.put("points", user.getPoints());
    return result;
  }
}
