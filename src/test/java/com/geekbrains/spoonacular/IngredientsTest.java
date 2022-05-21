package com.geekbrains.spoonacular;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class IngredientsTest extends SpoonacularTest{

    @Test
    void ComputeIngredientAmount() throws IOException {

        given()
                .pathParam("id", 9266)
                .param("nutrient", "protein")
                .param("target", 10)
                .param("unit", "oz")
                .expect()
                .body("amount", is(35.27F))
                .body("unit", is("oz"))
                .when()
                .get("/food/ingredients/{id}/amount");

    }

    @Test
    void IngredientSearch() throws Exception {
        String actually = given()
                .param("query", "salsa")
                .expect()
                .when()
                .get("/food/ingredients/search")
                .prettyPrint();

        String expected = getResource("expectedIngredientSearch.json");

        assertJSON(expected, actually);
    }

}