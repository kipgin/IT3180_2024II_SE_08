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
    private TextField buildingBlock;
    
    @FXML
    private TextField floorNumber;
    
    @FXML
    private TextField roomNumber;
    
    private boolean confirmed = false;
    
    public boolean isConfirmed() {
        return confirmed;
    }

    @FXML
    private void handleConfirmButton() {
    	
    	String owner = ownerUserName.getText();
    	String block = buildingBlock.getText();
    	int floor;
    	String room = roomNumber.getText();
        int members;
        try {
            floor = Integer.parseInt(floorNumber.getText());
        } catch (NumberFormatException e) {
            showAlert("Lỗi", "Vui lòng nhập số hợp lệ số phòng.");
            return;
        }
        
        if(owner == "" || block == "" || room =="" || floor == 0) {
        	showAlert("Lỗi", "Vui lòng nhập đầy đủ thông tin.");
            return;
        }
        
        if(floor < 0 || floor > 1000) {
        	showAlert("Lỗi", "Nhập sai số phòng.");
            return;
        }

        boolean success = ApiService.addHousehold(owner, block,floor,room);

        if (success) {
            showAlert("Thành công", "Thêm hộ khẩu thành công!");
            confirmed = true;
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
