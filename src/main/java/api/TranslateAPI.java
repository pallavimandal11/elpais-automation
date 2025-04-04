package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.*;

public class TranslateAPI {
    private static final String ENDPOINT = "https://translation.googleapis.com/language/translate/v2";
    private static final String API_KEY = "YOUR_GOOGLE_API_KEY";

    public static String translate(String text) {
        Map<String, Object> params = new HashMap<>();
        params.put("q", text);
        params.put("target", "en");
        params.put("source", "es");
        params.put("key", API_KEY);

        Response response = RestAssured
                .given()
                .contentType("application/json")
                .body(params)
                .post(ENDPOINT);

        return response.jsonPath().getString("data.translations[0].translatedText");
    }
}
