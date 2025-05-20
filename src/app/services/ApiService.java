package app.services;
import com.fasterxml.jackson.databind.JsonNode;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import app.models.CharityName;
import app.models.CharityRecord;
import app.models.FeeName;
import app.models.FeeRecord;
import app.models.Household;
import app.models.Mail;
import app.models.Notification;
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
	        
	       
//	        if (response.body().equals("false")) {
//	            return ";
//	        }
	        return response.body();
	        
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
	    
	    public static boolean addHousehold(String ownerUserName, String buildingBlock, int floor, String roomNumber) {
	        try {
	        	System.out.print(ownerUserName + buildingBlock + floor + roomNumber);
	            ObjectMapper objectMapper = new ObjectMapper();
	            ObjectNode jsonNode = objectMapper.createObjectNode();
	            jsonNode.put("ownerUsername", ownerUserName);
	            jsonNode.put("buildingBlock", buildingBlock);
	            jsonNode.put("floor", floor);
	            jsonNode.put("roomNumber", roomNumber);
	            String requestBody = objectMapper.writeValueAsString(jsonNode);

	            HttpClient client = HttpClient.newHttpClient();
	            HttpRequest request = HttpRequest.newBuilder()
	                    .uri(URI.create(BASE_URL + "/households/register"))
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
	    
	    public static boolean updateResident(Resident resident) {
	        try {
	        	//System.out.print(resident.getId());
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
	                    .uri(URI.create(BASE_URL + "/residents/residents/" + resident.getId() ) )
	                    .header("Content-Type", "application/json")
	                    .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
	                    .build();

	            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
	            
	            
	            return (response.statusCode() == 200 ) ;
	        } catch (Exception e) {
	            e.printStackTrace();
	            return false;
	        }
	    }
	    
	    public static boolean delResident(Resident resident) {
	        try {
	            	         
	            HttpClient client = HttpClient.newHttpClient();
	            HttpRequest request = HttpRequest.newBuilder()
	                    .uri(URI.create(BASE_URL + "/residents/delete/" + resident.getId()))
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
	    
	    public static List<Resident> getAllResidents() {
	        try {
	            HttpClient client = HttpClient.newHttpClient();
	            HttpRequest request = HttpRequest.newBuilder()
	                    .uri(URI.create(BASE_URL + "/residents/get-all"))
	                    .GET()
	                    .build();

	            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

	            if (response.statusCode() != 200) {
	                throw new RuntimeException("Failed to fetch households: " + response.statusCode());
	            }

	            ObjectMapper mapper = new ObjectMapper();
	            return mapper.readValue(response.body(), new TypeReference<List<Resident>>() {});
	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
	    }
	    // payment
	    public static List<FeeRecord> getAllPayments() {
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
	            return mapper.readValue(response.body(), new TypeReference<List<FeeRecord>>() {});
	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
	    }
	    
	    public static List<FeeName> getAllFeename() {
	        try {
	        	HttpClient client = HttpClient.newHttpClient();
	            HttpRequest request = HttpRequest.newBuilder()
	                    .uri(URI.create(BASE_URL + "/feename/get-all"))
	                    .GET()
	                    .build();

	            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

	            if (response.statusCode() != 200) {
	                throw new RuntimeException("Failed to fetch charitys: " + response.statusCode());
	            }

	            ObjectMapper mapper = new ObjectMapper();
	            return mapper.readValue(response.body(), new TypeReference<List<FeeName>>() {});
	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
	    }
	    
	    public static boolean addFeeName(String name, String block, double moneyPerBlock) {
	        try {                           
	            ObjectMapper objectMapper = new ObjectMapper();
	            ObjectNode jsonNode = objectMapper.createObjectNode();
	            jsonNode.put("name", name);
	            jsonNode.put("block", block);
	            jsonNode.put("moneyPerBlock", moneyPerBlock);
	            
	            String requestBody = objectMapper.writeValueAsString(jsonNode);

	            HttpClient client = HttpClient.newHttpClient();
	            HttpRequest request = HttpRequest.newBuilder()
	                    .uri(URI.create(BASE_URL + "/feename/create"))
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
	    
	    public static boolean delFeeName(String name) {
	        try {
	        	
	            ObjectMapper objectMapper = new ObjectMapper();
	            ObjectNode jsonNode = objectMapper.createObjectNode();
	           
	            jsonNode.put("name", name);
	      
	            String requestBody = objectMapper.writeValueAsString(jsonNode);

	            HttpClient client = HttpClient.newHttpClient();
	            HttpRequest request = HttpRequest.newBuilder()
	                    .uri(URI.create(BASE_URL + "/feename/delete-name" ))
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
	    
	    public static boolean createFeeManage(String ownerUserName, String accom_status) {
	        try {
	        	
	            ObjectMapper objectMapper = new ObjectMapper();
	            ObjectNode jsonNode = objectMapper.createObjectNode();
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
	    
	    public static boolean addFeeSection(String username, String name, double blockUsed) {
	        try {                           
	            ObjectMapper objectMapper = new ObjectMapper();
	            ObjectNode jsonNode = objectMapper.createObjectNode();
	            jsonNode.put("name", name);
	            jsonNode.put("blockUsed", blockUsed);
	            
	            String requestBody = objectMapper.writeValueAsString(jsonNode);

	            HttpClient client = HttpClient.newHttpClient();
	            HttpRequest request = HttpRequest.newBuilder()
	                    .uri(URI.create(BASE_URL + "/feemanage/add-section-of-feemanage/" + username))
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
	    
	    public static boolean updateFeeSection(String username, String name, double blockUsed) {
	        try {                           
	            ObjectMapper objectMapper = new ObjectMapper();
	            ObjectNode jsonNode = objectMapper.createObjectNode();
	            jsonNode.put("name", name);
	            jsonNode.put("blockUsed", blockUsed);
	            
	            String requestBody = objectMapper.writeValueAsString(jsonNode);

	            HttpClient client = HttpClient.newHttpClient();
	            HttpRequest request = HttpRequest.newBuilder()
	                    .uri(URI.create(BASE_URL + "/feemanage/update-section-of-ownerusername/" + username))
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
	    
	    public static boolean delFeeSection(String username, String name) {
	        try {                           
	            ObjectMapper objectMapper = new ObjectMapper();
	            ObjectNode jsonNode = objectMapper.createObjectNode();
	            jsonNode.put("name", name);
	            
	            
	            String requestBody = objectMapper.writeValueAsString(jsonNode);

	            HttpClient client = HttpClient.newHttpClient();
	            HttpRequest request = HttpRequest.newBuilder()
	                    .uri(URI.create(BASE_URL + "/feemanage/delete-section-of-feemanage/" + username))
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
	    
	    public static boolean paidFee(String username,double fee) {
	        try {
	            ObjectMapper objectMapper = new ObjectMapper();
	            ObjectNode jsonNode = objectMapper.createObjectNode();
	            
	            
	            String requestBody = objectMapper.writeValueAsString(jsonNode);

	            HttpClient client = HttpClient.newHttpClient();
	            HttpRequest request = HttpRequest.newBuilder()
	                    .uri(URI.create(BASE_URL + "/feemanage/payfeenormal-ownerusername/" + username + "/" + fee ) ) 
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
	    
	    public static double updateFee(String username) {
	        try {
	            ObjectMapper objectMapper = new ObjectMapper();
	            ObjectNode jsonNode = objectMapper.createObjectNode();
	            
	            
	            String requestBody = objectMapper.writeValueAsString(jsonNode);

	            HttpClient client = HttpClient.newHttpClient();
	            HttpRequest request = HttpRequest.newBuilder()
	                    .uri(URI.create(BASE_URL + "/feemanage/update-fee/" + username ) ) 
	                    .header("Content-Type", "application/json")
	                    .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
	                    .build();

	            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
	            if(response.statusCode() == 200) {
	            
	            return ( Double.parseDouble(response.body())) ;
	            } else {
	            	return 0;
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            return 0;
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
	    
	    public static FeeRecord getFeeRecord(String username) {
	        try {
	        	HttpClient client = HttpClient.newHttpClient();
	            HttpRequest request = HttpRequest.newBuilder()
	                    .uri(URI.create(BASE_URL + "/feemanage/update-fee/" + username))
	                    .GET()
	                    .build();

	            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

	            if (response.statusCode() != 200) {
	                throw new RuntimeException("Failed to fetch residents: " + response.statusCode());
	            }

	            ObjectMapper mapper = new ObjectMapper();
	            return mapper.readValue(response.body(), FeeRecord.class);
	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
	    }
	    
	    public static boolean delFeeRecord(String username) {
	        try {
	        	
	            ObjectMapper objectMapper = new ObjectMapper();
	            ObjectNode jsonNode = objectMapper.createObjectNode();
	           
	           
	      
	            String requestBody = objectMapper.writeValueAsString(jsonNode);

	            HttpClient client = HttpClient.newHttpClient();
	            HttpRequest request = HttpRequest.newBuilder()
	                    .uri(URI.create(BASE_URL + "/feemanage/delete-name/" + username))
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
	    
	    public static boolean exportFeeLog(String name , OutputStream outputStream ) {
	        try {
	           
	            ObjectMapper objectMapper = new ObjectMapper();
	            ObjectNode jsonNode = objectMapper.createObjectNode();
	            
	            String requestBody = objectMapper.writeValueAsString(jsonNode);

	           
	            HttpClient client = HttpClient.newHttpClient();
	            HttpRequest request = HttpRequest.newBuilder()
	                    .uri(URI.create(BASE_URL + "/feemanage/export-fee-logs-xlsx/" + name))  
	                    .header("Content-Type", "application/json")
	                    .GET()
	                    .build();

	           
	            HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());

	            if (response.statusCode() == 200) {
	                try (InputStream in = response.body()) {
	                    byte[] buffer = new byte[8192];
	                    int bytesRead;
	                    while ((bytesRead = in.read(buffer)) != -1) {
	                        outputStream.write(buffer, 0, bytesRead);
	                    }
	                    return true;
	                }
	            } else {
	                return false;
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	            return false;
	        }
	    }
	    
	    public static boolean exportBill(String name , OutputStream outputStream ) {
	        try {
	           
	            ObjectMapper objectMapper = new ObjectMapper();
	            ObjectNode jsonNode = objectMapper.createObjectNode();
	            
	            String requestBody = objectMapper.writeValueAsString(jsonNode);

	           
	            HttpClient client = HttpClient.newHttpClient();
	            HttpRequest request = HttpRequest.newBuilder()
	                    .uri(URI.create(BASE_URL + "/feemanage/export-bill/" + name))  
	                    .header("Content-Type", "application/json")
	                    .GET()
	                    .build();

	           
	            HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());

	            if (response.statusCode() == 200) {
	                try (InputStream in = response.body()) {
	                    byte[] buffer = new byte[8192];
	                    int bytesRead;
	                    while ((bytesRead = in.read(buffer)) != -1) {
	                        outputStream.write(buffer, 0, bytesRead);
	                    }
	                    return true;
	                }
	            } else {
	                return false;
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	            return false;
	        }
	    }
	    
	    public static boolean exportAllBill(OutputStream outputStream ) {
	        try {
	           
	            ObjectMapper objectMapper = new ObjectMapper();
	            ObjectNode jsonNode = objectMapper.createObjectNode();
	            
	            String requestBody = objectMapper.writeValueAsString(jsonNode);

	           
	            HttpClient client = HttpClient.newHttpClient();
	            HttpRequest request = HttpRequest.newBuilder()
	                    .uri(URI.create(BASE_URL + "/feesection/export-all-fee-logs-xlsx" ))  
	                    .header("Content-Type", "application/json")
	                    .GET()
	                    .build();

	           
	            HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());

	            if (response.statusCode() == 200) {
	                try (InputStream in = response.body()) {
	                    byte[] buffer = new byte[8192];
	                    int bytesRead;
	                    while ((bytesRead = in.read(buffer)) != -1) {
	                        outputStream.write(buffer, 0, bytesRead);
	                    }
	                    return true;
	                }
	            } else {
	                return false;
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	            return false;
	        }
	    }
	    
	    // Charity
	    
	    public static boolean exportCharityLog(String name , OutputStream outputStream ) {
	        try {
	           
	            ObjectMapper objectMapper = new ObjectMapper();
	            ObjectNode jsonNode = objectMapper.createObjectNode();
	            
	            String requestBody = objectMapper.writeValueAsString(jsonNode);

	           
	            HttpClient client = HttpClient.newHttpClient();
	            HttpRequest request = HttpRequest.newBuilder()
	                    .uri(URI.create(BASE_URL + "/charity/export-charity-logs-xlsx/" + name))  
	                    .header("Content-Type", "application/json")
	                    .GET()
	                    .build();

	           
	            HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());

	            if (response.statusCode() == 200) {
	                try (InputStream in = response.body()) {
	                    byte[] buffer = new byte[8192];
	                    int bytesRead;
	                    while ((bytesRead = in.read(buffer)) != -1) {
	                        outputStream.write(buffer, 0, bytesRead);
	                    }
	                    outputStream.flush();
	                    return true;
	                }
	            } else {
	                return false;
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	            return false;
	        }
	    }
	    
	    public static boolean exportAllCharity(OutputStream outputStream ) {
	        try {
	           
	            ObjectMapper objectMapper = new ObjectMapper();
	            ObjectNode jsonNode = objectMapper.createObjectNode();
	            
	            String requestBody = objectMapper.writeValueAsString(jsonNode);

	           
	            HttpClient client = HttpClient.newHttpClient();
	            HttpRequest request = HttpRequest.newBuilder()
	                    .uri(URI.create(BASE_URL + "/charitysection/export-all-charity-logs-xlsx" ))  
	                    .header("Content-Type", "application/json")
	                    .GET()
	                    .build();

	           
	            HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());

	            if (response.statusCode() == 200) {
	                try (InputStream in = response.body()) {
	                    byte[] buffer = new byte[8192];
	                    int bytesRead;
	                    while ((bytesRead = in.read(buffer)) != -1) {
	                        outputStream.write(buffer, 0, bytesRead);
	                    }
	                    return true;
	                }
	            } else {
	                return false;
	            }

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
	    
	    public static boolean addCharitySection(String username, String charityName, int donate) {
	        try {
	            ObjectMapper objectMapper = new ObjectMapper();
	            ObjectNode jsonNode = objectMapper.createObjectNode();
	            
	            jsonNode.put("name", charityName);	
	            jsonNode.put("donate", donate);
	            
	            String requestBody = objectMapper.writeValueAsString(jsonNode);

	            HttpClient client = HttpClient.newHttpClient();
	            HttpRequest request = HttpRequest.newBuilder()
	                    .uri(URI.create(BASE_URL + "/charity/add-section-to-ownerusername/" + username) )
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
	    
	    public static boolean updateCharitySection(String username, String charityName, int donate) {
	        try {
	            ObjectMapper objectMapper = new ObjectMapper();
	            ObjectNode jsonNode = objectMapper.createObjectNode();
	             
	            jsonNode.put("name", charityName);	
	           
	         
	            
	            String requestBody = objectMapper.writeValueAsString(jsonNode);

	            HttpClient client = HttpClient.newHttpClient();
	            HttpRequest request = HttpRequest.newBuilder()
	                    .uri(URI.create(BASE_URL + "/charity/update-section-of-charity/" + username + "/" + donate) )
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
	                    .uri(URI.create(BASE_URL + "/charityname/delete-name" ))
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
	    
	    // Notification
	    
	    public static  List<Notification> getAllNotifications()  {
			 try {
			    HttpClient client = HttpClient.newHttpClient();
		        HttpRequest request = HttpRequest.newBuilder()
		                .uri(URI.create(BASE_URL + "/api/notifications/all"))
		                .GET()
		                .build();

		        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

		        if (response.statusCode() != 200) {
		            throw new RuntimeException("Failed to fetch users: " + response.statusCode());
		        }

		        ObjectMapper mapper = new ObjectMapper();
		        return mapper.readValue(response.body(), new TypeReference<List<Notification>>() {});
			    } catch (Exception e) {
		            e.printStackTrace();
		            return null;
		        }
		    }
	    
	    public static  List<Notification> getUserNotifications(String username)  {
			 try {
			    HttpClient client = HttpClient.newHttpClient();
		        HttpRequest request = HttpRequest.newBuilder()
		                .uri(URI.create(BASE_URL + "/api/notifications/user/" + username))
		                .GET()
		                .build();

		        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

		        if (response.statusCode() != 200) {
		            throw new RuntimeException("Failed to fetch users: " + response.statusCode());
		        }

		        ObjectMapper mapper = new ObjectMapper();
		        return mapper.readValue(response.body(), new TypeReference<List<Notification>>() {});
			    } catch (Exception e) {
		            e.printStackTrace();
		            return null;
		        }
		    }
	    
	    public static  List<Notification> getGeneralNotifications()  {
			 try {
			    HttpClient client = HttpClient.newHttpClient();
		        HttpRequest request = HttpRequest.newBuilder()
		                .uri(URI.create(BASE_URL + "/api/notifications/general"))
		                .GET()
		                .build();

		        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

		        if (response.statusCode() != 200) {
		            throw new RuntimeException("Failed to fetch users: " + response.statusCode());
		        }

		        ObjectMapper mapper = new ObjectMapper();
		        return mapper.readValue(response.body(), new TypeReference<List<Notification>>() {});
			    } catch (Exception e) {
		            e.printStackTrace();
		            return null;
		        }
		    }
	    
	    public static boolean sendNotification(String senderId, String title, String content, String type, String receiverId) {
	        try {
	            System.out.print(senderId + " " + title + " " + content + " " + type + " " + receiverId);
	            ObjectMapper objectMapper = new ObjectMapper();
	            ObjectNode jsonNode = objectMapper.createObjectNode();
	            if(type.equals("GENERAL")) {
	            	jsonNode.put("senderId", senderId);
	            	jsonNode.put("title", title);
	            	jsonNode.put("content", content);
	            	jsonNode.put("type", type);
	            } else {
	            	jsonNode.put("senderId", senderId);
	            	jsonNode.put("title", title);
	            	jsonNode.put("content", content);
	            	jsonNode.put("type", type);
	            	jsonNode.put("receiverId", receiverId);
	            }
	            
	      
	            String requestBody = objectMapper.writeValueAsString(jsonNode);

	            HttpClient client = HttpClient.newHttpClient();
	            HttpRequest request = HttpRequest.newBuilder()
	                    .uri(URI.create(BASE_URL + "/api/notifications"))
	                    .header("Content-Type", "application/json")
	                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
	                    .build();

	            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
	            return (response.statusCode() == 200) ;
	        } catch (Exception e) {
	            e.printStackTrace();
	            return false;
	        }
	    }
	    
	    public static boolean delNotification(Notification record) {
	        try {
	        	
	            ObjectMapper objectMapper = new ObjectMapper();
	            ObjectNode jsonNode = objectMapper.createObjectNode();
	           
	           
	      
	            String requestBody = objectMapper.writeValueAsString(jsonNode);

	            HttpClient client = HttpClient.newHttpClient();
	            HttpRequest request = HttpRequest.newBuilder()
	                    .uri(URI.create(BASE_URL + "/api/notifications/" + record.getId() + "/" + record.getSenderId()))
	                    .header("Content-Type", "application/json")
	                    .DELETE()
	                    .build();

	            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
	            return (response.statusCode() == 200 ) ;
	        } catch (Exception e) {
	            e.printStackTrace();
	            return false;
	        }
	    }
	    
	    
	    
	    // Quên mật khẩu
	    
	    public static String getUserByEmail (String mail){
	    	try {
	        	HttpClient client = HttpClient.newHttpClient();
	            HttpRequest request = HttpRequest.newBuilder()
	                    .uri(URI.create(BASE_URL + "/users-gmail/email/" + mail))
	                    .GET()
	                    .build();

	            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

	            if (response.statusCode() != 200) {
	                throw new RuntimeException("Failed to fetch payments: " + response.statusCode());
	            }

	            ObjectMapper mapper = new ObjectMapper();
	         
	            return (mapper.readValue(response.body(), Mail.class)).getUsername();
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
	    }
	    
	    public static boolean sendToken(String username) {
	        try {
	    
	            ObjectMapper objectMapper = new ObjectMapper();
	            ObjectNode jsonNode = objectMapper.createObjectNode();
	            
	            String requestBody = objectMapper.writeValueAsString(jsonNode);

	            HttpClient client = HttpClient.newHttpClient();
	            HttpRequest request = HttpRequest.newBuilder()
	                    .uri(URI.create(BASE_URL + "/users/forgot/" + username))
	                    .header("Content-Type", "application/json")
	                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
	                    .build();

	            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
	           
	            return (response.statusCode() == 200) ;
	        } catch (Exception e) {
	            e.printStackTrace();
	            return false;
	        }
	    }
	    
	    public static boolean verifyToken (String username, String token){
	    	try {
	        	HttpClient client = HttpClient.newHttpClient();
	            HttpRequest request = HttpRequest.newBuilder()
	                    .uri(URI.create(BASE_URL + "/users/verify-token/" + username + "/" + token))
	                    .GET()
	                    .build();

	            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

	            if (response.statusCode() != 200) {
	                throw new RuntimeException("Failed to fetch payments: " + response.statusCode());
	            }
	         
	            return (response.statusCode() == 200 && response.body().equals("Token is valid.")) ;
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	            return false;
	        }
	    }
	    
	    // Thông tin 
	    
	    public static String getEmailByUsername (String username){
	    	try {
	        	HttpClient client = HttpClient.newHttpClient();
	            HttpRequest request = HttpRequest.newBuilder()
	                    .uri(URI.create(BASE_URL + "/users-gmail/" + username))
	                    .GET()
	                    .build();

	            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

	            if (response.statusCode() != 200) {
	                throw new RuntimeException("Failed to fetch payments: " + response.statusCode());
	            }

	            ObjectMapper mapper = new ObjectMapper();
	         
	            return (mapper.readValue(response.body(), Mail.class)).getEmail();
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	            return "";
	        }
	    }
	    
	    public static boolean addEmail(String username, String email) {
	        try {
	    
	            ObjectMapper objectMapper = new ObjectMapper();
	            ObjectNode jsonNode = objectMapper.createObjectNode();
	            
	            jsonNode.put("username", username);
	            jsonNode.put("email", email);
	            
	            String requestBody = objectMapper.writeValueAsString(jsonNode);

	            HttpClient client = HttpClient.newHttpClient();
	            HttpRequest request = HttpRequest.newBuilder()
	                    .uri(URI.create(BASE_URL + "/users-gmail/add"))
	                    .header("Content-Type", "application/json")
	                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
	                    .build();

	            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
	           
	            return (response.statusCode() == 200) ;
	        } catch (Exception e) {
	            e.printStackTrace();
	            return false;
	        }
	    }
	    
	    
}
