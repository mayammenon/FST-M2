package examples;

import io.restassured.http.ContentType;

import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

public class FirstTest {
    final String baseURI = "https://petstore.swagger.io/v2/pet";

    @Test
    public void simpleTest(){
        Response response = given().contentType(ContentType.JSON)
                .when().get(baseURI + "/findByStatus?status=sold");
        String petStatus = response.then().extract().path("[0].status");
        System.out.println(petStatus);

        response.then().statusCode(200);
        response.then().body("[0].status", equalTo("sold"));

        Assert.assertEquals(petStatus, "sold");

        }
    }
