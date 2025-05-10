package app.controllers.admin;

import app.models.User;
import app.services.ApiService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class EditAccountController {

    @FXML
    private TextField usernameField;
    @FXML
    private TextField fullNameField;
    @FXML
    private ComboBox<String> roleComboBox;
    @FXML
    private CheckBox activeCheckBox;

    private Stage stage;
    private User user;
    private AccountController parentController;

    public void setUser(User user) {
        this.user = user;
        usernameField.setText(user.getUsername());
        fullNameField.setText(user.getFullName());
        roleComboBox.getItems().addAll("ADMIN", "RESIDENT"); 
        roleComboBox.setValue(user.getRole());
        activeCheckBox.setSelected(user.isActive());
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setParentController(AccountController controller) {
        this.parentController = controller;
    }

    @FXML
    private void handleSave() {
        user.setFullName(fullNameField.getText());
        user.setRole(roleComboBox.getValue());
        user.setActive(activeCheckBox.isSelected());

        //ApiService.updateUser(user); 
        parentController.showStatistics(); 
        stage.close(); 
    }

    @FXML
    private void handleCancel() {
        stage.close();
    }

    public static void showEditDialog(User user, AccountController parent) {
        try {
            FXMLLoader loader = new FXMLLoader(EditAccountController.class.getResource("/app/views/admin/edit_account.fxml"));
            AnchorPane pane = loader.load();

            EditAccountController controller = loader.getController();
            Stage dialogStage = new Stage();
            controller.setStage(dialogStage);
            controller.setUser(user);
            controller.setParentController(parent);

            Scene scene = new Scene(pane);
            scene.getStylesheets().add(EditAccountController.class.getResource("/app/assets/css/admin/edit_account.css").toExternalForm());
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
