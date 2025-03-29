package app.controllers.admin;

import javafx.application.Platform;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

import app.controllers.DashboardControllable;
import app.models.Household;
import app.services.ApiService;

public class ResidentController implements DashboardControllable{

    @FXML
    private VBox vboxIcon;
    
    @FXML
    private Button registerButton;

    @FXML
    private Button statisticsIcon;

    @FXML
    private VBox statisticsTable;

    @FXML
    private TableView<Household> tableView;

    @FXML
    private TableColumn<Household, String> colId;

    @FXML
    private TableColumn<Household, String> colOwner;

    @FXML
    private TableColumn<Household, String> colNumOfMembers;

    @FXML
    private TableColumn<Household, String> colLocation;

    @FXML
    private TableColumn<Household, String> colStatus;


    @FXML
    public void initialize() {
        statisticsTable.setVisible(false);

        statisticsIcon.setOnAction(event -> showStatistics());

        colId.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getId()));
        colOwner.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getOwnerUsername()));
        colNumOfMembers.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNumOfMembers()));
        colLocation.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getCurrentLocation()));
        colStatus.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getStatus()));
       

        
        
        colId.prefWidthProperty().bind(tableView.widthProperty().multiply(0.1));   
        colOwner.prefWidthProperty().bind(tableView.widthProperty().multiply(0.2)); 
        colNumOfMembers.prefWidthProperty().bind(tableView.widthProperty().multiply(0.2));
        colLocation.prefWidthProperty().bind(tableView.widthProperty().multiply(0.4));  
        colStatus.prefWidthProperty().bind(tableView.widthProperty().multiply(0.1)); 
        
        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Double click
                Household selectedHousehold = tableView.getSelectionModel().getSelectedItem();
                if (selectedHousehold != null) {
                    showHouseholdDetailFrame(selectedHousehold);
                }
            }
        });
        
    }
    
    private void showHouseholdDetailFrame(Household household) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/views/admin/householdDetail.fxml"));
            Parent root = loader.load();

            // Truyền dữ liệu sang controller
            HouseholdDetailController controller = loader.getController();
            controller.setHouseholdData(household);
            
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/app/assets/css/admin/householdDetail.css").toExternalForm());

            Stage stage = new Stage();
            stage.setTitle("Thông tin chi tiết hộ gia đình");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private DashBoardController dashboardController;

    public void setDashboardController(DashBoardController controller) {
        this.dashboardController = controller;
    }

    private void showStatistics() {
        vboxIcon.setVisible(false);
        vboxIcon.setManaged(false);
        statisticsTable.setVisible(true);
        statisticsTable.setManaged(true);

        new Thread(() -> {
            try {
                List<Household> users = ApiService.getAllHouseholds();
                Platform.runLater(() -> {
                    ObservableList<Household> data = FXCollections.observableArrayList(users);
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
    
    @FXML
    public void handleAddHouseholdButton() {
    	try {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/views/admin/add_household_form.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root,350,180);
            scene.getStylesheets().add(getClass().getResource("/app/assets/css/admin/add_household_form.css").toExternalForm());
            Stage stage = new Stage();
            stage.setTitle("Thêm Hộ Khẩu Mới");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

}
