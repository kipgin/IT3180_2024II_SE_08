package app.controllers.admin;


import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import app.controllers.DashboardControllable;
import app.models.*;
import app.services.ApiService;

public class ResidentListController implements DashboardControllable  {

    @FXML
    private TableView<Resident> residentTable;
    @FXML
    private TableColumn<Resident, Number> idColumn;
    @FXML
    private TableColumn<Resident, String> nameColumn;
    @FXML
    private TableColumn<Resident, String> genderColumn;
    @FXML
    private TableColumn<Resident, Number> birthYearColumn;
    @FXML
    private TableColumn<Resident, String> statusColumn;
    @FXML
    private TableColumn<Resident, Number> householdColumn;
    @FXML
    private TableColumn<Resident, Void> actionColumn;

    @FXML
    private TextField searchField;
    @FXML
    private Button searchButton;
    @FXML
    private Button backButton;

    private ObservableList<Resident> data;
    
    private DashBoardController dashboardController;
   	@Override
       public void setDashboardController(DashBoardController controller) {
           this.dashboardController = controller;
       }

    @FXML
    public void initialize() {
        
        idColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getId()));
        nameColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getName()));
        genderColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getGender()));
        birthYearColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getBirthYear()));
        statusColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getAccomStatus()));
        householdColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getHouseholdId()));
        actionColumn.setCellFactory(param -> new TableCell<Resident, Void>() {
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
                    Resident data = getTableView().getItems().get(getIndex());

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Xác nhận xóa");
                    alert.setHeaderText("Bạn có chắc chắn muốn xóa?");
                    alert.setContentText("Người dùng: " + data.getId());

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        ApiService.delResident(data);
                        showStatistics(); 
                    }
                });

                // Hành động sửa
                editButton.setOnAction(event -> {
                    Resident data = getTableView().getItems().get(getIndex());
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
        showStatistics();

        searchButton.setOnAction(e -> handleSearch());
        backButton.setOnAction(e -> handleBack());
    }
    
    public void showStatistics() {
    	   
        new Thread(() -> {
            try {
                List<Resident> residents = ApiService.getAllResidents();
                Platform.runLater(() -> {
                	
                	
          
                    data = FXCollections.observableArrayList(residents);
                    residentTable.setItems(data);
                });
            } catch (Exception e) {
                Platform.runLater(() -> showError("Lỗi khi lấy dữ liệu: " + e.getMessage()));
            }
        }).start();
    }

    private void handleSearch() {
        String keyword = searchField.getText().toLowerCase();
        List<Resident> filtered = data.stream()
            .filter(r -> r.getName().toLowerCase().contains(keyword))
            .collect(Collectors.toList());
        residentTable.setItems(FXCollections.observableArrayList(filtered));
    }

    private void handleBack() {
        try {
        	dashboardController.handleResidentButton();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Lỗi");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    @FXML
    private void openEditDialog(Resident resident) {
    	EditResidentController.showEditDialog(resident, this);
    }


}

