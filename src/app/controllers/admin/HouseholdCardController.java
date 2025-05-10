package app.controllers.admin;

import app.models.Household;
import app.services.ApiService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class HouseholdCardController {

    @FXML private Label ownerLabel;
    @FXML private Label locationLabel;
    @FXML private Label statusLabel;
    @FXML private Label membersLabel;
    @FXML private Button detailBtn;
    @FXML private Button deleteBtn;

    private Household household;
    private ResidentController parent;

    public void setData(Household household, ResidentController parent) {
        this.household = household;
        this.parent = parent;

        ownerLabel.setText("👤 Chủ hộ: " + household.getOwnerUsername());
        locationLabel.setText("📍 Tòa nhà:  " + household.getBuildingBlock());
        statusLabel.setText("📌 Số phòng:  " + household.getRoomNumber());
        membersLabel.setText("👥 Số thành viên:  " + household.getNumOfMembers());

        detailBtn.setOnAction(event -> showHouseholdDetailFrame());
        deleteBtn.setOnAction(event -> delete());
    }
    private void showHouseholdDetailFrame() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/views/admin/householdDetail.fxml"));
            Parent root = loader.load();

            // Truyền dữ liệu sang controller
            HouseholdDetailController controller = loader.getController();
            controller.setHouseholdData(household);
            
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/app/assets/css/admin/householdDetail.css").toExternalForm());

            Stage stage = new Stage();
            stage.setTitle("Thông tin chi tiết hộ gia đình");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void delete() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Xác nhận xóa");
        alert.setContentText("Xóa hộ: " + household.getOwnerUsername() + "?");

        alert.showAndWait().ifPresent(result -> {
            if (result.getText().equals("OK")) {
                ApiService.delHouseholdByUsername(household);
                parent.refresh();
            }
        });
    }
}
