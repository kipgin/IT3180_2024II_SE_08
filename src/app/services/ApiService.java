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

import app.models.CharityName;
import app.models.CharityRecord;
import app.models.Household;
import app.models.PaymentRecord;
import app.models.Resident;
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
	 
	 public static boolean changePassword(String username, String oldPassword, String newPassword) {
	        try {
	            ObjectMapper objectMapper = new ObjectMapper();
	            ObjectNode jsonNode = objectMapper.createObjectNode();
	            jsonNode.put("username",username);
	            jsonNode.put("oldPassword", oldPassword);
	            jsonNode.put("newPassword", newPassword);
	          
	            
	            String requestBody = objectMapper.writeValueAsString(jsonNode);

	            HttpClient client = HttpClient.newHttpClient();
	            HttpRequest request = HttpRequest.newBuilder()
	                    .uri(URI.create(BASE_URL + "/users/change-password" ) ) 
	                    .header("Content-Type", "application/json")
	                    .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
	                    .build();

	            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
	            
	            
	            return (response.statusCode() == 200 && response.body().equals("true")) ;
	        } catch (Exception e) {
	            e.printStackTrace();
	            return false;
	        }
	    }
	 
	 public static User getUserByUsername(String username) {
	        try {
	        	HttpClient client = HttpClient.newHttpClient();
	            HttpRequest request = HttpRequest.newBuilder()
	                    .uri(URI.create(BASE_URL + "/users/get/" + username))
	                    .GET()
	                    .build();

	            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

	            if (response.statusCode() != 200) {
	                throw new RuntimeException("Failed to fetch residents: " + response.statusCode());
	            }

	            ObjectMapper mapper = new ObjectMapper();
	            return mapper.readValue(response.body(), User.class);
	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
	    }
	 
	 public static Household getHouseholdByUsername(String username) {
	        try {
	        	HttpClient client = HttpClient.newHttpClient();
	            HttpRequest request = HttpRequest.newBuilder()
	                    .uri(URI.create(BASE_URL + "/households/" + username))
	                    .GET()
	                    .build();

	            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

	            if (response.statusCode() != 200) {
	                throw new RuntimeException("Failed to fetch residents: " + response.statusCode());
	            }

	            ObjectMapper mapper = new ObjectMapper();
	            return mapper.readValue(response.body(), Household.class);
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
	        	//System.out.print(fullName + username + password + role + active);
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
	            return (response.statusCode() == 200 && response.body().equals("true")) ;
	                

	        } catch (Exception e) {
	            e.printStackTrace();
	            return false;
	        }
	    }
	    
	    public static boolean delUser(User user) {
	        try {
	            	         
	            HttpClient client = HttpClient.newHttpClient();
	            HttpRequest request = HttpRequest.newBuilder()
	                    .uri(URI.create(BASE_URL + "/users/delete/" + user.getUsername()))
	                    .header("Content-Type", "application/json")
	                    .DELETE()
	                    .build();

	            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
	            System.out.print(response.statusCode());
	            return (response.statusCode() == 200 && response.body().equals("true")) ;
	        } catch (Exception e) {
	            e.printStackTrace();
	            return false;
	        }
	    }
	    
	    public static boolean delHouseholdByUsername(Household household) {
	        try {
	            	         
	            HttpClient client = HttpClient.newHttpClient();
	            HttpRequest request = HttpRequest.newBuilder()
	                    .uri(URI.create(BASE_URL + "/households/delete/" + household.getOwnerUsername()))
	                    .header("Content-Type", "application/json")
	                    .DELETE()
	                    .build();

	            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
	            System.out.print(response.statusCode());
	            return (response.statusCode() == 200 && response.body().equals("true")) ;
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
	            return (response.statusCode() == 200 && response.body().equals("true")) ;
	        } catch (Exception e) {
	            e.printStackTrace();
	            return false;
	        }
	    }
	    
	    public static boolean addResident(Resident resident) {
	        try {
	        	//System.out.print(fullName + username + password + role + active);
	            ObjectMapper objectMapper = new ObjectMapper();
	            ObjectNode jsonNode = objectMapper.createObjectNode();
	            jsonNode.put("name", resident.getName());
	            jsonNode.put("gender", resident.getGender());
	            jsonNode.put("birthYear", resident.getBirthYear());
	            jsonNode.put("accomStatus", resident.getAccomStatus());
	            jsonNode.put("householdId", resident.getHouseholdId());
	            String requestBody = objectMapper.writeValueAsString(jsonNode);

	            HttpClient client = HttpClient.newHttpClient();
	            HttpRequest request = HttpRequest.newBuilder()
	                    .uri(URI.create(BASE_URL + "/residents/register"))
	                    .header("Content-Type", "application/json")
	                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
	                    .build();

	            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
	            return (response.statusCode() == 200 && response.body().equals("true")) ;
	                

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
	    
	    public static List<Resident> getResidentsByHouseholdId(int householdId) {
	        try {
	        	HttpClient client = HttpClient.newHttpClient();
	            HttpRequest request = HttpRequest.newBuilder()
	                    .uri(URI.create(BASE_URL + "/residents/household/" + householdId))
	                    .GET()
	                    .build();

	            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

	            if (response.statusCode() != 200) {
	                throw new RuntimeException("Failed to fetch residents: " + response.statusCode());
	            }

	            ObjectMapper mapper = new ObjectMapper();
	            return mapper.readValue(response.body(), new TypeReference<List<Resident>>() {});
	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
	    }
	    
	    public static List<PaymentRecord> getAllPayments() {
	        try {
	        	HttpClient client = HttpClient.newHttpClient();
	            HttpRequest request = HttpRequest.newBuilder()
	                    .uri(URI.create(BASE_URL + "/feemanage/get-all"))
	                    .GET()
	                    .build();

	            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

	            if (response.statusCode() != 200) {
	                throw new RuntimeException("Failed to fetch payments: " + response.statusCode());
	            }

	            ObjectMapper mapper = new ObjectMapper();
	            return mapper.readValue(response.body(), new TypeReference<List<PaymentRecord>>() {});
	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
	    }
	    
	    public static boolean addPaymentRecord(double area, double serviceFeePerSquare, double totalFee, String ownerUserName, String accom_status) {
	        try {
	            ObjectMapper objectMapper = new ObjectMapper();
	            ObjectNode jsonNode = objectMapper.createObjectNode();
	            jsonNode.put("area", area);
	            jsonNode.put("serviceFeePerSquare", serviceFeePerSquare);
	            jsonNode.put("totalFee", totalFee);
	            jsonNode.put("ownerUserName", ownerUserName);
	            jsonNode.put("accom_status", accom_status);
	            String requestBody = objectMapper.writeValueAsString(jsonNode);

	            HttpClient client = HttpClient.newHttpClient();
	            HttpRequest request = HttpRequest.newBuilder()
	                    .uri(URI.create(BASE_URL + "/feemanage/add"))
	                    .header("Content-Type", "application/json")
	                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
	                    .build();

	            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
	            return (response.statusCode() == 200 && response.body().equals("true")) ;
	        } catch (Exception e) {
	            e.printStackTrace();
	            return false;
	        }
	    }
	    
	    public static boolean updatePayment(PaymentRecord paymenRecord) {
	        try {
	            ObjectMapper objectMapper = new ObjectMapper();
	            ObjectNode jsonNode = objectMapper.createObjectNode();
	            jsonNode.put("area", paymenRecord.getArea());
	            jsonNode.put("serviceFeePerSquare", paymenRecord.getServiceFeePerSquare());
	            jsonNode.put("totalFee", paymenRecord.getTotalFee());
	            jsonNode.put("ownerUserName", paymenRecord.getOwnerUserName());
	            jsonNode.put("accom_status", paymenRecord.getAccom_status());
	            
	            String requestBody = objectMapper.writeValueAsString(jsonNode);

	            HttpClient client = HttpClient.newHttpClient();
	            HttpRequest request = HttpRequest.newBuilder()
	                    .uri(URI.create(BASE_URL + "/feemanage/update-id/" + String.valueOf(paymenRecord.getId()) ) )
	                    .header("Content-Type", "application/json")
	                    .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
	                    .build();

	            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
	            System.out.println("Response Code: " + response.statusCode());
	            System.out.println("Response Body: " + response.body());
	            
	            return (response.statusCode() == 200 && response.body().equals("true")) ;
	        } catch (Exception e) {
	            e.printStackTrace();
	            return false;
	        }
	    }
	    
	    public static List<CharityName> getAllCharityName(){
	    	try {
	        	HttpClient client = HttpClient.newHttpClient();
	            HttpRequest request = HttpRequest.newBuilder()
	                    .uri(URI.create(BASE_URL + "/charityname/get-all"))
	                    .GET()
	                    .build();

	            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

	            if (response.statusCode() != 200) {
	                throw new RuntimeException("Failed to fetch payments: " + response.statusCode());
	            }

	            ObjectMapper mapper = new ObjectMapper();
	            return mapper.readValue(response.body(), new TypeReference<List<CharityName>>() {});
	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
	    }
	    
	    public static List<CharityRecord> getAllCharityRecords() {
	        try {
	        	HttpClient client = HttpClient.newHttpClient();
	            HttpRequest request = HttpRequest.newBuilder()
	                    .uri(URI.create(BASE_URL + "/charity/get-all"))
	                    .GET()
	                    .build();

	            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

	            if (response.statusCode() != 200) {
	                throw new RuntimeException("Failed to fetch charitys: " + response.statusCode());
	            }

	            ObjectMapper mapper = new ObjectMapper();
	            return mapper.readValue(response.body(), new TypeReference<List<CharityRecord>>() {});
	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
	    }
	    
	    public static boolean updateCharitySection(String username, String charityName, int donate) {
	        try {
	            ObjectMapper objectMapper = new ObjectMapper();
	            ObjectNode jsonNode = objectMapper.createObjectNode();
	            jsonNode.put("name", charityName);
	            jsonNode.put("donate", donate);
	            
	            String requestBody = objectMapper.writeValueAsString(jsonNode);

	            HttpClient client = HttpClient.newHttpClient();
	            HttpRequest request = HttpRequest.newBuilder()
	                    .uri(URI.create(BASE_URL + "/charity/add-section-to-ownerusername/" + username ) )
	                    .header("Content-Type", "application/json")
	                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
	                    .build();

	            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
	            System.out.println("Response Code: " + response.statusCode());
	            System.out.println("Response Body: " + response.body());
	            
	            return (response.statusCode() == 200 && response.body().equals("true")) ;
	        } catch (Exception e) {
	            e.printStackTrace();
	            return false;
	        }
	    }
	    
	    public static boolean addCharityRecord(String ownerUserName, String accom_status) {
	        try {
	            ObjectMapper objectMapper = new ObjectMapper();
	            ObjectNode jsonNode = objectMapper.createObjectNode();
	           
	            jsonNode.put("ownerUserName", ownerUserName);
	            jsonNode.put("accomStatus", accom_status);
	            String requestBody = objectMapper.writeValueAsString(jsonNode);

	            HttpClient client = HttpClient.newHttpClient();
	            HttpRequest request = HttpRequest.newBuilder()
	                    .uri(URI.create(BASE_URL + "/charity/add"))
	                    .header("Content-Type", "application/json")
	                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
	                    .build();

	            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
	            return (response.statusCode() == 200 && response.body().equals("true")) ;
	        } catch (Exception e) {
	            e.printStackTrace();
	            return false;
	        }
	    }
	    
	    public static boolean addCharityName(String donateName) {
	        try {
	            ObjectMapper objectMapper = new ObjectMapper();
	            ObjectNode jsonNode = objectMapper.createObjectNode();
	           
	            jsonNode.put("name", donateName);
	      
	            String requestBody = objectMapper.writeValueAsString(jsonNode);

	            HttpClient client = HttpClient.newHttpClient();
	            HttpRequest request = HttpRequest.newBuilder()
	                    .uri(URI.create(BASE_URL + "/charityname/create"))
	                    .header("Content-Type", "application/json")
	                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
	                    .build();

	            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
	            return (response.statusCode() == 200 && response.body().equals("true")) ;
	        } catch (Exception e) {
	            e.printStackTrace();
	            return false;
	        }
	    }
	    
	    public static boolean delCharityName(String donateName) {
	        try {
	            ObjectMapper objectMapper = new ObjectMapper();
	            ObjectNode jsonNode = objectMapper.createObjectNode();
	           
	            jsonNode.put("name", donateName);
	      
	            String requestBody = objectMapper.writeValueAsString(jsonNode);

	            HttpClient client = HttpClient.newHttpClient();
	            HttpRequest request = HttpRequest.newBuilder()
	                    .uri(URI.create(BASE_URL + "/charityname/delete-name/" + donateName))
	                    .header("Content-Type", "application/json")
	                    .DELETE()
	                    .build();

	            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
	            return (response.statusCode() == 200 && response.body().equals("true")) ;
	        } catch (Exception e) {
	            e.printStackTrace();
	            return false;
	        }
	    }
	    
	    public static int getNumberOfResidents() {
	        try {
	        	HttpClient client = HttpClient.newHttpClient();
	            HttpRequest request = HttpRequest.newBuilder()
	                    .uri(URI.create(BASE_URL + "/residents/report/number_of_residents"))
	                    .GET()
	                    .build();

	            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

	            if (response.statusCode() != 200) {
	                throw new RuntimeException("Failed to fetch charitys: " + response.statusCode());
	            }

	            return Integer.parseInt(response.body());
	        } catch (Exception e) {
	            e.printStackTrace();
	            return 0;
	        }
	    }
	    
	    
}
