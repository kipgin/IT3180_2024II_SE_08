package app.controllers.resident;

import javafx.application.Platform;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

import app.controllers.DashboardControllable;
import app.models.PaymentRecord;
import app.services.ApiService;

public class FeeListController  {

  

    @FXML
    private TableView<PaymentRecord> tableView;

    @FXML
    private TableColumn<PaymentRecord, Number> colId;

    @FXML
    private TableColumn<PaymentRecord, String> colOwner;

    @FXML
    private TableColumn<PaymentRecord, Number> colArea;

    @FXML
    private TableColumn<PaymentRecord, Number> colServiceFeePerSquare;

    @FXML
    private TableColumn<PaymentRecord, Number> colTotalFee;

    @FXML
    private TableColumn<PaymentRecord, String> colStatus;

    

    @FXML
    public void initialize() {
       
    	
        colId.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()));
        colOwner.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOwnerUserName()));
        colArea.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getArea()));
        colServiceFeePerSquare.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getServiceFeePerSquare()));
        colTotalFee.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getTotalFee()));
        colStatus.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAccom_status()));
       
        
        colId.prefWidthProperty().bind(tableView.widthProperty().multiply(0.1));
        colOwner.prefWidthProperty().bind(tableView.widthProperty().multiply(0.2));
        colArea.prefWidthProperty().bind(tableView.widthProperty().multiply(0.1));
        colServiceFeePerSquare.prefWidthProperty().bind(tableView.widthProperty().multiply(0.15));
        colTotalFee.prefWidthProperty().bind(tableView.widthProperty().multiply(0.1));
        colStatus.prefWidthProperty().bind(tableView.widthProperty().multiply(0.15));
       

   
        loadData();
       
    }

    public void loadData() {
        
        new Thread(() -> {
            try {
                List<PaymentRecord> payments = ApiService.getAllPayments();
                Platform.runLater(() -> {
                    ObservableList<PaymentRecord> data = FXCollections.observableArrayList(payments);
                    tableView.setItems(data);
                 
                });
            } catch (Exception e) {
                Platform.runLater(() -> showError("Lỗi khi lấy dữ liệu: " + e.getMessage()));
            }
        }).start();
    }
    

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Lỗi");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}