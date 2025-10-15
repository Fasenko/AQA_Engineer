import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class PatchTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://postman-echo.com";
    }

    @Test
    public void testPatchRequest() {

        String requestBody = "This is expected to be sent back as part of response body.";


        Response response = given()
                .header("Cookie", "__cf_bm=sGI5guEXGDuT3viwE14j9piih3cY4p.TutGNUC5pX2g-1760472116-1.0.1.1-taDPM2VJQyZ5NjAOAwtoao3evQ8QcqWK_iBo5afqsR2rDSjXEU_Sg7PpICRgxM1nhcYrIgN5h0CX2i2ybvVlmwg6nIDhi2F0iSoWQtr5YTc")
                .header("Cache-Control", "no-cache")
                .header("User-Agent", "PostmanRuntime/7.48.0")
                .contentType(ContentType.TEXT)
                .body(requestBody)
                .when()
                .patch("/patch")
                .then()
                .statusCode(200) // проверка кода ответа
                .extract()
                .response();

        System.out.println("Full response body:");
        System.out.println(response.asPrettyString());

        String responseData = response.jsonPath().getString("data");
        assertThat(responseData, equalTo(requestBody));

        assertThat(response.jsonPath().getString("headers.cookie"), containsString("__cf_bm="));
        assertThat(response.jsonPath().getString("url"), equalTo("https://postman-echo.com/patch"));
    }
}