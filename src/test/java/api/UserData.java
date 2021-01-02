package tests.api.users;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.skyscreamer.jsonassert.JSONCompare;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONCompareResult;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.shaft.api.RestActions;
import com.shaft.api.RestActions.RequestType;
import com.shaft.validation.Assertions;
import com.shaft.validation.Assertions.AssertionComparisonType;
import com.shaft.validation.Assertions.AssertionType;

import io.restassured.response.Response;

public class UserData {

    @Test
    public void GetallPosts() throws IOException, ParseException, JSONException{
    	 String serviceURI = "https://jsonplaceholder.typicode.com/";
    	  String expectedResponse = new String(
                  Files.readAllBytes(Paths.get(System.getProperty("jsonFolderPath")+"subFolder/AssertionValues.json")));
       Response posts = RestActions.buildNewRequest(serviceURI, "posts", RequestType.GET).performRequest();

       String actualJSONString = RestActions.getResponseJSONValue(posts, "");
       JSONObject expectedJsonObject = (JSONObject) (new JSONParser()).parse(expectedResponse);
       String expectedJSONString = expectedJsonObject.toJSONString();
       JSONCompareResult result = JSONCompare.compareJSON(expectedJSONString, actualJSONString,
               JSONCompareMode.LENIENT);
       boolean finalResult = result.passed();
       Assert.assertTrue(finalResult);
    }
    
    
    @Test
    public void Getonepost() {
	RestActions apiObject = new RestActions("https://jsonplaceholder.typicode.com");
	Response users = apiObject.performRequest(RequestType.GET, 200, "/posts/1");


	RestActions.getResponseJSONValueAsList(users, "$").forEach(user -> {
	    if (RestActions.getResponseJSONValue(user, "id").equals("1")) {
			Assertions.assertEquals("sunt aut facere repellat provident occaecati excepturi optio reprehenderit", RestActions.getResponseJSONValue(user, "title"),
					AssertionComparisonType.EQUALS, AssertionType.POSITIVE);
		Assertions.assertEquals("quia et suscipit\\nsuscipit recusandae consequuntur expedita et cum\\nreprehenderit molestiae ut ut quas totam\\nnostrum rerum est autem sunt rem eveniet architecto", RestActions.getResponseJSONValue(user, "body"),
			AssertionComparisonType.EQUALS, AssertionType.POSITIVE);

	    }

	});
    }
    
    
}
