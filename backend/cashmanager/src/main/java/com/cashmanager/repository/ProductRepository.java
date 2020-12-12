package com.cashmanager.repository;

import com.cashmanager.model.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Integer> {
  Product findProductById(Integer id);
  Product findProductByTitle(String title);
}