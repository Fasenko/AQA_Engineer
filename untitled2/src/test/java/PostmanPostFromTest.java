import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class PostmanPostFromTest {

    @Test
    public void testPostFormSuccessfully() {
        String url = "https://postman-echo.com/post";

        Map<String, String> formParams = new HashMap<>();
        formParams.put("foo1", "bar1");
        formParams.put("foo2", "bar2");

        Response response = RestAssured
                .given()
                .header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                .formParams(formParams)
                .log().all()
                .when()
                .post(url)
                .then()
                .log().all()
                .extract().response();

        assertThat(response.getStatusCode(), is(200));


        Map<String, String> returnedForm = response.jsonPath().getMap("form");
        assertThat(returnedForm, is(formParams));

        System.out.println("Тело ответа: " + response.getBody().asString());
    }
}