package app.controllers.admin;

import app.controllers.DashboardControllable;
import app.services.ApiService;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;

public class RegisterController implements DashboardControllable{
	@FXML
    private TextField fullNameField;
   
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private ComboBox<String> roleComboBox;


    private DashBoardController dashboardController;

    public void setDashboardController(DashBoardController controller) {
        this.dashboardController = controller;
    }


    @FXML
    private void handleRegister() {
        try {
            if (!passwordField.getText().equals(confirmPasswordField.getText())) {
                showAlert("Error", "Passwords do not match");
                return;
            }
            
            Boolean response = ApiService.register(
                    fullNameField.getText(),
                    usernameField.getText(),
                    passwordField.getText(),
                    roleComboBox.getValue(),
                    "TRUE"
            );
            showAlert("Success", "Registration successful: " + response);
        } catch (Exception e) {
            showAlert("Error", e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
