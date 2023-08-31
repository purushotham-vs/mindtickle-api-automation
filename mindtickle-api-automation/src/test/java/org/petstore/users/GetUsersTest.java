package org.petstore.users;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


public class GetUsersTest {

    @Test
    public void getUsers() throws FileNotFoundException {
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
//                Map<String, String> expectedUserData = readExpectedUserDataFromCSV(username);

                Response response = RestAssured.given()
                        .pathParam("username", username)
                        .when()
                        .get("https://petstore.swagger.io/v2/user/{username}")
                        .then()
                        .statusCode(200)
                        .contentType(ContentType.JSON)
                        .extract()
                        .response();
                System.out.println(username);

                JSONObject jsonResponse = new JSONObject(response.getBody().asString());
                assertThat(jsonResponse.getString("firstName"), equalTo(firstName));
                assertThat(jsonResponse.getString("lastName"), equalTo(lastName));
                assertThat(jsonResponse.getString("email"), equalTo(email));
                assertThat(jsonResponse.getString("password"), equalTo(password));
                assertThat(jsonResponse.getString("phone"), equalTo(phone));

//                for (Map.Entry<String, String> entry : expectedUserData.entrySet()) {
//                    assert response.asString().contains(entry.getValue());
                // assertThat(response.asString(), equalTo(entry.getValue()));
            }

        }

        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private Map<String, String> readExpectedUserDataFromCSV(String username) throws IOException {
        Map<String, String> userData = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("/Users/puru/Documents/Mindtickle/mindtickle-api-automation/src/main/java/org/petstore/testData/users-data.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[1].equals(username)) {
                    // Assuming the username is in the second column
                    userData.put("id", parts[0]);
                    userData.put("username", parts[1]);
                    userData.put("firstName", parts[2]);
                    userData.put("lastName", parts[3]);
                    userData.put("email", parts[4]);
                    userData.put("password", parts[5]);
                    userData.put("phone", parts[6]);
                    userData.put("userStatus", parts[7]);
                    break;
                }
            }
        }
        return userData;
    }
}

