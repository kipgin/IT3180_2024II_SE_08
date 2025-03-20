package app.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import app.services.ApiService;
import app.services.AuthService;

import java.io.IOException;

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
            openMainScreen();
        } else {
            System.out.println("Login failed");
        }
    }

    private void openMainScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/views/dashboard.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) loginButton.getScene().getWindow();
            Scene scene = new Scene(root,1350,750);
            scene.getStylesheets().add(getClass().getResource("/app/assets/dashboard.css").toExternalForm());
            stage.setScene(scene);
            stage.setX(50);
            stage.setY(30);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
