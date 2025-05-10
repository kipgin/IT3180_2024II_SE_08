package app.controllers.admin;

import app.models.*;
import app.services.ApiService;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class AccountInfoController {
    @FXML private Label usernameLabel;
    @FXML private Label _usernameLabel;
    @FXML private Label fullNameLabel;
    @FXML private Label roleLabel;
    @FXML private Label createdAtLabel;
    @FXML private Label updatedAtLabel;
    @FXML private Label activeLabel;
    @FXML private Label emailLabel;
    @FXML private TextField emailField;
    
    private String mail;

    private User currentUser; 
    
    public void setUser(User user) {
        this.currentUser = user;
        usernameLabel.setText(currentUser.getUsername());
        _usernameLabel.setText(currentUser.getUsername());
        fullNameLabel.setText(currentUser.getFullName());
        roleLabel.setText(currentUser.getRole());
        createdAtLabel.setText(currentUser.getCreatedAt());
        updatedAtLabel.setText(currentUser.getUpdatedAt());
        activeLabel.setText(currentUser.isActive() ? "Active" : "Inactive");
        
        mail = ApiService.getEmailByUsername(user.getUsername());
        
        emailLabel.setText(mail);
        emailField.setText(mail);
        if(mail.isEmpty()) {
        	
        	 emailLabel.setText("Chưa liên kết địa chỉ Email");
        }
        
    }

    public void initialize() {
       
        
    }

    @FXML
    private void onUpdateEmail() {
        String newEmail = emailField.getText();
        if (!newEmail.matches("^\\S+@\\S+\\.\\S+$")) {
            // Email sai format
            showAlert("Email không hợp lệ!", Alert.AlertType.ERROR);
            return;
        }

        if(ApiService.addEmail(currentUser.getUsername(), newEmail)) {
        	emailLabel.setText(newEmail);
        	showAlert("Cập nhật địa chỉ Email thành công", Alert.AlertType.INFORMATION);
        } else {
        	showAlert("Email này đã tồn tại!", Alert.AlertType.ERROR);
        }
        

    }

    private void showAlert(String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle("Thông báo");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
