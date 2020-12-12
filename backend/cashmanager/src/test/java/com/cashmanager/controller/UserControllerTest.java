package com.cashmanager.controller;

import com.cashmanager.TestHelper;
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
public class UserControllerTest {

  @Lazy
  @Autowired
  ObjectMapper objectMapper;

  @Autowired
  private MockMvc mockMvc;

  private String token;


  @Test
  public void Register() throws Exception {
    User user = new TestHelper().createUser();
    user.setEmail("email@gmail.com");
    String payload = "{ \"email\": \"" + user.getEmail() + "\", \"username\": \"" + user.getUsername() + "\", \"password\": \"" + user.getPassword() + "\"}";

    this.mockMvc.perform(MockMvcRequestBuilders.post("/register")
        .content(payload)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isCreated());
  }

  @Test
  public void Login() throws Exception {
    String payload = "{\"username\" : \"test\", \"password\": \"password\"}";

    MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.post("/login")
        .content(payload)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.header().exists("Authorization"))
        .andReturn();
    this.token = result.getResponse().getHeader("Authorization");
  }
}

