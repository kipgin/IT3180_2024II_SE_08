package app.controllers.admin;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import app.models.CharityName;
import app.models.CharityRecord;
import app.models.CharitySection;
import app.services.ApiService;

public class OverviewController {

    private static final String API_KEY = "efb1955b93fd74c6f49db019faa25f99"; 
    private static final String WEATHER_API_URL = "https://api.openweathermap.org/data/2.5/weather?q=Hanoi,vn&appid=" + API_KEY + "&units=metric";

    @FXML
    private VBox weatherBox, mapBox, chartBox;

    @FXML
    private Label weatherTitle, weatherInfo, Info;

    @FXML
    private PieChart donationChart;
    
    @FXML
    private Label apartmentCount;

    @FXML
    private Label populationCount;

    @FXML
    private Label paymentRate;

    @FXML
    public void initialize() {
        loadWeatherData();  
        loadDonationChart(); 
        loadData();
    }
    
    private void loadData() {
    	
    	String text = String.format("🏊 Hồ bơi: Hoạt động từ 6h - 21h \n🏋️ Phòng Gym: Mở cửa 24/7 ");

        Platform.runLater(() -> Info.setText(text));
    	
    	new Thread(() -> {
    	    int result = ApiService.getNumberOfResidents();
    	    Platform.runLater(() -> populationCount.setText(String.valueOf(result)));
    	}).start();
    

    }

    private void loadWeatherData() {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(WEATHER_API_URL))
                .GET()
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(this::parseWeatherData)
                .exceptionally(e -> {
                    e.printStackTrace();
                    Platform.runLater(() -> weatherInfo.setText("Lỗi tải dữ liệu!"));
                    return null;
                });
    }

    private void parseWeatherData(String responseBody) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(responseBody);

            double temperature = rootNode.path("main").path("temp").asDouble();
            int humidity = rootNode.path("main").path("humidity").asInt();
            String weatherDesc = rootNode.path("weather").get(0).path("description").asText();

            String weatherText = String.format("🌡 Nhiệt độ: %.1f°C\n💧 Độ ẩm: %d%%\n☁️ Trạng thái: %s",
                    temperature, humidity, weatherDesc);

            Platform.runLater(() -> weatherInfo.setText(weatherText));

        } catch (Exception e) {
            e.printStackTrace();
            Platform.runLater(() -> weatherInfo.setText("Lỗi xử lý dữ liệu!"));
        }
    }

    private void loadDonationChart() {
        new Thread(() -> {
            try {
                List<CharityName> charityNames = ApiService.getAllCharityName();
                List<CharityRecord> records = ApiService.getAllCharityRecords();

                Map<String, Integer> donationMap = new HashMap<>();
                for (CharityName charity : charityNames) {
                    int totalAmount = records.stream()
                        .flatMap(r -> r.getCharitySections().stream())
                        .filter(s -> s.getName().equals(charity.getName()))
                        .mapToInt(CharitySection::getDonate)
                        .sum();
                    donationMap.put(charity.getName(), totalAmount);
                }

                
                Platform.runLater(() -> {
                    donationChart.getData().clear(); 
                    for (Map.Entry<String, Integer> entry : donationMap.entrySet()) {
                        donationChart.getData().add(new PieChart.Data(entry.getKey(), entry.getValue()));
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

}
