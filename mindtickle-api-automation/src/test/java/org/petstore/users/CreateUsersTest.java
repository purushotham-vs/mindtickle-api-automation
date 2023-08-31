package org.petstore.users;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static org.hamcrest.Matchers.equalTo;

public class CreateUsersTest {

    @Test
    public void createMultipleUsers() {
        JSONArray usersArray = new JSONArray();

        try (BufferedReader reader = new BufferedReader(new FileReader("/Users/puru/Documents/Mindtickle/mindtickle-api-automation/src/main/java/org/petstore/testData/users-data.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",");
                int id = Integer.parseInt(userData[0]);
                String username = userData[1];
                String firstName = userData[2];
                String lastName = userData[3];
                String email = userData[4];
                String password = userData[5];
                String phone = userData[6];
                int userStatus = Integer.parseInt(userData[7]);
                System.out.println(id);
                var user = new JSONObject();
                user.put("id", id);
                user.put("username", username);
                user.put("firstName", firstName);
                user.put("lastName", lastName);
                user.put("email", email);
                user.put("password", password);
                user.put("phone", phone);
                user.put("userStatus", userStatus);

                usersArray.put(user);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(usersArray.toString())
                .when()
                .post("https://petstore.swagger.io/v2/user/createWithArray")
                .then()
                .statusCode(200)
                .body("message", equalTo("ok"))
                .body("code", equalTo(200))
                .body("type", equalTo("unknown"));
    }
}

