package com.geekbrains.spoonacular;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


public class RecipesTest extends SpoonacularTest {

    @Test
    void testAutocompleteSearch() throws IOException {
        String actually = given()
                .param("query", "cheese")
                .param("number", 10)
                .expect()
                .when()
                .get("/recipes/autocomplete")
                .body()
                .prettyPrint();

        String expected = getResource("expectedTestAutocompleteSearch.json");

        assertJSON(expected, actually);
    }

    @Test
    void testEquipmentById() {

        given()
                .pathParam("id", 1003464)
                .expect()
                .body("equipment[0].name", is("oven"))
                .body("equipment[0].image", is("oven.jpg"))
                .when()
                .get("/recipes/{id}/equipmentWidget.json");

    }

    @Test
    void testEquipmentByIdV2() {

        EquipmentResponse response = given()
                .pathParam("id", 1003464)
                .expect()
                .when()
                .get("/recipes/{id}/equipmentWidget.json")
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
                .param("query", "Pasta With Tuna")
                .expect()
                .body("results[0].title", is("Pasta With Tuna"))
                .when()
                .get("/recipes/complexSearch");

    }

    @Test
    void GetRecipeCard() {
        given()
                .pathParam("id", 4632)
                .expect()
                .body("status", is("success"))
                .when()
                .get("/recipes/{id}/card");
    }

}
