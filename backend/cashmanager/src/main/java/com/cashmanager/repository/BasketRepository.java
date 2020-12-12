package com.cashmanager.repository;

import com.cashmanager.model.Basket;
import com.cashmanager.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface BasketRepository extends CrudRepository<Basket, Integer> {
  Set<Basket> findAllByUser(User user);
  Basket findTopByUserOrderByIdDesc(User user);
}