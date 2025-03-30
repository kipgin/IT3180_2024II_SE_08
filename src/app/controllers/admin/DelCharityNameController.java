package app.controllers.admin;

import app.models.CharityName;
import app.services.ApiService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DelCharityNameController {

    @FXML
    private TextField donateNameField;

    @FXML
    private Button cancelButton;

    @FXML
    private Button confirmButton;
    
    @FXML
    private ComboBox<String> charityNameComboBox; 

    @FXML
    public void initialize() {
        try {
            // Gọi API để lấy danh sách charityName
            List<CharityName> charityNames = ApiService.getAllCharityName();

            // Thêm vào ComboBox
            charityNameComboBox.getItems().addAll(
                charityNames.stream().map(CharityName::getName).toList()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
             if (charityNameComboBox.getValue().isEmpty()) {
                 showAlert(Alert.AlertType.ERROR, "Lỗi", "Vui lòng chọn khoản cần xóa.");
                 return;
             }

             String donateName = charityNameComboBox.getValue();

             boolean success = ApiService.delCharityName(donateName);

             if (success) {
                 showAlert(Alert.AlertType.INFORMATION, "Thành công", "Xóa khoản đóng góp thành công.");
                 controller.loadColumns();
                 handleCancel();
             } else {
                 showAlert(Alert.AlertType.ERROR, "Lỗi", "Có lỗi xảy ra khi xóa khoản đóng góp.");
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
