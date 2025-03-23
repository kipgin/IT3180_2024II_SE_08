package app.controllers;

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

public class DashBoardController {

	@FXML
	private TextField searchField;
	@FXML
	private Button logoutButton;
	
	@FXML
	private Button overviewButton;
	
    @FXML
    private BorderPane mainBorderPane;
    
    @FXML
    private void handleLogout() {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/views/login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            Scene scene = new Scene(root,450,450);
            scene.getStylesheets().add(getClass().getResource("/app/assets/style.css").toExternalForm());
            stage.setScene(scene);
            stage.setX(500);
            stage.setY(150);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void handleOverviewButton() {
        loadFXML("/app/views/overview.fxml","");
    }

    @FXML
    private void handleAccountButton() {
        loadFXML("/app/views/account_management.fxml","/app/assets/account_management.css");
    }

    @FXML
    private void handleResidentButton() {
        loadFXML("/app/views/resident_management.fxml","/app/assets/resident_management.css");
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
