package app.services;
import com.fasterxml.jackson.databind.JsonNode;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.core.type.TypeReference;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import app.models.Household;
import app.models.User;

public class ApiService {
	 private static final String BASE_URL = "http://spring-boot-demo-env.eba-p4mintvf.ap-southeast-2.elasticbeanstalk.com";
	   
	 
	 public static String login(String username, String password)  {
		 try {
	        // Khởi tạo ObjectMapper của Jackson
	        ObjectMapper objectMapper = new ObjectMapper();
	        
	        // Tạo JSON request body
	        ObjectNode jsonNode = objectMapper.createObjectNode();
	        jsonNode.put("username", username);
	        jsonNode.put("password", password);
	        String requestBody = objectMapper.writeValueAsString(jsonNode);
	        
	        // Tạo HttpClient
	        HttpClient client = HttpClient.newHttpClient();

	        // Tạo HttpRequest với phương thức POST
	        HttpRequest request = HttpRequest.newBuilder()
	                .uri(URI.create(BASE_URL + "/users/login"))
	                .header("Content-Type", "application/json")
	                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
	                .build();

	        // Gửi yêu cầu và nhận phản hồi
	        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

	        // Kiểm tra nếu server trả về false thì đăng nhập thất bại
	        if (response.body().equals("false")) {
	            return null;
	        }
	        return "true";
	        // Phân tích JSON để lấy token
//	        JsonNode responseJson = objectMapper.readTree(response.body());
//	        return responseJson.has("token") ? responseJson.get("token").asText() : null;
	        
		    } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
	    }
	    
	    
	 public static  List<User> getAllUsers()  {
		 try {
		    HttpClient client = HttpClient.newHttpClient();
	        HttpRequest request = HttpRequest.newBuilder()
	                .uri(URI.create(BASE_URL + "/users/all"))
	                .GET()
	                .build();

	        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

	        if (response.statusCode() != 200) {
	            throw new RuntimeException("Failed to fetch users: " + response.statusCode());
	        }

	        ObjectMapper mapper = new ObjectMapper();
	        return mapper.readValue(response.body(), new TypeReference<List<User>>() {});
		    } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
	    }
	 
	    public static boolean register(String fullName, String username, String password, String role, String active) {
	        try {
	        	System.out.print(fullName + username + password + role + active);
	            ObjectMapper objectMapper = new ObjectMapper();
	            ObjectNode jsonNode = objectMapper.createObjectNode();
	            jsonNode.put("fullName", fullName);
	            jsonNode.put("username", username);
	            jsonNode.put("password", password);
	            jsonNode.put("role", role);
	            jsonNode.put("true", active);
	            String requestBody = objectMapper.writeValueAsString(jsonNode);

	            HttpClient client = HttpClient.newHttpClient();
	            HttpRequest request = HttpRequest.newBuilder()
	                    .uri(URI.create(BASE_URL + "/users/register"))
	                    .header("Content-Type", "application/json")
	                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
	                    .build();

	            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
	            System.out.print(response.statusCode() );
	            return response.statusCode() == 200;
	        } catch (Exception e) {
	            e.printStackTrace();
	            return false;
	        }
	    }
	    
	    public static boolean addHousehold(String ownerUserName, int numOfMembers) {
	        try {
	            System.out.print(ownerUserName + " " + numOfMembers + " " );
	            ObjectMapper objectMapper = new ObjectMapper();
	            ObjectNode jsonNode = objectMapper.createObjectNode();
	            jsonNode.put("ownerUserName", ownerUserName);
	            jsonNode.put("numOfMembers", numOfMembers);
	            String requestBody = objectMapper.writeValueAsString(jsonNode);

	            HttpClient client = HttpClient.newHttpClient();
	            HttpRequest request = HttpRequest.newBuilder()
	                    .uri(URI.create(BASE_URL + "/households/register/" + ownerUserName))
	                    .header("Content-Type", "application/json")
	                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
	                    .build();

	            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
	            System.out.print(response.statusCode());
	            return response.statusCode() == 200 || response.statusCode() == 201;
	        } catch (Exception e) {
	            e.printStackTrace();
	            return false;
	        }
	    }
	    
	    public static List<Household> getAllHouseholds() {
	        try {
	            HttpClient client = HttpClient.newHttpClient();
	            HttpRequest request = HttpRequest.newBuilder()
	                    .uri(URI.create(BASE_URL + "/households/get-all"))
	                    .GET()
	                    .build();

	            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

	            if (response.statusCode() != 200) {
	                throw new RuntimeException("Failed to fetch households: " + response.statusCode());
	            }

	            ObjectMapper mapper = new ObjectMapper();
	            return mapper.readValue(response.body(), new TypeReference<List<Household>>() {});
	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
	    }
}
