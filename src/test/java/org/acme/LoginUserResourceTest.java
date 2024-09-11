package org.acme;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.quarkus.test.junit.QuarkusTest;
import org.acme.entity.LoginUser;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;

@QuarkusTest
public class LoginUserResourceTest {

    @Test
    public void testeInsertDeleteUser() {

        String jsonUser = "{\n" +
                "    \"name\": \"user-teste\",\n" +
                "    \"password\": \"userTeste123#\"\n" +
                "}";

        String resBody = given()
                .contentType("application/json")
                .body(jsonUser)
                .when()
                .post("/user")
                .then()
                .statusCode(200)
                .body("name", equalTo("user-teste"))
                .body("password", equalTo("userTeste123#")).extract().body().asString();

        Gson gson = new GsonBuilder().create();

        LoginUser user = gson.fromJson(resBody, LoginUser.class);

        given()
                .when()
                .delete("/user/remove/" + user.id)
                .then()
                .statusCode(200)
                .body(containsString("User " + user.id + " has been removed"));
    }

}
