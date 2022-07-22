package liveProject;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.response.Response;
import org.hamcrest.Condition;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class GitHubProject {
    RequestSpecification reqSpec;

    String sshKey;

    int sshKeyId;

    @BeforeClass
    public void setUp() {

        reqSpec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addHeader("Authorization", "token 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")
                .setBaseUri("https://api.github.com")
                .build();
        sshKey = "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQCOtyqwvMg6+ABbKJzUo1mU84fAz4DzOaBa2957WcDcLc2HHmR0nrxI2FFULWAhNv3OBhufVz7iJBlyOXZSY3/FeI8EEGK6LozYJPOaLLN3fye4chgtlRTI2LmQFXhAZeVUnzhXdKB24sp/vxurGQMHPJRAtCxTNwI40UkwhndipywozwLqCwqNkugtKLDxL0jgGs+sEI2BRt+0Es2XzXXpkNuZ71Upy03s8gDwm0Ozd8xzc6Xhtz+9tuu94cMLOnAN1Br84nc8bDedSogpbva4dYClNYZWif+Qv4lah+MBt8XZIHSdPMweMkwQeYykyWzQIYflPPqV1wweXWnVjlvD";
    }
    @Test(priority=1)
    public void addKeys(){
        String reqBody = "{\"title\": \"TestKey\",\"key\": \""+ sshKey + "\"}";
        Response response = given().spec(reqSpec)
                .body(reqBody)
                .when().post("/user/keys");
        String resBody = response.getBody().asPrettyString();
        System.out.println(resBody);
        sshKeyId = response.then().extract().path("id");

        response.then().statusCode(201);
    }

    @Test(priority=2)
    public void getKeys(){
        Response response = given().spec(reqSpec)
                .when().get("/user/keys");

        String resBody = response.getBody().asPrettyString();
        System.out.println(resBody);

        response.then().statusCode(200);
    }

    @Test(priority=3)
    public void deleteKeys(){
        Response response = given().spec(reqSpec)
                .pathParam("keyId" , sshKeyId)
                .when().delete("/user/keys/{keyId}");

        String resBody = response.getBody().asPrettyString();
        System.out.println(resBody);

        response.then().statusCode(204);
    }
}
