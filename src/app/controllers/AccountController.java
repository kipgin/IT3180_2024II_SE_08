package app.controllers;

import javafx.application.Platform;



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

import app.models.User;
import app.services.ApiService;

public class AccountController implements DashboardControllable{

    @FXML
    private VBox vboxIcon;
    
    @FXML
    private Button registerButton;

    @FXML
    private Button statisticsIcon;

    @FXML
    private VBox statisticsTable;

    @FXML
    private TableView<User> tableView;

    @FXML
    private TableColumn<User, String> usernameColumn;

    @FXML
    private TableColumn<User, String> fullNameColumn;

    @FXML
    private TableColumn<User, String> roleColumn;

    @FXML
    private TableColumn<User, String> activeColumn;

    @FXML
    private TableColumn<User, String> createdAtColumn;

    @FXML
    private TableColumn<User, String> updatedAtColumn;
    @FXML
    private TableColumn<User, Void> actionColumn;

    //private final ApiService userService = new ApiService();

    @FXML
    public void initialize() {
        statisticsTable.setVisible(false);

        statisticsIcon.setOnAction(event -> showStatistics());

        usernameColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getUsername()));
        fullNameColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getFullName()));
        roleColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getRole()));
        activeColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().isActive() ? "Active" : "Inactive"));
        createdAtColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getCreatedAt().toString()));
        updatedAtColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getUpdatedAt().toString()));
        actionColumn.setCellFactory(param -> new TableCell<User, Void>() {
            private final Button deleteButton = new Button();
            {   
            	System.out.println(getClass().getResource("/app/assets/img/find.png").toString());
                //ImageView deleteIcon = new ImageView(new Image(getClass().getResource("/assets/img/find.png").toString()));
                //deleteIcon.setFitHeight(20);
                //deleteIcon.setFitWidth(20);
                //deleteButton.setGraphic(deleteIcon);
                deleteButton.getStyleClass().add("icon-button");

                deleteButton.setOnAction(event -> {
                    User data = getTableView().getItems().get(getIndex());
                    //ApiService.DeleteAccount(data);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : deleteButton);
            }
        });
        
        
        usernameColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.1));   
        fullNameColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.1)); 
        roleColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.1));
        activeColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.1));  
        createdAtColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.25)); 
        updatedAtColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.25));
        actionColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.1));
    
    
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
                List<User> users = ApiService.getAllUsers();
                Platform.runLater(() -> {
                    ObservableList<User> data = FXCollections.observableArrayList(users);
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
    private void openRegisterWindow() {
    	
        dashboardController.loadFXML("/app/views/register.fxml","/app/assets/register.css");
       
    }
}

