package app.controllers.admin;

import app.controllers.DashboardControllable;
import app.models.Household;
import app.services.ApiService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.lang.ModuleLayer.Controller;
import java.util.List;
import java.util.stream.Collectors;

public class ResidentController implements DashboardControllable {

    @FXML
    private FlowPane cardContainer;

    @FXML
    private Button addHouseholdBtn;
    //  lọc
    @FXML private TextField usernameField;
    @FXML private ComboBox<String> blockFilter;
    @FXML private TextField floorField;
    @FXML private TextField roomField;
    @FXML private TextField memberFilter;
    @FXML private Button applyFilterBtn;
    @FXML private Button clearFilterBtn;
    
    // thống kế
    @FXML private Label totalHouseholdsLabel;
    @FXML private Label activeHouseholdsLabel;
    @FXML private Label totalMembersLabel;
    
    private List<Household> households;
    
    
    private DashBoardController dashboardController;
	@Override
    public void setDashboardController(DashBoardController controller) {
        this.dashboardController = controller;
    }

    @FXML
    public void initialize() {
        addHouseholdBtn.setOnAction(event -> showAddForm());
        
        blockFilter.setItems(FXCollections.observableArrayList("A", "B", "C", "D"));

        new Thread(() -> {
            try {
                households = ApiService.getAllHouseholds();
                Platform.runLater(() -> loadCards(households));
            } catch (Exception e) {
                e.printStackTrace(); 
                Platform.runLater(() -> showError("Không thể tải danh sách hộ: " + e.getMessage()));
            }
        }).start();
    }
    
    @FXML
    private void onApplyFilter() {
        String username = usernameField.getText().trim();
        String block = blockFilter.getValue();
        String floorText = floorField.getText().trim();
        String room = roomField.getText().trim();
        String memberText = memberFilter.getText().trim();

        List<Household> result = households.stream()
            .filter(h -> username.isEmpty() || h.getOwnerUsername().toLowerCase().contains(username.toLowerCase()))
            .filter(h -> block == null || h.getBuildingBlock().equalsIgnoreCase(block))
            .filter(h -> floorText.isEmpty() || parseIntSafe(floorText, h.getFloor()))
            .filter(h -> room.isEmpty() || h.getRoomNumber().equalsIgnoreCase(room))
            .filter(h -> memberText.isEmpty() || h.getNumOfMembers() >= parseInt(memberText, 0))
            .collect(Collectors.toList());

        loadCards(result);
    }

    @FXML
    private void onClearFilter() {
        usernameField.clear();
        blockFilter.setValue(null);
        floorField.clear();
        roomField.clear();
        memberFilter.clear();
        loadCards(households);
    }

    private void loadCards(List<Household> households) {
    	
    	totalHouseholdsLabel.setText("Total households: " + String.valueOf(households.size()));
    	int cnt = 0;
        cardContainer.getChildren().clear();
        for (Household h : households) {
            try {
            	cnt += h.getNumOfMembers();
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
        
        activeHouseholdsLabel.setText("Currently living: " + String.valueOf(cnt));
        totalMembersLabel.setText("Total members: " + String.valueOf(cnt));
    }

    public void refresh() {
        new Thread(() -> {
            try {
                households = ApiService.getAllHouseholds();
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
    
    private boolean parseIntSafe(String input, int actual) {
        try {
            return actual == Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return true; 
        }
    }

    private int parseInt(String input, int fallback) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return fallback;
        }
    }
	
	
}
