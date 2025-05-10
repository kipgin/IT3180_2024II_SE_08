package app.controllers.admin;

import javafx.util.Duration;


import app.controllers.DashboardControllable;
import app.services.ApiService;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Alert;

public class RegisterController {
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
    @FXML private Label errorMessage;

    @FXML
    private AnchorPane registerPane;
    
    private AccountController accountController;

    public void setAccountController(AccountController controller) {
        this.accountController = controller;
    }

    public void initialize() {
       
        registerPane.setTranslateX(1500); 
        TranslateTransition slideIn = new TranslateTransition(Duration.seconds(0.5), registerPane);
        slideIn.setToX(850); 
        slideIn.play();
    }
    
    @FXML
    private void handleCloseButton() {
       
        TranslateTransition slideOut = new TranslateTransition(Duration.seconds(0.5), registerPane);
        slideOut.setToX(1500);  
        slideOut.play();

       
        slideOut.setOnFinished(event -> {
           
            // rootLayout.getChildren().remove(registerPane); 
        });
    }
    
    @FXML
    private void handleRegister() {
        try {
            // Kiểm tra nếu các trường nhập liệu rỗng
            if (fullNameField.getText().isEmpty() || usernameField.getText().isEmpty() || 
                passwordField.getText().isEmpty() || confirmPasswordField.getText().isEmpty() ||
                roleComboBox.getValue() == null) {
                errorMessage.setText("All fields must be filled in!");
                errorMessage.setVisible(true);
                return;
            }

            // Kiểm tra mật khẩu và xác nhận mật khẩu có trùng khớp không
            if (!passwordField.getText().equals(confirmPasswordField.getText())) {
                errorMessage.setText("Passwords do not match");
                errorMessage.setVisible(true);
                return;
            }

            // Kiểm tra độ dài mật khẩu (ví dụ: phải có ít nhất 8 ký tự)
            if (passwordField.getText().length() < 8) {
                errorMessage.setText("Password must be at least 8 characters long");
                errorMessage.setVisible(true);
                return;
            }

            // Kiểm tra username (ví dụ: phải không chứa khoảng trắng hoặc ký tự đặc biệt)
            String username = usernameField.getText();
            if (username.contains(" ") || !username.matches("^[a-zA-Z0-9_]+$")) {
                errorMessage.setText("Username is invalid. It should not contain spaces or special characters.");
                errorMessage.setVisible(true);
                return;
            }

            // Gọi API đăng ký
            Boolean response = ApiService.register(
                    fullNameField.getText(),
                    usernameField.getText(),
                    passwordField.getText(),
                    roleComboBox.getValue(),
                    "TRUE"
            );

            // Kiểm tra kết quả đăng ký và thông báo cho người dùng
            if (response) {
            	accountController.showStatistics();
                errorMessage.setText("Registration successful");
                errorMessage.setStyle("-fx-text-fill: green;");
                errorMessage.setVisible(true);
            } else {
                errorMessage.setText("Registration failed. Please try again.");
                errorMessage.setVisible(true);
            }

        } catch (IllegalArgumentException e) {
            errorMessage.setText("Invalid input: " + e.getMessage());
            errorMessage.setVisible(true);
        } catch (Exception e) {
            errorMessage.setText("An unexpected error occurred: " + e.getMessage());
            errorMessage.setVisible(true);
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
