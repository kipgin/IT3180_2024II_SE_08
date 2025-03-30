package app.controllers.admin;

import app.models.User;
import app.services.ApiService;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ChangePasswordController {
    @FXML private PasswordField txtOldPassword;
    @FXML private PasswordField txtNewPassword;
    @FXML private PasswordField txtConfirmPassword;
    @FXML private TextField txtOldPasswordVisible;
    @FXML private TextField txtNewPasswordVisible;
    @FXML private TextField txtConfirmPasswordVisible;
    @FXML private Button btnToggleOldPassword;
    @FXML private Button btnToggleNewPassword;
    @FXML private Button btnToggleConfirmPassword;
    @FXML private Label lblErrorMessage;

    private boolean isOldPasswordVisible = false;
    private boolean isNewPasswordVisible = false;
    private boolean isConfirmPasswordVisible = false;
    
    private User user;
    public void setUser(User user) {
    	this.user = user;
    	
    }
    
    @FXML
    public void initialize() {
    	
    	txtOldPasswordVisible.setManaged(false);
        txtNewPasswordVisible.setManaged(false);;
        txtConfirmPasswordVisible.setManaged(false);;
    }
    @FXML
    private void toggleOldPassword() {
        isOldPasswordVisible = !isOldPasswordVisible;
        togglePasswordField(txtOldPassword, txtOldPasswordVisible, isOldPasswordVisible);
    }

    @FXML
    private void toggleNewPassword() {
        isNewPasswordVisible = !isNewPasswordVisible;
        togglePasswordField(txtNewPassword, txtNewPasswordVisible, isNewPasswordVisible);
    }

    @FXML
    private void toggleConfirmPassword() {
        isConfirmPasswordVisible = !isConfirmPasswordVisible;
        togglePasswordField(txtConfirmPassword, txtConfirmPasswordVisible, isConfirmPasswordVisible);
    }

    private void togglePasswordField(PasswordField passwordField, TextField textField, boolean isVisible) {
        if (isVisible) {
            textField.setText(passwordField.getText());
            textField.setManaged(true);
            textField.setVisible(true);
            passwordField.setManaged(false);
            passwordField.setVisible(false);
        } else {
            passwordField.setText(textField.getText());
            passwordField.setManaged(true);
            passwordField.setVisible(true);
            textField.setManaged(false);
            textField.setVisible(false);
        }
    }

    @FXML
    private void updatePassword() {
        try {
            // Lấy giá trị mật khẩu từ field nào đang hiển thị
            String oldPass = txtOldPassword.isVisible() ? txtOldPassword.getText() : txtOldPasswordVisible.getText();
            String newPass = txtNewPassword.isVisible() ? txtNewPassword.getText() : txtNewPasswordVisible.getText();
            String confirmPass = txtConfirmPassword.isVisible() ? txtConfirmPassword.getText() : txtConfirmPasswordVisible.getText();

            // Kiểm tra rỗng
            if (oldPass.isEmpty() || newPass.isEmpty() || confirmPass.isEmpty()) {
                throw new IllegalArgumentException("Fields cannot be empty!");
            }

            // Kiểm tra độ dài mật khẩu
//            if (newPass.length() < 6) {
//                throw new IllegalArgumentException("Password must be at least 6 characters!");
//            }

            // Kiểm tra mật khẩu nhập lại có khớp không
            if (!newPass.equals(confirmPass)) {
                throw new IllegalArgumentException("Passwords do not match!");
            }

            // Gọi API để cập nhật mật khẩu
            boolean isUpdated = ApiService.changePassword(user.getUsername(),oldPass, newPass);

            if (isUpdated) {
                lblErrorMessage.setText("Password updated successfully!");
                lblErrorMessage.setStyle("-fx-text-fill: green;");
            } else {
                throw new Exception("Failed to update password. Please try again!");
            }

        } catch (IllegalArgumentException e) {
            lblErrorMessage.setText(e.getMessage());
            lblErrorMessage.setStyle("-fx-text-fill: red;");
        } catch (Exception e) {
            lblErrorMessage.setText("An error occurred: " + e.getMessage());
            lblErrorMessage.setStyle("-fx-text-fill: red;");
        }
    }
}
