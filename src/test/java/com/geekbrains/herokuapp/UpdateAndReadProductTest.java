package com.geekbrains.herokuapp;

import com.geekbrains.herokuapp.dto.Product;
import com.geekbrains.herokuapp.service.ProductService;
import com.geekbrains.herokuapp.util.RetrofitUtils;
import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.*;

import retrofit2.Response;

import static org.hamcrest.MatcherAssert.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UpdateAndReadProductTest {
    static ProductService productService;
    Product product;
    Faker faker = new Faker();
    int id = 431;

    @BeforeAll
    static void beforeAll() {
        productService = RetrofitUtils.getRetrofit().create(ProductService.class);
    }

    @SneakyThrows
    @Test
    @Order(1)
    void updateTest() {
        product = new Product()
                .withTitle(faker.food().fruit())
                .withCategoryTitle("Food")
                .withId(id)
                .withPrice( (int) (Math.random() * 10000));
        Response<Product> response = productService.updateProduct(product).execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
    }

    @SneakyThrows
    @Test
    @Order(2)
    void readTest() {

        Response<Product> response = productService.getProduct(id).execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
        assertThat(response.body().getId(), CoreMatchers.equalTo(id));
        assertThat(response.body().getCategoryTitle(), CoreMatchers.equalTo("Food"));
    }

}
