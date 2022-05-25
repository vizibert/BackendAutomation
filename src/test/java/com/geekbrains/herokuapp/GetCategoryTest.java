package com.geekbrains.herokuapp;

import com.geekbrains.herokuapp.dto.GetCategoryResponse;
import com.geekbrains.herokuapp.service.CategoryService;
import com.geekbrains.herokuapp.util.RetrofitUtils;
import lombok.SneakyThrows;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import retrofit2.Response;

import static org.hamcrest.MatcherAssert.assertThat;

public class GetCategoryTest {
    static CategoryService categoryService;

    @BeforeAll
    static void beforeAll() {
        categoryService = RetrofitUtils.getRetrofit().create(CategoryService.class);
    }

    @SneakyThrows
    @Test
    void getCategoryByIDPositiveTest() {
        Response<GetCategoryResponse> response = categoryService.getCategory(1).execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
        assertThat(response.body().getId(), CoreMatchers.equalTo(1));
        assertThat(response.body().getTitle(), CoreMatchers.equalTo("Food"));
        response.body().getProducts().forEach( product ->
                assertThat(product.getCategoryTitle(), CoreMatchers.equalTo("Food")));
      }

    @SneakyThrows
    @Test
    void getCategoryByIDNegativeTest() {
        Response<GetCategoryResponse> response = categoryService.getCategory(43587).execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(false));
    }


}
