package app.controllers.admin;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

import app.controllers.DashboardControllable;
import app.models.CharityName;
import app.models.User;
import app.services.ApiService;

public class DashBoardController {

	@FXML
	private TextField searchField;
	@FXML
	private Button logoutButton;
	
	@FXML
	private Button overviewButton;
	
    @FXML
    private BorderPane mainBorderPane;
    
    private User user;
    
    public void setUser(User user) {
    	this.user = user;
    }
    
    @FXML
    private void handleLogout() {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/views/admin/login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            Scene scene = new Scene(root,450,450);
            scene.getStylesheets().add(getClass().getResource("/app/assets/css/admin/style.css").toExternalForm());
            stage.setScene(scene);
            stage.setX(500);
            stage.setY(150);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    public void initialize() {
    	
    	loadFXML("/app/views/admin/overview.fxml","/app/assets/css/admin/overview.css");
    }
    
    @FXML
    private void handleOverviewButton() {
        loadFXML("/app/views/admin/overview.fxml","/app/assets/css/admin/overview.css");
    }

    @FXML
    private void handleAccountButton() {
        loadFXML("/app/views/admin/account_management.fxml","/app/assets/css/admin/account_management.css");
    }

    @FXML
    private void handleResidentButton() {
        loadFXML("/app/views/admin/resident_management.fxml","/app/assets/css/admin/resident_management.css");
    }
    @FXML
    private void handleFeeButton() {
    	 loadFXML("/app/views/admin/fee_management.fxml","/app/assets/css/admin/fee_management.css");
    }
    
    @FXML
    private void handleCharityButton() {
    	 loadFXML("/app/views/admin/charity_management.fxml","/app/assets/css/admin/charity_management.css");
    }

    // Phương thức để load FXML và đặt vào center
    public void loadFXML(String fxmlFile,String cssFile) {
        try {
        	
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent content = loader.load();
            
            Object controller = loader.getController();
            if (controller instanceof DashboardControllable) {
                ((DashboardControllable) controller).setDashboardController(this);
            }
            
            content.getStylesheets().add(getClass().getResource(cssFile).toExternalForm());

            mainBorderPane.setCenter(content); 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
