package app.controllers.admin;

import java.net.URL;

import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import app.models.*;
import app.services.ApiService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

public class NotificationController  {


		@FXML
		private Label totalLabel;

		@FXML
		private TextField searchField;

		@FXML
		private ListView<Notification> notificationListView;
		
		@FXML private HBox onTabAll, onTabSent, onTabReceived;

		private void setSelectedTab(HBox selectedTab) {
		    List<HBox> tabs = List.of(onTabAll, onTabSent, onTabReceived);
		    for (HBox tab : tabs) {
		        tab.getStyleClass().remove("selected");
		    }
		    if (!selectedTab.getStyleClass().contains("selected")) {
		        selectedTab.getStyleClass().add("selected");
		    }
		}

		
		 @FXML
		    public void initialize() {
			 onTabAll();
		 }
		 
		@FXML
		private void onTabAll() {
			setSelectedTab(onTabAll);
		    loadNotifications("all");
		}

		@FXML
		private void onTabSent() {
			setSelectedTab(onTabSent);
		    loadNotifications("sent");
		}

		@FXML
		private void onTabReceived() {
			setSelectedTab(onTabReceived);
		    loadNotifications("received");
		}
		
		
        public void loadNotifications(String format) {
        	notificationListView.setCellFactory(list -> new NotificationCell());
        
        
        new Thread(() -> {
            try {
            	List<Notification> notiList = ApiService.getAllNotifications();
                
                Platform.runLater(() -> {
                	
                	notificationListView.setItems(FXCollections.observableArrayList(notiList));
          
                });
            } catch (Exception e) {
               
            }
        }).start();

        
    }
}

