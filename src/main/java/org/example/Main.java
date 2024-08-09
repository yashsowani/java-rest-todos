package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class Main {

    public static void main(String[] args) {

        try{
            String BASE_URL = "https://jsonplaceholder.typicode.com/posts/1";

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest getRequest = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL))
                    .GET()
                    .build();

            HttpResponse<String> getResponse = client.send(getRequest,HttpResponse.BodyHandlers.ofString());

            System.out.println(getResponse.body());

            ObjectMapper mapper = new ObjectMapper();
            ObjectNode jsonObject = (ObjectNode) mapper.readTree(getResponse.body());
            System.out.println(jsonObject);

            // Print the original JSON
            System.out.println("Original JSON: \n" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject));

            // Step 4: Modify the "title" field
            jsonObject.put("title", "Chin da bag dam dam");

            // Step 5: Convert the modified object back to JSON string
            String updatedJsonString = mapper.writeValueAsString(jsonObject);

            // Step 6: Make a PUT request to update the first entry
            HttpRequest putRequest = HttpRequest.newBuilder()
                    .uri(URI.create("https://jsonplaceholder.typicode.com/posts/1"))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(updatedJsonString))
                    .build();

            HttpResponse<String> putResponse = client.send(putRequest, HttpResponse.BodyHandlers.ofString());

            // Print the response from the PUT request
            System.out.println("PUT Response: \n" + putResponse.body());
            

        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

}



