package com.cashmanager.services.mapper;

import com.cashmanager.model.Basket;
import com.cashmanager.model.BasketProduct;
import com.cashmanager.model.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class BasketMapper {
  public Map<?, ?> mapOne(Basket basket) {
    Map<Object, Object> result = new HashMap<>();

    result.put("id", basket.getId());
    result.put("status", basket.getStatus().toString());
    result.put("products", new BasketProductMapper().mapAll(basket.getProducts()));
    result.put("createdAt", basket.getCreatedAt());
    return result;
  }

  public Set<Map<?, ?>> mapAll(Set<Basket> baskets) {
    Set<Map<?, ?>> result = new HashSet<>();

    baskets.forEach(basket -> {
      result.add(mapOne(basket));
    });
    return result;
  }

}
