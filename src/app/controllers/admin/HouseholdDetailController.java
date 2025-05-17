package app.controllers.admin;

import java.io.IOException;
import java.util.List;

import app.models.Household;
import app.models.PaymentRecord;
import app.models.Resident;
import app.models.User;
import app.services.ApiService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;

public class HouseholdDetailController {

    @FXML
    private Label lblId, lblOwner, lblMembers, lblLocation, lblStatus;
    
    @FXML
    private TableView<Resident> residentTable;

    @FXML
    private TableColumn<Resident, Number> colId;
    
    @FXML
    private TableColumn<Resident, String> colName;

    @FXML
    private TableColumn<Resident, String> colGender;

    @FXML
    private TableColumn<Resident, Number> colBirthYear;

    @FXML
    private TableColumn<Resident, String> colAccomStatus;
    
    //@FXML
    //private TableColumn<Resident, String> colHouseholdId;
    

    private Household household;
    
    public void loadTable() {
    	
    	 new Thread(() -> {
             try {
                 List<Resident> users = ApiService.getResidentsByHouseholdId(Integer.valueOf(household.getId()));
                 Platform.runLater(() -> {
                     ObservableList<Resident> data = FXCollections.observableArrayList(users);
                     residentTable.setItems(data);
                 });
             } catch (Exception e) {
                 Platform.runLater(() -> showError("Lỗi khi lấy dữ liệu: " + e.getMessage()));
             }
         }).start();
         
    }

    public void setHouseholdData(Household household) {
        this.household = household;
        lblId.setText(String.valueOf(household.getId()));
        lblOwner.setText(household.getOwnerUsername());
        lblMembers.setText(String.valueOf(household.getNumOfMembers()));
        lblLocation.setText(household.getBuildingBlock());
        lblStatus.setText(household.getRoomNumber());
        
        colId.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getId()));
        colName.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getName()));
        colBirthYear.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getBirthYear()));
        colAccomStatus.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getAccomStatus()));
        colGender.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getGender()));

        
        loadTable();
        
        
    }
    @FXML
    public void addResident() {
        // Gọi API hoặc mở form nhập liệu để thêm nhân khẩu
    	try {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/views/admin/add_resident.fxml"));
    		Parent root = loader.load();
    		Object controller = loader.getController();
    		((AddResidentController) controller).setIdHousehold(household.getId());
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/app/assets/css/admin/add_resident.css").toExternalForm());
            Stage stage = new Stage();
            stage.setTitle("Thêm Nhân Khẩu Mới");
            stage.setScene(scene);
            stage.showAndWait();
            
            if (((AddResidentController) controller).isConfirmed()) {
                loadTable();
               
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
            
        
    }

    @FXML
    public void deleteResident() {
        // Gọi API để xóa nhân khẩu
        System.out.println("Xóa nhân khẩu khỏi hộ: " + household.getId());
    }
    
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Lỗi");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
