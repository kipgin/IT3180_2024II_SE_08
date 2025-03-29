package app.controllers.admin;

import app.models.CharityName;
import app.models.CharityRecord;
import app.services.ApiService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DonationDialogController {
    
    @FXML private Label ownerLabel;
    @FXML private Label locationLabel;
    @FXML private Label charityNameLabel;
    @FXML private TextField amountField;

    private CharityRecord record;
    private CharityName charityName;
    private boolean confirmed = false;
    
    public boolean isConfirmed() {
        return confirmed;
    }
    
    public void setData(CharityRecord record, CharityName charityName) {
        this.record = record;
        this.charityName = charityName;

        ownerLabel.setText(record.getOwnerUserName());
        locationLabel.setText(record.getAccomStatus());
        charityNameLabel.setText(charityName.getName());
    }

    @FXML
    private void handleConfirm() {
        String inputText = amountField.getText().trim();
        
        if (inputText.isEmpty()) {
            showAlert("Lỗi", "Vui lòng nhập số tiền!");
            return;
        }

        try {
            int amount = Integer.parseInt(inputText);
            
            if (amount <= 0) {
                showAlert("Lỗi", "Số tiền phải lớn hơn 0!");
                return;
            }
            confirmed = true;
           
            ApiService.updateCharitySection(record.getOwnerUserName(),charityName.getName() , amount);
            
            ((Stage) amountField.getScene().getWindow()).close();
        } catch (NumberFormatException e) {
            showAlert("Lỗi", "Số tiền không hợp lệ! Vui lòng nhập số nguyên.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
