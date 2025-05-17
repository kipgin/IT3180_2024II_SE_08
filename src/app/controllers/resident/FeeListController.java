package app.controllers.resident;

import app.controllers.DashboardControllable;
import app.models.FeeName;
import app.models.FeeRecord;
import app.models.User;
import app.services.ApiService;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FeeListController  {

    @FXML
    private TableView<FeeRecord> tableView;
    @FXML private TableColumn<FeeRecord, Number> colHouseholdId;
    @FXML private TableColumn<FeeRecord, String> colOwner;
    @FXML private TableColumn<FeeRecord, String> colStatus;
    @FXML private TableColumn<FeeRecord, Number> colTotalFee;
    @FXML private TableColumn<FeeRecord, String> colPaid;
    @FXML private TableColumn<FeeRecord, String> colAction;

    @FXML
    public void initialize() {
        setupTableColumns();
        
        loadData();
       
    }

    private void setupTableColumns() {
    	colHouseholdId.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getId()));
        colOwner.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getOwnerUserName()));
        colStatus.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAccom_status()));
        colTotalFee.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getTotalFee()));
        colPaid.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().isPaid() ? "COMPLETED" : "INCOMPLETE"));

        colPaid.setCellFactory(column -> new TableCell<FeeRecord, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    Label dot = new Label("●");
                    dot.getStyleClass().add(item.equalsIgnoreCase("Completed") ? "status-dot-green" : "status-dot-red");

                    Label label = new Label(item);
                    label.setGraphic(dot);
                    label.setContentDisplay(ContentDisplay.LEFT);
                    label.getStyleClass().add(item.equalsIgnoreCase("Completed") ? "status-pill-green" : "status-pill-red");

                    setGraphic(label);
                    setAlignment(Pos.CENTER);
                }
            }
        });
        

        colHouseholdId.prefWidthProperty().bind(tableView.widthProperty().multiply(0.19));
        colOwner.prefWidthProperty().bind(tableView.widthProperty().multiply(0.24));
        colStatus.prefWidthProperty().bind(tableView.widthProperty().multiply(0.19));
        colTotalFee.prefWidthProperty().bind(tableView.widthProperty().multiply(0.17));
        colPaid.prefWidthProperty().bind(tableView.widthProperty().multiply(0.21));
        
    }

    public void loadData() {
        new Thread(() -> {
            try {
            	List<FeeRecord> records = ApiService.getAllPayments();
                Platform.runLater(() -> {
                    ObservableList<FeeRecord> data = FXCollections.observableArrayList(records);
                    tableView.setItems(data);        
                });
            } catch (Exception e) {
                Platform.runLater(() -> showError("Lỗi khi lấy dữ liệu: " + e.getMessage()));
            }
        }).start();
    }



    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Lỗi");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

 
}