package com.cashmanager.repository;

import com.cashmanager.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
  User findByEmail(String email);
  User findByUsername(String username);
}
