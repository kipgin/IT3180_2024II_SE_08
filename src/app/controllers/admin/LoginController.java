package app.controllers.admin;

import javafx.event.ActionEvent;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import app.models.User;
import app.services.ApiService;


import java.io.IOException;
import java.lang.ModuleLayer.Controller;

public class LoginController {
    @FXML
    private TextField emailField;
    
    @FXML
    private PasswordField passwordField;
    
    @FXML
    private Button loginButton;
    @FXML
    private Button accountButton;
    
    @FXML
    private void handleLogin(ActionEvent e) throws Exception {
        String username = emailField.getText();
        String password = passwordField.getText();
        
      
        if (ApiService.login(username, password)=="true") {
        	User user = ApiService.getUserByUsername(username);
        	if(user.getRole().equals("ADMIN"))
               openAdminMainScreen(user); 
        	else openResidentMainScreen(user);
        } else {
            System.out.println("Login failed");
        }
    }

    private void openAdminMainScreen(User user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/views/admin/dashboard.fxml"));
            Parent root = loader.load();
            Object controller = loader.getController();
            ((DashBoardController) controller).setUser(user);
            Stage stage = (Stage) loginButton.getScene().getWindow();
            Scene scene = new Scene(root,1350,750);
            scene.getStylesheets().add(getClass().getResource("/app/assets/css/admin/dashboard.css").toExternalForm());
            stage.setScene(scene);
            stage.setX(50);
            stage.setY(30);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void openResidentMainScreen(User user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/views/resident/dashboard.fxml"));
            Parent root = loader.load();
            Object controller = loader.getController();
            ((app.controllers.resident.DashBoardController) controller).setUser(user);
            Stage stage = (Stage) loginButton.getScene().getWindow();
            Scene scene = new Scene(root,1350,750);
            scene.getStylesheets().add(getClass().getResource("/app/assets/css/resident/dashboard.css").toExternalForm());
            stage.setScene(scene);
            stage.setX(50);
            stage.setY(30);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
