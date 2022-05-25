package com.geekbrains.herokuapp;

import com.geekbrains.herokuapp.dto.Product;
import com.geekbrains.herokuapp.service.ProductService;
import com.geekbrains.herokuapp.util.RetrofitUtils;
import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.*;

import retrofit2.Response;

import static org.hamcrest.MatcherAssert.assertThat;

public class CreateAndDeleteProductTest {
    static ProductService productService;
    Product product;
    Faker faker = new Faker();
    int id;

    @BeforeAll
    static void beforeAll() {
        productService = RetrofitUtils.getRetrofit().create(ProductService.class);
    }

    @BeforeEach
    void setUp() {
        product = new Product()
                .withTitle(faker.food().fruit())
                .withCategoryTitle("Food")
                .withPrice( (int) (Math.random() * 10000));
    }

    @SneakyThrows
    @Test
    void createTest() {
        Response<Product> response = productService.createProduct(product).execute();
        assert response.body() != null;
        id = response.body().getId();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
    }

    @SneakyThrows
    @AfterEach
    void deleteTest() {
        Response<ResponseBody> response = productService.deleteProduct(id).execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
    }
}
