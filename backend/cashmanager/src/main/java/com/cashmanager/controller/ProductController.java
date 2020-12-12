package com.cashmanager.controller;

import com.cashmanager.model.Product;
import com.cashmanager.repository.ProductRepository;
import com.cashmanager.services.mapper.ResponseTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/product")
public class ProductController {

  private final ProductRepository productRepository;

  public ProductController(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @PostMapping()
  public ResponseEntity<?> create(@Valid @RequestBody Product product) {
    try {
      Product returned = productRepository.save(product);
      return ResponseTemplate.created(returned);
    } catch (Exception | Error error) {
      return ResponseTemplate.badRequest(error.getLocalizedMessage());
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> show(@PathVariable Integer id) {
    try {
      Product product = productRepository.findProductById(id);
      return ResponseTemplate.success(product);
    } catch (Exception | Error error) {
      return ResponseTemplate.badRequest(error.getLocalizedMessage());
    }
  }
}
