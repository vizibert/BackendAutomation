package com.geekbrains.spoonacular;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

public class ImageClassificationTest extends SpoonacularTest {

    @Test
    void testImageClassification() {
        given()
                .multiPart(getFile("sushi.jpg"))
                .expect()
                .body("category", is("sushi"))
                .body("probability", greaterThan(0.7F))
                .when()
                .post("food/images/classify");
    }

}
