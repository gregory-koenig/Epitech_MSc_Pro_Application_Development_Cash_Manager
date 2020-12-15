package com.cashmanager.services.mapper;

import com.cashmanager.model.Basket;
import com.cashmanager.model.BasketProduct;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class BasketProductMapper {
  public Map<?, ?> mapOne(BasketProduct basketProduct) {
    Map<Object, Object> result = new HashMap<>();
    Integer price = basketProduct.getProduct().getPrice() * basketProduct.getQuantity();
    result.put("id", basketProduct.getProduct().getId());
    result.put("cashback", basketProduct.getProduct().getCashback());
    result.put("quantity", basketProduct.getQuantity());
    result.put("price", price);
    result.put("stockQuantity", basketProduct.getProduct().getStockQuantity());
    result.put("title", basketProduct.getProduct().getTitle());
    result.put("description", basketProduct.getProduct().getDescription());
    return result;
  }

  public Set<Map<?, ?>> mapAll(Set<BasketProduct> basketProducts) {
    Set<Map<?, ?>> result = new HashSet<>();

    basketProducts.forEach(basketProduct -> {
      result.add(mapOne(basketProduct));
    });
    return result;
  }

}

