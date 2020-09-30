package bikes;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;

public class BikeTestMain {
	public static void main(String args[]) {
		RestAssured.defaultParser = Parser.JSON;
		Response response = given().headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON).when()
				.get("http://api.citybik.es/v2/networks").then().contentType(ContentType.JSON).extract().response();
		ResponseBody body = response.getBody();
		List<HashMap<String, String>> company = response.jsonPath().getJsonObject("networks");
		for (HashMap<String, String> hashMap : company) {
			String name = hashMap.get("name");
			if (name.equals("City bikes")) {
				ObjectMapper objectMapper = new ObjectMapper();
				String json = null;
				try {
					json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(hashMap.get("location"));
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		         System.out.println("Loation of City bikes is: "+json);
			}
		}

	}
}

