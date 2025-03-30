package app.controllers.resident;



import javafx.beans.property.SimpleStringProperty;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;

import app.models.CharityName;
import app.models.CharityRecord;
import app.models.CharitySection;

import app.services.ApiService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.*;

import javafx.stage.Stage;

import java.util.AbstractMap;

import java.util.List;

import java.util.concurrent.CompletableFuture;


public class CharityListController {

	    @FXML
	    private TableView<CharityRecord> tableView;

	    @FXML
	    private TableColumn<CharityRecord, Number> colId;
	    @FXML
	    private TableColumn<CharityRecord, String> colName, colPosition,colAccomStatus;
	

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

	                tableView.getColumns().add(column);
	            }
	            tableView.getItems().setAll(charityRecords);

	          


	        }, Platform::runLater);
	    }

	    
	   
	   
	    
		 @FXML
		 public void handleAddCharityButton() {
		    	try {
		    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/views/admin/add_charity.fxml"));
		    		 Parent root = loader.load();
		    		 Object controller = loader.getController();
		      
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
		 
		 
		 
		 
		 
	    

}
