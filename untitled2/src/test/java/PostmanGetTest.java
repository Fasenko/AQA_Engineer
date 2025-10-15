import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
public class PostmanGetTest {

    @Test
    public void testGetRequestWithHeaders() {
        given()
                .header("Cookie", "__cf_bm=sGI5guEXGDuT3viwE14j9piih3cY4p.TutGNUC5pX2g-1760472116-1.0.1.1-taDPM2VJQyZ5NjAOAwtoao3evQ8QcqWK_iBo5afqsR2rDSjXEU_Sg7PpICRgxM1nhcYrIgN5h0CX2i2ybvVlmwg6nIDhi2F0iSoWQtr5YTc")
                .header("Cache-Control", "no-cache")
                .header("Postman-Token", "test-token")
                .header("Host", "postman-echo.com")
                .header("User-Agent", "PostmanRuntime/7.48.0")
                .header("Accept", "*/*")
                .log().all()
                .when()
                .get("https://postman-echo.com/get?foo1=bar1&foo2=bar2")
                .then()
                .log().all()
                .statusCode(200)
                .body("args.foo1", equalTo("bar1"))
                .body("args.foo2", equalTo("bar2"));
    }

    @Test
    public void testGetRequestSimple() {
        given()
                .log().all()
                .when()
                .get("https://postman-echo.com/get?foo1=bar1&foo2=bar2")
                .then()
                .log().all()
                .statusCode(200)
                .body("args.foo1", equalTo("bar1"))
                .body("args.foo2", equalTo("bar2"));
    }
}
