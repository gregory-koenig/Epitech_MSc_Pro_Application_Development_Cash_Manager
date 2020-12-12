package com.cashmanager;

import com.cashmanager.model.Product;
import com.cashmanager.model.User;

public class TestHelper {

  public User createUser() {
    User user = new User();
    user.setEmail("test@gmail.com");
    user.setUsername("test");
    user.setPassword("password");
    return user;
  }

  public Product createProduct() {
    Product product = new Product();
    product.setTitle("product");
    product.setDescription("description");
    product.setCashback(10);
    product.setPrice(1000);
    product.setStockQuantity(100);
    return product;
  }

}
