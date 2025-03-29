package app.controllers.admin;

import app.services.ApiService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.util.HashMap;
import java.util.Map;

public class AddCharityNameController {

    @FXML
    private TextField donateNameField;

    @FXML
    private Button cancelButton;

    @FXML
    private Button confirmButton;


    private CharityManagementController controller;
    
    public void setCharityManagementController(CharityManagementController controller) {
       this.controller = controller;	
    }

    @FXML
    private void handleCancel() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleConfirm() {
    	 try {
             if (donateNameField.getText().isEmpty()) {
                 showAlert(Alert.AlertType.ERROR, "Lỗi", "Vui lòng điền đầy đủ thông tin.");
                 return;
             }

             String donateName = donateNameField.getText();

             boolean success = ApiService.addCharityName(donateName);

             if (success) {
                 showAlert(Alert.AlertType.INFORMATION, "Thành công", "Thêm khoản đóng góp mới thành công.");
                 controller.loadColumns();
                 handleCancel();
             } else {
                 showAlert(Alert.AlertType.ERROR, "Lỗi", "Có lỗi xảy ra khi thêm khoản đóng góp.");
             }
         }   catch (Exception e) {
             showAlert(Alert.AlertType.ERROR, "Lỗi", "Có lỗi không xác định: " + e.getMessage());
             e.printStackTrace();
         }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
} 
