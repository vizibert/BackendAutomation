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
import static org.hamcrest.Matchers.*;


public class RecipesTest {

    private static final String API_KEY = "101c5acf23d5483599af36154b5d6849";

    @BeforeAll
    static void beforeAll() {
        RestAssured.baseURI = "https://api.spoonacular.com/recipes/";

    }

    @Test
    void testAutocompleteSearch() throws IOException {
        String actually = given()
                .log()
                .all()
                .param("apiKey", API_KEY)
                .param("query", "cheese")
                .param("number", 10)
                .expect()
                .log()
                .all()
                .when()
                .get("autocomplete")
                .body()
                .prettyPrint();

        String expected = getResourceAsString("testAutocompleteSearch/expected.json");

        JsonAssert.assertJsonEquals(
                expected,
                actually,
                JsonAssert.when(Option.IGNORING_ARRAY_ORDER)
        );
    }


    @Test
    void testEquipmentById() {

        given()
                .log()
                .all()
                .param("apiKey", API_KEY)
                .pathParam("id", 1003464)
                .expect()
                .log()
                .body()
                .body("equipment[0].name", is("oven"))
                .body("equipment[0].image", is("oven.jpg"))
                .when()
                .get("{id}/equipmentWidget.json");

    }

    @Test
    void testEquipmentByIdV2() {

        EquipmentResponse response = given()
                .log()
                .all()
                .param("apiKey", API_KEY)
                .pathParam("id", 1003464)
                .expect()
                .log()
                .body()
                .when()
                .get("{id}/equipmentWidget.json")
                .as(EquipmentResponse.class);

        response.getEquipment()
                .stream()
                .filter(item -> item.getName().equals("oven"))
                .peek(item -> item.getImage().equals("oven.jpg"))
                .findAny()
                .orElseThrow();
    }

    @Test
    void ComplexSearch() {

        given()
                .log()
                .all()
                .param("apiKey", API_KEY)
                .param("query", "Pasta With Tuna")
                .expect()
                .log()
                .body()
                .body("results[0].title", is("Pasta With Tuna"))
                .when()
                .get("complexSearch");

    }

    @Test
    void GetRecipeCard() throws IOException {
        given()
                .log()
                .all()
                .param("apiKey", API_KEY)
                .pathParam("id", 4632)
                .expect()
                .log()
                .body()
                .body("status", is("success"))
                .when()
                .get("{id}/card");
    }

    public String getResourceAsString(String resource) throws IOException {
        InputStream stream = getClass().getResourceAsStream(resource);
        assert stream != null;
        byte[] bytes = stream.readAllBytes();
        return new String(bytes, StandardCharsets.UTF_8);
    }

}