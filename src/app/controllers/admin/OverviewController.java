package app.controllers.admin;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import app.models.CharityName;
import app.models.CharityRecord;
import app.models.CharitySection;
import app.models.Resident;
import app.models.TopDonor;
import app.services.ApiService;

public class OverviewController {

    private static final String API_KEY = "efb1955b93fd74c6f49db019faa25f99"; 
    private static final String WEATHER_API_URL = "https://api.openweathermap.org/data/2.5/weather?q=Hanoi,vn&appid=" + API_KEY + "&units=metric";

    @FXML
    private VBox weatherBox, mapBox, chartBox;

    @FXML
    private Label weatherTitle, tempInfo,humidityInfo,cloudyInfo, Info;

    
    @FXML
    private Label apartmentCount;

    @FXML
    private Label populationCount;

    @FXML
    private Label paymentRate;
    
    @FXML
    private TableView<TopDonor> donationTable;
    @FXML private TableColumn<TopDonor, Number> rankColumn;
    @FXML
    private TableColumn<TopDonor, String> ownerColumn;
    @FXML
    private TableColumn<TopDonor, Number> donateColumn;

    private void loadTop5Donors() {
    	new Thread(() -> {
            try {
                Platform.runLater(() -> {
                	                	                            	 
                    List<CharityRecord> records = ApiService.getAllCharityRecords();                    
                    // Tính tổng donate mỗi người
                    List<TopDonor> topDonors = records.stream()
                            .map(record -> {
                                int totalDonate = record.getCharitySections().stream()
                                        .mapToInt(CharitySection::getDonate)
                                        .sum();
                                return new TopDonor(0,record.getOwnerUserName(), totalDonate);
                            })
                            .sorted(Comparator.comparingInt(TopDonor::getTotalDonate).reversed())
                            .limit(5)
                            .collect(Collectors.toList());
                    for(int i = 0; i < topDonors.size(); i++ ) {
                    	topDonors.get(i).setRank(i+1);
                    }
                    // Hiển thị lên bảng
                    ObservableList<TopDonor> observableList = FXCollections.observableArrayList(topDonors);
                    donationTable.setItems(observableList);
                });
            } catch (Exception e) {
                
            }
        }).start();
       
    }

    @FXML
    public void initialize() {
        loadWeatherData();  

        donateColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getTotalDonate()) );
        ownerColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getOwnerUserName()) );
        rankColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getRank()) );
       
        
        loadTop5Donors();
        
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
                    Platform.runLater(() -> tempInfo.setText("Lỗi tải dữ liệu!"));
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

            String tempText = String.format(" Nhiệt độ: %.1f°C",temperature);

            Platform.runLater(() -> tempInfo.setText(tempText));
            
            String humidityText = String.format(" Độ ẩm: %d%%", humidity);

            Platform.runLater(() -> humidityInfo.setText(humidityText));
            
            String cloudyText = String.format(" Trạng thái: %s",weatherDesc);

            Platform.runLater(() -> cloudyInfo.setText(cloudyText));

        } catch (Exception e) {
            e.printStackTrace();
            Platform.runLater(() -> tempInfo.setText("Lỗi xử lý dữ liệu!"));
        }
    }

   

}
