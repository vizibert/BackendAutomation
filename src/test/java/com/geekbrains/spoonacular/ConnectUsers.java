package com.geekbrains.spoonacular;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class ConnectUsers {

    private static final String API_KEY = "101c5acf23d5483599af36154b5d6849";

    private String requestBody = "{\n" +
            "  \"username\": \"your user's name\",\n" +
            "  \"firstName\": \"your user's first name\",\n" +
            "  \"lastName\": \"your user's last name name\",\n" +
            "  \"email\": \"your user's email\" \n}";

    @BeforeAll
    static void beforeAll() {
        RestAssured.baseURI = "https://api.spoonacular.com/users/";
    }

    @Test
    void ConnectUsersTest() throws IOException {

        given()
                .log()
                .all()
                .header("Content-type", "application/json")
                .queryParam("apiKey", API_KEY)
                .and()
                .body(requestBody)
                .expect()
                .log()
                .body()
                .body("status", is("success"))
                .when()
                .post("connect");
    }
}
