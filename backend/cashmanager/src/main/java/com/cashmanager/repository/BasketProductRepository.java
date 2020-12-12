package com.cashmanager.repository;

import com.cashmanager.model.Basket;
import com.cashmanager.model.BasketProduct;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface BasketProductRepository extends CrudRepository<BasketProduct, Integer> {
  Set<BasketProduct> findAllByBasket(Basket basket);
}
