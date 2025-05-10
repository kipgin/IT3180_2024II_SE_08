package app.controllers.admin;

import app.controllers.DashboardControllable;
import app.models.Household;
import app.services.ApiService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import java.io.IOException;
import java.lang.ModuleLayer.Controller;
import java.util.List;

public class ResidentController implements DashboardControllable {

    @FXML
    private FlowPane cardContainer;

    @FXML
    private Button addHouseholdBtn;
    
    private DashBoardController dashboardController;
	@Override
    public void setDashboardController(DashBoardController controller) {
        this.dashboardController = controller;
    }

    @FXML
    public void initialize() {
        addHouseholdBtn.setOnAction(event -> showAddForm());

        new Thread(() -> {
            try {
                List<Household> households = ApiService.getAllHouseholds();
                Platform.runLater(() -> loadCards(households));
            } catch (Exception e) {
                e.printStackTrace(); 
                Platform.runLater(() -> showError("Không thể tải danh sách hộ: " + e.getMessage()));
            }
        }).start();
    }

    private void loadCards(List<Household> households) {
        cardContainer.getChildren().clear();
        for (Household h : households) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/views/admin/household_card.fxml"));
                VBox card = loader.load();
                card.getStylesheets().add(getClass().getResource("/app/assets/css/admin/household_card.css").toExternalForm());
                FlowPane.setMargin(card, new Insets(5));
                HouseholdCardController controller = loader.getController();
                controller.setData(h, this);
                cardContainer.getChildren().add(card);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void refresh() {
        new Thread(() -> {
            try {
                List<Household> households = ApiService.getAllHouseholds();
                Platform.runLater(() -> loadCards(households));
            } catch (Exception e) {
                Platform.runLater(() -> showError("Không thể tải lại danh sách: " + e.getMessage()));
            }
        }).start();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    
    public void showAddForm() {
    	try {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/views/admin/add_household_form.fxml"));
            Parent root = loader.load();
            AddHouseholdFormController controller = loader.getController();
            Scene scene = new Scene(root,350,250);
            scene.getStylesheets().add(getClass().getResource("/app/assets/css/admin/add_household_form.css").toExternalForm());
            Stage stage = new Stage();
            stage.setTitle("Thêm Hộ Khẩu Mới");
            stage.setScene(scene);
            stage.showAndWait();
            if (controller.isConfirmed()) {
                refresh();
          
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void openResidentList() {
    	dashboardController.handleResidentList();
    }
	
	
}
