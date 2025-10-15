import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RequestMethodsTests {

    private final String BASE_URL = "https://postman-echo.com";

    @Test
    public void testPostRequest() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Cookie", "__cf_bm=sGI5guEXGDuT3viwE14j9piih3cY4p.TutGNUC5pX2g-1760472116-1.0.1.1-taDPM2VJQyZ5NjAOAwtoao3evQ8QcqWK_iBo5afqsR2rDSjXEU_Sg7PpICRgxM1nhcYrIgN5h0CX2i2ybvVlmwg6nIDhi2F0iSoWQtr5YTc");
        headers.put("Cache-Control", "no-cache");
        headers.put("User-Agent", "PostmanRuntime/7.48.0");
        headers.put("Accept", "*/*");
        headers.put("Connection", "keep-alive");
        headers.put("Accept-Encoding", "gzip, deflate, br");

        String requestBody = "{ \"test\": \"value\" }";

        Response response = given()
                .baseUri(BASE_URL)
                .headers(headers)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/post")
                .then()
                .extract()
                .response();

        String fullResponseBody = response.asString();
        System.out.println("Full response body:");
        System.out.println(fullResponseBody);

        assertEquals(200, response.getStatusCode(), "Status code should be 200");

        Map<String, Object> actualBody = response.jsonPath().getMap("json");
        Map<String, Object> expectedBody = new HashMap<>();
        expectedBody.put("test", "value");
        assertEquals(expectedBody, actualBody, "Response body does not match expected body");
    }
}