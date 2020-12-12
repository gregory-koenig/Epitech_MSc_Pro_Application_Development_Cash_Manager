package com.cashmanager.model;

import com.cashmanager.TestHelper;
import com.cashmanager.repository.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.Valid;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ProductTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private ProductRepository productRepository;

  @Test
  public void canCreateProduct() {
    Product product = new TestHelper().createProduct();
    entityManager.persistAndFlush(product);

    Product selected = productRepository.findProductByTitle("product");

    assertThat(product, equalTo(selected));
  }

  @Test
  public void cannotCreateProductWithoutTitle() {
    Product product = new TestHelper().createProduct();
    product.setTitle(null);

    try {
      entityManager.persistAndFlush(product);
      assert false;
    } catch (Exception | Error error) {
      System.out.println(error.getLocalizedMessage());
      assert error.getLocalizedMessage().contains("Title cannot be blank");
    }
  }

  @Test
  public void cannotCreateProductWithoutDescription() {
    Product product = new TestHelper().createProduct();
    product.setDescription(null);

    try {
      entityManager.persistAndFlush(product);
      assert false;
    } catch (Exception | Error error) {
      System.out.println(error.getLocalizedMessage());
      assert error.getLocalizedMessage().contains("Description cannot be blank");
    }
  }

  @Test
  public void cannotCreateProductWithoutPrice() {
    Product product = new TestHelper().createProduct();
    product.setPrice(null);

    try {
      entityManager.persistAndFlush(product);
      assert false;
    } catch (Exception | Error error) {
      System.out.println(error.getLocalizedMessage());
      assert error.getLocalizedMessage().contains("Price cannot be blank");
    }
  }

}