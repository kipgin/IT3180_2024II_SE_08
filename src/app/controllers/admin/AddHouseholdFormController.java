package app.controllers.admin;

import app.services.ApiService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddHouseholdFormController {
	@FXML
    private TextField ownerUserName;

    @FXML
    private TextField numOfMembers;

    @FXML
    private void handleConfirmButton() {
    	
    	String owner = ownerUserName.getText();
        int members;
        try {
            members = Integer.parseInt(numOfMembers.getText());
        } catch (NumberFormatException e) {
            showAlert("Lỗi", "Vui lòng nhập số hợp lệ cho số thành viên.");
            return;
        }

        boolean success = ApiService.addHousehold(owner, members);

        if (success) {
            showAlert("Thành công", "Thêm hộ khẩu thành công!");
            closeForm();
        } else {
            showAlert("Thất bại", "Thêm hộ khẩu thất bại!");
        }
    }
    
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void closeForm() {
        Stage stage = (Stage) ownerUserName.getScene().getWindow();
        stage.close();
    }
}
