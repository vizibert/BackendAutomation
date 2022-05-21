package com.geekbrains.spoonacular;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.jupiter.api.BeforeAll;

public class SpoonacularTest extends AbstractTest{

    private static final String API_KEY = "101c5acf23d5483599af36154b5d6849";

    @BeforeAll
    static void beforeAll() {

        RestAssured.baseURI = "https://api.spoonacular.com/";

        RestAssured.requestSpecification = new RequestSpecBuilder()
                .addQueryParam("apiKey", API_KEY)
                .log(LogDetail.ALL)
                .addFilter(new ResponseLoggingFilter(LogDetail.BODY))
                .build();

    }
}
