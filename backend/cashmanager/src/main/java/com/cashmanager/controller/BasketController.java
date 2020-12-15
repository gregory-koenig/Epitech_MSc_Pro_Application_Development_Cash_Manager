package com.cashmanager.controller;


import com.cashmanager.configuration.UserConfig;
import com.cashmanager.model.Basket;
import com.cashmanager.model.BasketProduct;
import com.cashmanager.model.Product;
import com.cashmanager.model.User;
import com.cashmanager.repository.BasketProductRepository;
import com.cashmanager.repository.BasketRepository;
import com.cashmanager.repository.ProductRepository;
import com.cashmanager.repository.UserRepository;
import com.cashmanager.services.mapper.BasketMapper;
import com.cashmanager.services.mapper.ResponseTemplate;
import org.assertj.core.error.ShouldBeAfterYear;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping(path = "/basket")
public class BasketController {

  private final BasketRepository basketRepository;
  private final UserConfig userConfig;
  private final ProductRepository productRepository;
  private final BasketProductRepository basketProductRepository;
  private final BasketMapper mapper;
  private final UserRepository userRepository;

  public BasketController(BasketRepository basketRepository,
                          ProductRepository productRepository,
                          BasketProductRepository basketProductRepository,
                          BasketMapper mapper, UserConfig userConfig, UserRepository userRepository) {
    this.basketRepository = basketRepository;
    this.productRepository = productRepository;
    this.basketProductRepository = basketProductRepository;
    this.userRepository = userRepository;
    this.mapper = mapper;
    this.userConfig = userConfig;
  }

  @PostMapping()
  public ResponseEntity<?> create(@RequestHeader("Authorization") String authorization, @RequestBody Map<String, Object> body) {
    try {
      User user = this.userConfig.getUserFromToken(authorization);
      Basket basket = new Basket();
      basket.setUser(user);
      setBasketProducts((Map<String, String>) body.get("products"), basket);
      Basket returned = basketRepository.save(basket);
      return ResponseTemplate.created(mapper.mapOne(returned));
    } catch (Exception | Error error) {
      return ResponseTemplate.badRequest(error.getLocalizedMessage());
    }
  }

  @PutMapping("/{basketId}")
  public ResponseEntity<?> updateStatus(@PathVariable Integer basketId, @RequestBody Map<String, String> body) {
    try {
      Basket basket = basketRepository.findById(basketId).get();
      Basket.BasketStatus status = Basket.BasketStatus.valueOf(body.get("status"));
      basket.setStatus(status);
      basketRepository.save(basket);
      Object returned = mapper.mapOne(basket);
      if (status == Basket.BasketStatus.VALIDATED) {
        Set<BasketProduct> products = basketProductRepository.findAllByBasket(basket);
        updateProductQuantityAndFidelity(products, basket.getUser());
      }
      return ResponseTemplate.success(returned);
    } catch (Exception | Error error) {
      return ResponseTemplate.badRequest(error.getLocalizedMessage());
    }
  }

  @GetMapping("/all")
  public ResponseEntity<?> readAll(@RequestHeader("Authorization") String authorization) {
    try {
      User user = this.userConfig.getUserFromToken(authorization);
      Set<Basket> baskets = basketRepository.findAllByUser(user);
      return ResponseTemplate.success(mapper.mapAll(baskets));
    } catch (Exception | Error error) {
      return ResponseTemplate.badRequest(error.getStackTrace().toString());
    }
  }

  @GetMapping("/last")
  public ResponseEntity<?> readOne(@RequestHeader("Authorization") String authorization) {
    try {
      User user = this.userConfig.getUserFromToken(authorization);
      Basket basket = basketRepository.findTopByUserOrderByIdDesc(user);
      return ResponseTemplate.success(mapper.mapOne(basket));
    } catch (Exception | Error error) {
      return ResponseTemplate.badRequest(error.getLocalizedMessage());
    }
  }

  @DeleteMapping("/{basketId}")
  public ResponseEntity<?> delete(@PathVariable Integer basketId) {
    try {
      basketRepository.deleteById(basketId);
      return ResponseTemplate.success(new HashMap<>().put("message", "successfully deleted"));
    } catch (Exception | Error error) {
      return ResponseTemplate.badRequest(error.getLocalizedMessage());
    }
  }

  private void setBasketProducts(Map<String, String> products, Basket basket) {
    for (String key : products.keySet()) {
      Product product = productRepository.findById(Integer.parseInt(key)).get();
      Integer quantity = Integer.parseInt(products.get(key));
      BasketProduct link = new BasketProduct();
      link.setProduct(product);
      link.setBasket(basket);
      link.setQuantity(quantity);
      basket.getProducts().add(link);
    }
  }

  private void updateProductQuantityAndFidelity(Set<BasketProduct> basketProducts, User user) {
    basketProducts.forEach(basketProduct -> {
      System.out.println(basketProduct);
      Product product = basketProduct.getProduct();
      Integer quantity = basketProduct.getQuantity();
      Integer calcQuantity = product.getStockQuantity() - quantity;
      if (calcQuantity < 0)
        calcQuantity = 0;
      product.setStockQuantity(calcQuantity);
      user.setPoints(user.getPoints() + product.getCashback());
      productRepository.save(product);
      userRepository.save(user);
      //save les points fidélités du user
    });
  }

}
