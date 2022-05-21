package com.geekbrains.spoonacular;

import io.restassured.RestAssured;
import net.javacrumbs.jsonunit.JsonAssert;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class IngredientsTest {

    private static final String API_KEY = "101c5acf23d5483599af36154b5d6849";

    @BeforeAll
    static void beforeall() {
        RestAssured.baseURI = "https://api.spoonacular.com/food/ingredients/";
    }

    @Test
    void ComputeIngredientAmount() throws IOException {

        given()
                .log()
                .all()
                .param("apiKey", API_KEY)
                .pathParam("id", 9266)
                .param("nutrient", "protein")
                .param("target", 10)
                .param("unit", "oz")
                .expect()
                .log()
                .body()
                .body("amount",is(35.27F))
                .body("unit",is("oz"))
                .when()
                .get("{id}/amount")
                .body()
                .prettyPrint();
    }

    @Test
    void IngredientSearch() throws IOException {
        String actually = given()
                .log()
                .all()
                .param("apiKey", API_KEY)
                .param("query", "salsa")
                .expect()
                .log()
                .all()
                .when()
                .get("search")
                .body()
                .prettyPrint();

        String expected = getResourceAsString("testIngredientSearch/expected.json");

        JsonAssert.assertJsonEquals(
                expected,
                actually,
                JsonAssert.when(Option.IGNORING_ARRAY_ORDER)
        );
    }


    public String getResourceAsString(String resource) throws IOException {
        InputStream stream = getClass().getResourceAsStream(resource);
        assert stream != null;
        byte[] bytes = stream.readAllBytes();
        return new String(bytes, StandardCharsets.UTF_8);
    }

}