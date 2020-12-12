package com.cashmanager.controller;

import com.cashmanager.TestHelper;
import com.cashmanager.model.Product;
import com.cashmanager.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithSecurityContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class ProductControllerTest {

  @Lazy
  @Autowired
  ObjectMapper objectMapper;

  @Autowired
  private MockMvc mockMvc;

  private String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYwNjE3MTc0M30.8xRrr3HPlelRMgD816H-ekR5zclbBmDDZy5ODXaxIANCugZhfHxpw-42NamwDZEppwdqDqyu74COGxU7oivmKA";


  @Test
  public void create() throws Exception {
    Product product = new TestHelper().createProduct();
    String payload = "{ \"title\": \"" + product.getTitle() + "\", " +
        "\"description\": \"" + product.getDescription() + "\", " +
        "\"price\": \"" + product.getPrice() + "\", " +
        "\"stockQuantity\": \"" + product.getStockQuantity() + "\", " +
        "\"cashback\": \"" + product.getCashback() + "\"}";

    this.mockMvc.perform(MockMvcRequestBuilders.post("/product")
        .content(payload)
        .header("Authorization", token)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isCreated());
  }

  @Test
  public void show() throws Exception {

    this.mockMvc.perform(MockMvcRequestBuilders.get("/product/1")
        .header("Authorization", token)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }
}
