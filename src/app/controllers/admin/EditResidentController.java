package app.controllers.admin;

import java.io.IOException;

import app.models.Resident;
import app.models.User;
import app.services.ApiService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EditResidentController {

    @FXML private TextField nameField;
    @FXML private ComboBox<String> genderComboBox;
    @FXML private TextField birthYearField;
    @FXML private ComboBox<String> statusComboBox;
    @FXML private TextField householdIdField;

    private Stage stage;
    private Resident currentResident;
    
    private ResidentListController parentController;
    
    public void setParentController(ResidentListController controller) {
        this.parentController = controller;
    }

    public void setResident(Resident resident) {
        this.currentResident = resident;
        nameField.setText(resident.getName());
        genderComboBox.setValue(resident.getGender());
        birthYearField.setText(String.valueOf(resident.getBirthYear()));
        statusComboBox.setValue(resident.getAccomStatus());
        householdIdField.setText(String.valueOf(resident.getHouseholdId()));
    }
    
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    

    @FXML
    private void initialize() {
        genderComboBox.getItems().addAll("MALE", "FEMALE");
        statusComboBox.getItems().addAll("RESIDENT", "MOVED_OUT");
    }

    @FXML
    private void handleSave() {
        try {
            
            String name = nameField.getText().trim();
            int birthYear = Integer.parseInt(birthYearField.getText().trim());
            int householdId = Integer.parseInt(householdIdField.getText().trim());

            currentResident.setName(name);
            currentResident.setGender(genderComboBox.getValue());
            currentResident.setBirthYear(birthYear);
            currentResident.setAccomStatus(statusComboBox.getValue());
            currentResident.setHouseholdId(householdId);
            

            boolean success = ApiService.updateResident(currentResident); 

            if (success) {
            	 showAlert(Alert.AlertType.INFORMATION, "Thành công", "Sửa đổi cư dân thành công.");
                
            } else {
            	showAlert(Alert.AlertType.ERROR, "Lỗi", "Có lỗi xảy ra khi sửa đổi thông tin cư dân.");
            }
            parentController.showStatistics(); 

            closeWindow();
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Năm sinh và ID hộ khẩu phải là số.");
        } catch (Exception e) {
            
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Đã xảy ra lỗi khi cập nhật cư dân");
        }
    }

    @FXML
    private void handleCancel() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    public static void showEditDialog(Resident resident, ResidentListController parent) {
        try {
            FXMLLoader loader = new FXMLLoader(EditAccountController.class.getResource("/app/views/admin/edit_resident.fxml"));
            AnchorPane pane = loader.load();

            EditResidentController controller = loader.getController();
            Stage dialogStage = new Stage();
            controller.setStage(dialogStage);
            controller.setResident(resident);
            controller.setParentController(parent);

            Scene scene = new Scene(pane);
            scene.getStylesheets().add(EditAccountController.class.getResource("/app/assets/css/admin/edit_resident.css").toExternalForm());
            dialogStage.setScene(scene);
            dialogStage.setTitle("Chỉnh sửa tài khoản");
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setResizable(false);
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

