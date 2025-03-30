package app.controllers.admin;

import app.services.ApiService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.util.HashMap;
import java.util.Map;

public class AddCharityController {

    @FXML
    private TextField ownerUserNameField;

    @FXML
    private ComboBox<String> statusComboBox;

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
             if (ownerUserNameField.getText().isEmpty() ||
                     statusComboBox.getValue() == null) {
                 showAlert(Alert.AlertType.ERROR, "Lỗi", "Vui lòng điền đầy đủ thông tin.");
                 return;
             }

             String ownerUserName = ownerUserNameField.getText();
             String accomStatus = statusComboBox.getValue();

             boolean success = ApiService.addCharityRecord(ownerUserName, accomStatus);

             if (success) {
                 showAlert(Alert.AlertType.INFORMATION, "Thành công", "Thêm hộ khẩu thành công.");
                 controller.loadColumns();
                 handleCancel();
             } else {
                 showAlert(Alert.AlertType.ERROR, "Lỗi", "Có lỗi xảy ra khi thêm hộ khẩu.");
             }
         } catch (NumberFormatException e) {
             showAlert(Alert.AlertType.ERROR, "Lỗi", "Vui lòng nhập đúng định dạng số.");
         } catch (Exception e) {
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
