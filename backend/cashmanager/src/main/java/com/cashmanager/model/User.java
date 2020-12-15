package com.cashmanager.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.util.Set;

@Entity()
@Table(name = "user", schema = "public")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column(unique = true)
  @NotBlank(message = "The Email is required")
  private String email;

  @Column(unique = true)
  @NotBlank(message = "The Username is required")
  private String username;

  private String password;

  private Integer points = 0;

  @OneToMany(mappedBy = "user")
  private Set<Basket> baskets;

  @CreationTimestamp
  private Timestamp createdAt;

  @UpdateTimestamp
  private Timestamp updatedAt;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Set<Basket> getBaskets() {
    return baskets;
  }

  public void setBaskets(Set<Basket> baskets) {
    this.baskets = baskets;
  }

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

  public Integer getPoints() { return points; }

  public void setPoints(Integer points) { this.points = points; }

  @Override
  public String toString() {
    return "{ email : " + email + " , username : " + username + ", password: " + password + "}";
  }

}
