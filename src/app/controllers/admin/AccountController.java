package app.controllers.admin;

import javafx.application.Platform;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import app.controllers.DashboardControllable;
import app.models.User;
import app.services.ApiService;

public class AccountController implements DashboardControllable{

	@FXML
    private BorderPane rootLayout;
	
    @FXML
    private Button registerButton;



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
    
    @FXML
    private PieChart rolePieChart;
    
    @FXML
    private ComboBox<String> roleFilter;
    @FXML
    private TextField searchField;
    @FXML
    private ComboBox<String> activeFilter;

    @FXML
    private void applyFilter() {
        String selectedRole = roleFilter.getValue();
        String searchText = searchField.getText().toLowerCase();
        String selectedStatus = activeFilter.getValue();

        
        List<User> allUsers = ApiService.getAllUsers();

        // Lọc tài khoản theo các tiêu chí
        List<User> filteredUsers = allUsers.stream()
            .filter(user -> (selectedRole == null || selectedRole.equals("ALL") || user.getRole().equals(selectedRole)))
            .filter(user -> (selectedStatus == null || selectedStatus.equals("ALL") || user.isActive() == (selectedStatus.equals("Active"))))
            .filter(user -> (searchText.isEmpty() || user.getUsername().toLowerCase().contains(searchText)))
            .collect(Collectors.toList());

        // Hiển thị dữ liệu đã lọc lên bảng
        ObservableList<User> filteredData = FXCollections.observableArrayList(filteredUsers);
        tableView.setItems(filteredData);
    }
    

    //private final ApiService userService = new ApiService();

    @FXML
    public void initialize() {
       


        usernameColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getUsername()));
        fullNameColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getFullName()));
        roleColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getRole()));
        activeColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().isActive() ? "Active" : "Inactive"));
        createdAtColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getCreatedAt().toString()));
        updatedAtColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getUpdatedAt().toString()));
        actionColumn.setCellFactory(param -> new TableCell<User, Void>() {
            private final Button deleteButton = new Button();
            private final Button editButton = new Button();
            private final HBox buttonContainer = new HBox(10); 

            {
                // ICON delete
                ImageView deleteIcon = new ImageView(new Image(getClass().getResource("/app/assets/img/delete.png").toString()));
                deleteIcon.setFitHeight(20);
                deleteIcon.setFitWidth(20);
                deleteButton.setGraphic(deleteIcon);
                deleteButton.getStyleClass().add("icon-button-delete");

                // ICON edit
                ImageView editIcon = new ImageView(new Image(getClass().getResource("/app/assets/img/edit.png").toString()));
                editIcon.setFitHeight(20);
                editIcon.setFitWidth(20);
                editButton.setGraphic(editIcon);
                editButton.getStyleClass().add("icon-button-account");

                // Hành động xóa
                deleteButton.setOnAction(event -> {
                    User data = getTableView().getItems().get(getIndex());

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Xác nhận xóa");
                    alert.setHeaderText("Bạn có chắc chắn muốn xóa?");
                    alert.setContentText("Người dùng: " + data.getUsername());

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        ApiService.delUser(data);
                        showStatistics(); 
                    }
                });

                // Hành động sửa
                editButton.setOnAction(event -> {
                    User data = getTableView().getItems().get(getIndex());
                    openEditDialog(data); 
                });

                buttonContainer.setAlignment(Pos.CENTER);
                buttonContainer.getChildren().addAll(editButton, deleteButton);
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : buttonContainer);
            }
        });
        
        activeColumn.setCellFactory(column -> new TableCell<User, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    Label dot = new Label("●");
                    dot.getStyleClass().add(item.equalsIgnoreCase("Active") ? "status-dot-green" : "status-dot-red");

                    Label label = new Label(item);
                    label.setGraphic(dot);
                    label.setContentDisplay(ContentDisplay.LEFT);
                    label.getStyleClass().add("status-pill");

                    setGraphic(label);
                    setAlignment(Pos.CENTER);
                }
            }
        });


        
        
        usernameColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.12));   
        fullNameColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.15)); 
        roleColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.1));
        activeColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.1));  
        createdAtColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.20)); 
        updatedAtColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.20));
        actionColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.1));
        
    
        showStatistics();
    }
    
    private DashBoardController dashboardController;

    public void setDashboardController(DashBoardController controller) {
        this.dashboardController = controller;
    }

    public void showStatistics() {
   
        new Thread(() -> {
            try {
                List<User> users = ApiService.getAllUsers();
                Platform.runLater(() -> {
                	
                	Platform.runLater(() -> {
                		long CntActive = 0;
                    	for(User user:users) {
                    		if(user.isActive()) CntActive++;
                    	}
                        rolePieChart.getData().clear();
                        rolePieChart.getData().add(new PieChart.Data("Active", CntActive));
                        rolePieChart.getData().add(new PieChart.Data("Inactive", users.size() - CntActive));
                    });
              
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
    	
        
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/views/admin/register.fxml"));
            AnchorPane registerWindow = loader.load();
            registerWindow.getStylesheets().add(getClass().getResource("/app/assets/css/admin/register.css").toExternalForm());

            // Thêm cửa sổ vào layout chính
            rootLayout.getChildren().add(registerWindow);
            
            // Gọi phương thức khởi tạo từ controller của register.fxml
            RegisterController registerController = loader.getController();
            registerController.setAccountController(this);
            registerController.initialize(); // Bắt đầu hiệu ứng Slide-in
        } catch (IOException e) {
            e.printStackTrace();
        }
       
    }
    
    @FXML
    private void openEditDialog(User user) {
    	EditAccountController.showEditDialog(user, this);
    }
    
}

