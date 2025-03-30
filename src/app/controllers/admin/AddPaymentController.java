package app.controllers.admin;

import app.services.ApiService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.util.HashMap;
import java.util.Map;

public class AddPaymentController {

    @FXML
    private TextField areaField;

    @FXML
    private TextField serviceFeeField;

    @FXML
    private TextField totalFeeField;

    @FXML
    private TextField ownerUserNameField;

    @FXML
    private ComboBox<String> statusComboBox;

    @FXML
    private Button cancelButton;

    @FXML
    private Button confirmButton;

    private final ApiService apiService = new ApiService();

    private FeeManagementController controller;
    
    public void setFeeManagementController(FeeManagementController controller) {
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
             if (areaField.getText().isEmpty() || serviceFeeField.getText().isEmpty() ||
                     totalFeeField.getText().isEmpty() || ownerUserNameField.getText().isEmpty() ||
                     statusComboBox.getValue() == null) {
                 showAlert(Alert.AlertType.ERROR, "Lỗi", "Vui lòng điền đầy đủ thông tin.");
                 return;
             }

             double area = Double.parseDouble(areaField.getText());
             double serviceFeePerSquare = Double.parseDouble(serviceFeeField.getText());
             double totalFee = Double.parseDouble(totalFeeField.getText());
             String ownerUserName = ownerUserNameField.getText();
             String accomStatus = statusComboBox.getValue();

             boolean success = ApiService.addPaymentRecord(area, serviceFeePerSquare, totalFee, ownerUserName, accomStatus);

             if (success) {
                 showAlert(Alert.AlertType.INFORMATION, "Thành công", "Thêm hộ khẩu thành công.");
                 controller.loadData();
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
