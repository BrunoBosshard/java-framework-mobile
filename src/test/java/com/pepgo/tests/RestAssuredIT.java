package com.pepgo.tests;

import com.google.gson.JsonObject;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.List;

public class RestAssuredIT {

    // Get a resource
    @Test(groups={"rest","all"})
    public void RESTexampleGET() {
        String strStatusLine, strResponseBody, strTitle, strContentType;
        Long lngResponseTime;
        Integer intCounter, intStatusCode;

        // Set base URI
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        // Request object
        RequestSpecification httpRequest = RestAssured.given();

        // Response object
        Response response = httpRequest.request(Method.GET,"/posts");

        // Output response time
        lngResponseTime = response.getTime();
        System.out.println("Response Time in milliseconds is: " + lngResponseTime);

        // Check status code
        intStatusCode = response.getStatusCode();
        System.out.println("Status Code is: " + intStatusCode);
        Assert.assertEquals(intStatusCode, Integer.valueOf(200));

        // Check status line
        strStatusLine = response.getStatusLine();
        System.out.println("Status Line is: " + strStatusLine);
        Assert.assertEquals(strStatusLine, "HTTP/1.1 200 OK");

        // Output all headers
        Headers allHeaders = response.headers();
        for (Header singleHeader:allHeaders)
        {
            System.out.println("Header " + singleHeader.getName() + " is: " + singleHeader.getValue());
        }

        // Check "Content-Type" (inside the header of the response)
        strContentType = response.header("Content-Type");
        System.out.println("Content Type is: " + strContentType);
        Assert.assertEquals(strContentType, "application/json; charset=utf-8");

        // Output the full response body
        strResponseBody = response.getBody().asString();
        System.out.println("Response Body is: "  + strResponseBody);

        // Use jsonPath to output all "title" values of all objects of the response body
        List<String> titles = response.jsonPath().getList("title");
        for (intCounter = 0;intCounter < titles.size();intCounter++) {
            System.out.println("Title value of object number " +
                    (intCounter + 1) + " is: " + titles.get(intCounter));
        }

        // Output just the "title" value of the 3rd object (index is zero based!)
        strTitle = response.jsonPath().getString("title[2]");
        System.out.println("Title value of object number 3 is: " + strTitle);
    }

    // Create a resource
    @Test(groups={"rest","all"})
    public void RESTexamplePOST() {
        String strResponseBody;

        // Set base URI
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        // Request object
        RequestSpecification httpRequest = RestAssured.given();

        // Set the "Content-Type" of the request header
        httpRequest.header("Content-Type","application/json; charset=UTF-8");

        // Build the payload JSON object for the request body
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("title", "foo");
        jsonObject.addProperty("body", "bar");
        jsonObject.addProperty("userId", 1);

        // Attach the jsonObject as body to the request
        httpRequest.body(jsonObject.toString());

        // Response object
        Response response = httpRequest.request(Method.POST,"/posts");

        // Output response body
        strResponseBody = response.getBody().asString();
        System.out.println("Response Body is: "  + strResponseBody);

        // Check that the response JSON contains a key/value pair with a key named "id" and a value of 101
        Integer intId = response.jsonPath().get("id");
        Assert.assertEquals(intId, Integer.valueOf(101));
    }

    // Update a resource (full update)
    @Test(groups={"rest","all"})
    public void RESTexamplePUT() {
        String strResponseBody, strTitle;

        // Set base URI
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        // Request object
        RequestSpecification httpRequest = RestAssured.given();

        // Set the "Content-Type" of the request header
        httpRequest.header("Content-Type","application/json; charset=UTF-8");

        // Build the payload JSON object for the request body
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", 1);
        jsonObject.addProperty("title", "updated foo");
        jsonObject.addProperty("body", "bar");
        jsonObject.addProperty("userId", 1);

        // Attach the jsonObject as body to the request
        httpRequest.body(jsonObject.toString());

        // Response object
        Response response = httpRequest.request(Method.PUT,"/posts/1");

        // Output response body
        strResponseBody = response.getBody().asString();
        System.out.println("Response Body is: "  + strResponseBody);

        // Check that the response JSON contains a key/value pair "title"/"updated foo"
        strTitle = response.jsonPath().get("title");
        Assert.assertEquals(strTitle, "updated foo");
    }

    // Patch a resource (partial update)
    @Test(groups={"rest","all"})
    public void RESTexamplePATCH() {
        String strResponseBody, strTitle;

        // Set base URI
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        // Request object
        RequestSpecification httpRequest = RestAssured.given();

        // Set the "Content-Type" of the request header
        httpRequest.header("Content-Type","application/json; charset=UTF-8");

        // Build the payload JSON object for the request body
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("title", "patched foo");

        // Attach the jsonObject as body to the request
        httpRequest.body(jsonObject.toString());

        // Response object
        Response response = httpRequest.request(Method.PATCH,"/posts/1");

        // Output response body
        strResponseBody = response.getBody().asString();
        System.out.println("Response Body is: "  + strResponseBody);

        // Check that the response JSON contains a key/value pair "title/updated foo"
        strTitle = response.jsonPath().get("title");
        Assert.assertEquals(strTitle, "patched foo");
    }

    // Delete a resource
    @Test(groups={"rest","all"})
    public void RESTexampleDELETE() {
        Integer intStatusCode;

        // Set base URI
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        // Request object
        RequestSpecification httpRequest = RestAssured.given();

        // Response object
        Response response = httpRequest.request(Method.DELETE,"/posts/1");

        // Check status code
        intStatusCode = response.getStatusCode();
        System.out.println("Status Code is: " + intStatusCode);
        Assert.assertEquals(intStatusCode, Integer.valueOf(200));
    }
}