package com.cashmanager.model;

import net.bytebuddy.implementation.bind.annotation.Default;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity()
@Table(name = "product", schema = "public")
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @NotBlank(message = "Title cannot be blank")
  @Column(unique = true)
  private String title;

  @NotBlank(message = "Description cannot be blank")
  private String description;

  @NotNull(message = "Price cannot be blank")
  private Integer price;

  private Integer cashback = 0;

  private Integer stockQuantity = 1;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Integer getPrice() {
    return price;
  }

  public void setPrice(Integer price) {
    this.price = price;
  }

  public Integer getCashback() {
    return cashback;
  }

  public void setCashback(Integer cashback) {
    this.cashback = cashback;
  }

  public Integer getStockQuantity() {
    return stockQuantity;
  }

  public void setStockQuantity(Integer stockQuantity) {
    this.stockQuantity = stockQuantity;
  }
}
