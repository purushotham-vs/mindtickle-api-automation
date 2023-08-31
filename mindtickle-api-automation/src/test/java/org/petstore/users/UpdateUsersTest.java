package org.petstore.users;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class UpdateUsersTest {

    @Test
    public void updateUsers() {
        JSONArray usersArray = new JSONArray();

        try (BufferedReader reader = new BufferedReader(new FileReader("/Users/puru/Documents/mindtickle-assignment/mindtickle-api-automation/src/main/java/org/petstore/testData/updated-users-data.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",");
                int id = Integer.parseInt(userData[0]);
                String usernameToUpdate = userData[1];
                String firstName = userData[2];
                String lastName = userData[3];
                String email = userData[4];
                String password = userData[5];
                String phone = userData[6];
                int userStatus = Integer.parseInt(userData[7]);
//                Map<String, String> expectedUserData = readExpectedUserDataFromCSV(username);

                String requestBody = "{"
                        + "\"id\": " + id + ","
                        + "\"username\": \"" + usernameToUpdate+ "\","
                        + "\"firstName\": \"" + firstName + "\","
                        + "\"lastName\": \"" + lastName + "\","
                        + "\"email\": \"" + email + "\","
                        + "\"password\": \"" + password + "\","
                        + "\"phone\": \"" + phone + "\","
                        + "\"userStatus\": " + userStatus
                        + "}";

                Response response = RestAssured.given()
                        .pathParam("usernameToUpdate", usernameToUpdate)
                        .contentType(ContentType.JSON)
                        .body(requestBody)
                        .when()
                        .put("https://petstore.swagger.io/v2/user/{usernameToUpdate}")
                        .then()
                        .statusCode(200)
                        .body("code", equalTo(200))
                        .body("type", equalTo("unknown"))
                        .contentType(ContentType.JSON)
                        .extract()
                        .response();
            }
        }

         catch(IOException e){
                throw new RuntimeException(e);
            }
        }

}

