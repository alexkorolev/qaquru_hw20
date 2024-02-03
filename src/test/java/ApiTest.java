import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;

public class ApiTest extends BaseTest {
    @Test
    void getUsers(){
        given().
                log().all()
                .get("/api/users?page=2")
        .then()
                .log().all()
                .statusCode(200)
                .body("total",is(12))
                .body("data.id[0]",is(7));

    }

    @Test
    void getStatus(){
        given().
                log().uri()
                .get("/api2/users?page=2")
        .then()
                .log().body()
                .log().status()
                .statusCode(404);
    }

    @Test
    void get(){
        given().
                log().all()
                .get("/api/users?page=2")
        .then()
                .log().all()
                .statusCode(200)
                .body("data.id[0]",is(7));

    }

    @Test
    void getUnknown(){
        given().
                log().all()
                .get("/api/unknown/2")
        .then()
                .log().all()
                .statusCode(200)
                .body("data.id",is(2))
                .body("data.name",is("fuchsia rose"))
                .body("data.year",is(2001))
                .body("data.color",is("#C74375"))
                .body("data.pantone_value",is("17-2031"));
    }

    @Test
    void createUser(){
        String bodyData = "{\"name\":\"morpheus\",\"job\":\"leader\"}";
        given()
                .body(bodyData)
                .contentType(ContentType.JSON)
                .post("api/users")
        .then()
                .log().all()
                .statusCode(201)
                .body("name",is("morpheus"))
                .body("job",is("leader"));
    }

    @Test
    void createMyUser(){
        String bodyData = "{\"name\":\"Aleksey\",\"job\":\"QA\"}";
        given()
                .body(bodyData)
                .contentType(ContentType.JSON)
                .post("api/users")
                .then()
                .log().all()
                .statusCode(201)
                .body("name",is("Aleksey"))
                .body("job",is("QA"));
    }

    @Test
    void createUserWithoutName(){
        String bodyData = "{\"job\":\"QA\"}";
        given()
                .body(bodyData)
                .contentType(ContentType.JSON)
                .post("api/users")
                .then()
                .log().all()
                .statusCode(201)
                .body("name",isEmptyOrNullString())
                .body("job",is("QA"));
    }

    @Test
    void createUserWithoutJob(){
        String bodyData = "{\"name\":\"Aleksey\"}";
        given()
                .body(bodyData)
                .contentType(ContentType.JSON)
                .post("api/users")
                .then()
                .log().all()
                .statusCode(201)
                .body("name",is("Aleksey"))
                .body("job",isEmptyOrNullString());
    }

    @Test
    void createUserEmptyBody(){
        String bodyData = "";
        given()
                .body(bodyData)
                .contentType(ContentType.JSON)
                .post("api/users")
                .then()
                .log().all()
                .statusCode(201)
                .body("name",isEmptyOrNullString())
                .body("job",isEmptyOrNullString());
    }
}
