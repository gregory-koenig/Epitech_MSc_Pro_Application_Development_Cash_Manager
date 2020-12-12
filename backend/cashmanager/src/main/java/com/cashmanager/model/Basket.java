package com.cashmanager.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "basket", schema = "public")
public class Basket {
  public enum BasketStatus {
    PENDING,
    VALIDATED,
    CANCELED
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @ManyToOne
  @JoinColumn(name="user_id", nullable = false)
  private User user;

  @JsonManagedReference
  @OneToMany(mappedBy = "basket", cascade = CascadeType.ALL)
  private Set<BasketProduct> products = new HashSet<>();

  @Enumerated
  private BasketStatus status = BasketStatus.PENDING;

  @CreationTimestamp
  private Timestamp createdAt;
  @UpdateTimestamp
  private Timestamp updatedAt;

  public Integer getId() {
    return id;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Set<BasketProduct> getProducts() {
    return products;
  }

  public void setProducts(Set<BasketProduct> products) {
    this.products = products;
  }

  public BasketStatus getStatus() { return status; }

  public void setStatus(BasketStatus status) { this.status = status; }

  public Timestamp getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Timestamp createdAt) {
    this.createdAt = createdAt;
  }

  public Timestamp getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Timestamp updatedAt) {
    this.updatedAt = updatedAt;
  }
}
