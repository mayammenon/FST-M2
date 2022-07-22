package liveProject;

import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

@ExtendWith(PactConsumerTestExt.class)
public class ConsumerTest {
    Map<String, String> headers = new HashMap<>();
    //API path
    String userResourcePath = "/api/users";
    @Pact(consumer = "UserConsumer", provider = "UserProvider")
    public RequestResponsePact createPact(PactDslWithProvider builder) {
          //Set headers
        headers.put("Content-Type", "application/json");
        //headers.put("Accept","application/json");

        //Set body
        DslPart requestResponseBody = new PactDslJsonBody()
                .numberType("id")
                .stringType("firstName")
                .stringType("lastName")
                .stringType("email");

        //Create Pact
        return builder.given("A request to create a user")
            .uponReceiving("A request to create a user")
                .method("POST")
                .headers(headers)
                .path(userResourcePath)
                .body(requestResponseBody)
            .willRespondWith()
                .status(201)
                .body(requestResponseBody)
            .toPact();

    }

    @Test
    @PactTestFor(providerName = "UserProvider" , port = "8282")
    public void consumerSideTest() {
        final String baseURI = "http://localhost:8282";
//Create req body
        Map<String, Object> reqBody = new HashMap<String,Object>();
        reqBody.put("id", 84);
        reqBody.put("firstName", "Maya");
        reqBody.put("lastName", "Menon");
        reqBody.put("email", "maya@example.com");

        //Generate Response
       Response response = given().headers(headers).when().body(reqBody).post(baseURI + userResourcePath);
       //System.out.println(response.getBody().asPre  ttyString());

       response.then().statusCode(201);



    }

}
