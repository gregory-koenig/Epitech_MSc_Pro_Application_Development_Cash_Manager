package com.cashmanager.model;

import com.cashmanager.TestHelper;
import com.cashmanager.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private UserRepository userRepository;

  @Test
  public void canCreateUser() {
    User user = new TestHelper().createUser();
    user = entityManager.persist(user);
    entityManager.flush();

    User selected = userRepository.findByEmail("test@gmail.com");

    assertThat(user, equalTo(selected));
  }

  @Test
  public void cannotCreateUserWithoutEmail() {
    User user = new User();
    user.setUsername("test");
    user.setPassword("password");
    try {
      User user1 = entityManager.persistAndFlush(user);
      assert false;
    } catch (Exception | Error error) {
      assert error.getLocalizedMessage().contains("'The Email is required'");
    }
  }

  @Test
  public void CannotCreateUserWithoutUsername() {
    User user = new User();
    user.setEmail("test@gmail.com");
    user.setPassword("password");
    try {
      User user1 = entityManager.persistAndFlush(user);
      assert false;
    } catch (Exception | Error error) {
      assert error.getLocalizedMessage().contains("'The Username is required'");
    }
  }

  @Test
  public void CannotCreateUsersWithSameEmail() {
    User user1 = new TestHelper().createUser();
    User user2 = new TestHelper().createUser();
    entityManager.persistAndFlush(user1);

    try {
      entityManager.persistAndFlush(user2);
      assert false;
    } catch (Exception | Error error) {
      assert error.getLocalizedMessage().contains("org.hibernate.exception.ConstraintViolationException: could not execute statement");
    }
  }

  @Test
  public void CannotCreateUsersWithSameUsername() {
    User user1 = new TestHelper().createUser();
    User user2 = new TestHelper().createUser();
    entityManager.persistAndFlush(user1);
    user2.setEmail("test2@gmail.com");

    try {
      entityManager.persistAndFlush(user2);
      assert false;
    } catch (Exception | Error error) {
      assert error.getLocalizedMessage().contains("org.hibernate.exception.ConstraintViolationException: could not execute statement");
    }
  }
}
