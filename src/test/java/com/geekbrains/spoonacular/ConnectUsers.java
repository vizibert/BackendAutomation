package com.geekbrains.spoonacular;

import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class ConnectUsers extends SpoonacularTest {

    private String requestBody = "{\n" +
            "  \"username\": \"your user's name\",\n" +
            "  \"firstName\": \"your user's first name\",\n" +
            "  \"lastName\": \"your user's last name name\",\n" +
            "  \"email\": \"your user's email\" \n}";

    @Test
    void ConnectUsersTest() {

        given()
                .header("Content-type", "application/json")
                .body(requestBody)
                .expect()
                .body("status", is("success"))
                .when()
                .post("/users/connect");
    }
}
