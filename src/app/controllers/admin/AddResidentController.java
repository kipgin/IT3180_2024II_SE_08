package app.controllers.admin;

import app.models.Resident;
import app.services.ApiService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.util.HashMap;
import java.util.Map;

public class AddResidentController {

	 @FXML
	    private TextField nameField;

	    @FXML
	    private ComboBox<String> genderComboBox;

	    @FXML
	    private TextField birthYearField;

	    @FXML
	    private ComboBox<String> statusComboBox;

	    @FXML
	    private Label iDField;

	    @FXML
	    private Button cancelButton;

	    @FXML
	    private Button confirmButton;
	    
	    private boolean confirmed = false;
	    
	    public boolean isConfirmed() {
	        return confirmed;
	    }

	    @FXML
	    private void handleCancel() {
	        Stage stage = (Stage) cancelButton.getScene().getWindow();
	        stage.close();
	    }
	    
	    private int householdId;
	    public void setIdHousehold(int id) {
	    	householdId = id;
	    	iDField.setText(String.valueOf(id));
	    }

	    @FXML
	    private void handleConfirm() {
	    	String name = nameField.getText().trim();
	        String gender = genderComboBox.getValue();
	        String status = statusComboBox.getValue();

	        if (name.isEmpty() || gender == null || birthYearField.getText().trim().isEmpty() || status == null || iDField.getText().trim().isEmpty()) {
	            showAlert("Lỗi", "Vui lòng nhập đầy đủ thông tin.");
	            return;
	        }

	        try {
	            int birthYear = Integer.parseInt(birthYearField.getText().trim());
	            
	            if (birthYear < 1900 || birthYear > 2025) {
	                showAlert("Lỗi", "Năm sinh không hợp lệ.");
	                return;
	            }
	            
	            Resident resident = new Resident(0, name, gender, birthYear, status, householdId);
	            boolean success = ApiService.addResident(resident);
	            
	            if (success) {
	            	confirmed = true;
	                showAlert("Thành công", "Cư dân đã được thêm thành công.");
	                handleCancel();
	            } else {
	                showAlert("Lỗi", "Không thể thêm cư dân. Vui lòng thử lại sau.");
	            }
	        } catch (NumberFormatException e) {
	            showAlert("Lỗi", "Năm sinh và ID hộ gia đình phải là số.");
	        }
	    }

	    private void showAlert(String title, String message) {
	        Alert alert = new Alert(Alert.AlertType.INFORMATION);
	        alert.setTitle(title);
	        alert.setHeaderText(null);
	        alert.setContentText(message);
	        alert.showAndWait();
	    }
} 
