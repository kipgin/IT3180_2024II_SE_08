package app.controllers.admin;

import com.fasterxml.jackson.core.type.TypeReference;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;

import app.models.CharityName;
import app.models.CharityRecord;
import app.models.CharitySection;
import app.models.Household;
import app.services.ApiService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class CharityManagementController {

	    @FXML
	    private TableView<CharityRecord> tableView;

	    @FXML
	    private TableColumn<CharityRecord, Number> colId;
	    @FXML
	    private TableColumn<CharityRecord, String> colName, colPosition,colAccomStatus;
	    @FXML
	    private BarChart<Number, String> donationChart;  
	    @FXML
	    private NumberAxis xAxis;
	    @FXML
	    private CategoryAxis yAxis;

	    @FXML
	    public void initialize() {
	    	
	    	colId.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getId()));
 	        colName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getOwnerUserName()));
 	        colAccomStatus.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAccomStatus()));
	        
	        loadColumns();
	    }

	    public void loadColumns() {
	        CompletableFuture.supplyAsync(() -> {
	            try {
	                List<CharityName> charityNames = ApiService.getAllCharityName();
	                List<CharityRecord> charityRecords = ApiService.getAllCharityRecords();
	                return new AbstractMap.SimpleEntry<>(charityNames, charityRecords);
	            } catch (Exception e) {
	                e.printStackTrace();
	                return null;
	            }
	        }).thenAcceptAsync(data -> {
	            if (data == null) return;

	            List<CharityName> charityNames = data.getKey();
	            List<CharityRecord> charityRecords = data.getValue();

	            tableView.getColumns().clear();
	            tableView.getColumns().addAll(colId, colName, colAccomStatus);
	            double columnWidth = tableView.getWidth() / (charityNames.size() + 1);

	            for (CharityName charityName : charityNames) {
	                TableColumn<CharityRecord, Integer> column = new TableColumn<>(charityName.getName());
	                column.setMinWidth(columnWidth);
	                column.setCellValueFactory(dataItem -> {
	                    CharityRecord record = dataItem.getValue();
	                    return new SimpleIntegerProperty(
	                        record.getCharitySections().stream()
	                            .filter(section -> section.getName().equals(charityName.getName()))
	                            .mapToInt(CharitySection::getDonate)
	                            .sum()
	                    ).asObject();
	                });

	                column.setCellFactory(col -> new TableCell<>() {
	                    @Override
	                    protected void updateItem(Integer amount, boolean empty) {
	                        super.updateItem(amount, empty);
	                        if (empty || amount == null) {
	                            setText(null);
	                        } else {
	                            setText(amount.toString());
	                            setOnMouseClicked(event -> { 
	                                if (event.getClickCount() == 2) {
	                                    openDonationDialog(getTableRow().getItem(), charityName);
	                                }
	                            });
	                        }
	                    }
	                });

	                tableView.getColumns().add(column);
	            }

	            TableColumn<CharityRecord, Void> addColumn = new TableColumn<>();
	            addColumn.setMinWidth(40);
	            Button addButton = new Button("+");
	            addButton.setOnAction(event -> handleAddCharityNameButton());
	            addColumn.setGraphic(addButton);
	            tableView.getColumns().add(addColumn);

	            TableColumn<CharityRecord, Void> delColumn = new TableColumn<>();
	            delColumn.setMinWidth(40);
	            Button delButton = new Button("-");
	            delButton.setOnAction(event -> handleDelCharityNameButton());
	            delColumn.setGraphic(delButton);
	            tableView.getColumns().add(delColumn);

	            tableView.getItems().setAll(charityRecords);

	            Map<String, Integer> donationMap = new HashMap<>();
	            for (CharityName charity : charityNames) {
	                int totalAmount = charityRecords.stream()
	                    .flatMap(r -> r.getCharitySections().stream())
	                    .filter(s -> s.getName().equals(charity.getName()))
	                    .mapToInt(CharitySection::getDonate)
	                    .sum();
	                donationMap.put(charity.getName(), totalAmount);
	            }

	            yAxis.setCategories(FXCollections.observableArrayList(new ArrayList<>(donationMap.keySet())));
	            yAxis.setTickLabelFont(Font.font("Arial", FontWeight.NORMAL, 7));
	            yAxis.setTickLabelRotation(-5);

	            XYChart.Series<Number, String> series = new XYChart.Series<>();
	            for (Map.Entry<String, Integer> entry : donationMap.entrySet()) {
	                series.getData().add(new XYChart.Data<>(entry.getValue(), entry.getKey()));
	            }

	            donationChart.getData().clear();
	            donationChart.getData().add(series);

	        }, Platform::runLater);
	    }

	    
	   
	    private void openDonationDialog(CharityRecord record, CharityName charityName) {
	    	try {
	    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/views/admin/donation_dialog.fxml"));
	            Parent root = loader.load();
	            DonationDialogController controller = loader.getController();
	            controller.setData(record, charityName);
	            Scene scene = new Scene(root);
	            scene.getStylesheets().add(getClass().getResource("/app/assets/css/admin/donation_dialog.css").toExternalForm());
	            Stage stage = new Stage();
	            stage.setTitle("Ghi Nhận Đóng Góp");
	            stage.setScene(scene);
	            stage.showAndWait();

	            if (controller.isConfirmed()) {
	                loadColumns();
	                System.out.println("Thanh toán thành công!");
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    
		 @FXML
		 public void handleAddCharityButton() {
		    	try {
		    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/views/admin/add_charity.fxml"));
		    		 Parent root = loader.load();
		    		 Object controller = loader.getController();
		 
		             ((AddCharityController) controller).setCharityManagementController(this);
		             
		           
		      
		            Scene scene = new Scene(root);
		            scene.getStylesheets().add(getClass().getResource("/app/assets/css/admin/add_charity.css").toExternalForm());
		            Stage stage = new Stage();
		            stage.setTitle("Thêm Hộ Khẩu Mới");
		            stage.setScene(scene);
		            stage.show();
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		    }
		 
		 public void handleAddCharityNameButton() {
		    	try {
		    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/views/admin/add_charityName.fxml"));
		    		 Parent root = loader.load();
		    		 Object controller = loader.getController();
		 
		             ((AddCharityNameController) controller).setCharityManagementController(this);
		             
		           
		      
		            Scene scene = new Scene(root);
		            scene.getStylesheets().add(getClass().getResource("/app/assets/css/admin/add_charityName.css").toExternalForm());
		            Stage stage = new Stage();
		            stage.setTitle("Thêm Khoản Đóng Góp Mới");
		            stage.setScene(scene);
		            stage.show();
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		    }
		 
		 public void handleDelCharityNameButton() {
		    	try {
		    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/views/admin/del_charityName.fxml"));
		    		 Parent root = loader.load();
		    		 Object controller = loader.getController();
		 
		             ((DelCharityNameController) controller).setCharityManagementController(this);
		             
		           
		      
		            Scene scene = new Scene(root);
		            scene.getStylesheets().add(getClass().getResource("/app/assets/css/admin/del_charityName.css").toExternalForm());
		            Stage stage = new Stage();
		            stage.setTitle("Xóa Khoản Đóng Góp");
		            stage.setScene(scene);
		            stage.show();
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		    }
		 
		 
		 
		 
	    

}
