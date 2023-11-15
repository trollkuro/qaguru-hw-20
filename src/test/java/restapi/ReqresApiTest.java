package restapi;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;

public class ReqresApiTest {

    private String createBody = "{ \"name\": \"morpheus\", \"job\": \"leader\" }";
    private String updateBody = "{ \"name\": \"morpheus\", \"job\": \"dancer\" }";
    private String incorrectUpdateBody = "{ \"name\": , \"job\":  }";

    @Test
    void successfulUserCreate(){
        given()
                .log().uri()
                .log().method()
                .log().body()
                .body(createBody)
                .contentType(JSON)
                .when()
                    .post("https://reqres.in/api/users")
                .then()
                    .log().status()
                    .log().body()
                    .statusCode(201)
                    .body("name", is("morpheus"))
                    .body("job", is("leader"));
    }


    @Test
    void successfulUserUpdate(){
        given()
                .log().uri()
                .log().method()
                .log().body()
                .body(updateBody)
                .contentType(JSON)
                .when()
                    .patch("https://reqres.in/api/users/2")
                .then()
                    .log().status()
                    .log().body()
                    .statusCode(200)
                    .body("job", is("dancer"));
    }

    @Test
    void negativeUserUpdate(){
        given()
                .log().uri()
                .log().method()
                .log().body()
                .body(incorrectUpdateBody)
                .contentType(JSON)
                .when()
                    .patch("https://reqres.in/api/users/2")
                .then()
                    .log().status()
                    .log().body()
                    .statusCode(400);
    }

    @Test
    void getOnePantoneColor(){
        given()
                .log().uri()
                .log().method()
                .when()
                    .get("https://reqres.in/api/unknown/2")
                .then()
                    .log().status()
                    .log().body()
                    .statusCode(200);
    }

    @Test
    void getAllPantoneColors(){
        given()
                .log().uri()
                .log().method()
                .when()
                    .get("https://reqres.in/api/unknown")
                .then()
                    .log().status()
                    .log().body()
                    .statusCode(200)
                    .body("total", is(12));
    }
}
