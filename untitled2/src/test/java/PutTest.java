import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;

public class PutTest {

    @Test
    public void testPutRequest() {

        RestAssured.baseURI = "https://postman-echo.com";

        String requestBody = "This is expected to be sent back as part of response body.";

        Response response = given().header("Cookie", "__cf_bm=sGI5guEXGDuT3viwE14j9piih3cY4p.TutGNUC5pX2g-1760472116-1.0.1.1-taDPM2VJQyZ5NjAOAwtoao3evQ8QcqWK_iBo5afqsR2rDSjXEU_Sg7PpICRgxM1nhcYrIgN5h0CX2i2ybvVlmwg6nIDhi2F0iSoWQtr5YTc").header("Cache-Control", "no-cache").header("User-Agent", "PostmanRuntime/7.48.0").header("Accept", "*/*").header("Connection", "keep-alive").header("Accept-Encoding", "gzip, deflate, br").contentType(ContentType.TEXT).body(requestBody).when().put("/put").then().statusCode(200) // проверяем код ответа
                .extract().response();

        String fullResponseBody = response.getBody().asString();
        System.out.println("Полное тело ответа:\n" + fullResponseBody);

        String responseData = response.jsonPath().getString("data");
        org.junit.jupiter.api.Assertions.assertEquals(requestBody, responseData, "Проверка тела ответа");
    }
}