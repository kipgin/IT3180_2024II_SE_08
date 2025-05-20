package app.controllers.admin;

import java.util.List;
import java.util.Optional;

import app.models.*;
import app.services.ApiService;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class PaymentDetailController {

    @FXML private Label lblId, lblOwner, lblStatus, lblTotalFee, lblPaid;
    @FXML private TableView<FeeSection> feeSectionTable;
    @FXML private TableColumn<FeeSection, String> colFeeName;
    @FXML private TableColumn<FeeSection, Number> colBlockUsed;
    
    @FXML private TableColumn<FeeSection, Void> colActions;

    @FXML private TextField txtFeeName, txtBlockUsed, txtUnit;
    @FXML private ComboBox<String> feeTypeCombo;

    private ObservableList<FeeSection> feeSections;
    private ObservableList<FeeName> feeTypes;
    private Stage stage; 
    
    private boolean confirmed = false;
    
    public boolean isConfirmed() {
        return confirmed;
    }


    public void setData(FeeRecord data, Stage stage, ObservableList<FeeName> feeTypes) {
        this.stage = stage;

        lblId.setText(String.valueOf(data.getId()));
        lblOwner.setText(data.getOwnerUserName());
        lblStatus.setText(data.getAccom_status());
        lblTotalFee.setText(String.valueOf(data.getTotalFee()));
        lblPaid.setText(data.isPaid() ? "Đã nộp" : "Chưa nộp");
        
        this.feeTypes = feeTypes;
        
        //feeTypeCombo.setItems(feeTypes);
        for(FeeName feename: feeTypes) {
        	feeTypeCombo.getItems().add(feename.getName());
        }
        

        // Khởi tạo danh sách
        feeSections = FXCollections.observableArrayList(data.getFeeSections());

        // Setup các cột
        colFeeName.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getName()));
        colBlockUsed.setCellValueFactory(cell -> new SimpleDoubleProperty(cell.getValue().getBlockUsed()));
        

        // Cột actions
        colActions.setCellFactory(col -> new TableCell<>() {
            private final Button btnDelete = new Button();
            

            {
            	 ImageView deleteIcon = new ImageView(new Image(getClass().getResource("/app/assets/img/delete.png").toString()));
                 deleteIcon.setFitHeight(20);
                 deleteIcon.setFitWidth(20);
                 btnDelete.setGraphic(deleteIcon);
                 btnDelete.getStyleClass().add("icon-button-delete");
            	
                btnDelete.setOnAction(event -> {
             
                    FeeSection fee = getTableView().getItems().get(getIndex());
                    
                 
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Xác nhận xóa");
                    alert.setHeaderText("Bạn có chắc chắn muốn xóa?");
                    alert.setContentText("Khoản thu: " + fee.getName());

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        boolean isDelete = ApiService.delFeeSection(lblOwner.getText(), fee.getName());
                        System.out.print(isDelete);
                        confirmed= true;
                        feeSections.remove(fee);
                    }
                });

               
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btnDelete);
                }
            }
        });

        // Đặt dữ liệu cho bảng
        feeSectionTable.setItems(feeSections);
        
        colFeeName.prefWidthProperty().bind(feeSectionTable.widthProperty().multiply(0.35));   
        colBlockUsed.prefWidthProperty().bind(feeSectionTable.widthProperty().multiply(0.35)); 
        colActions.prefWidthProperty().bind(feeSectionTable.widthProperty().multiply(0.3));
    }
    
    @FXML
    private void updateFee() {
    	String name = lblOwner.getText();
    	double newAmount = ApiService.updateFee(name);
    	if(newAmount != 0) {
    		confirmed = true;
    		lblTotalFee.setText(String.valueOf(newAmount));
    	}
    }

    @FXML
    private void handleAddFee() {
        String name = feeTypeCombo.getValue();
        String blockStr = txtBlockUsed.getText();


        if (name.isEmpty() || blockStr.isEmpty() ) {
            showAlert("Vui lòng nhập đầy đủ thông tin.");
            return;
        }

        double blockUsed;
        try {
            blockUsed = Double.parseDouble(blockStr);
        } catch (NumberFormatException e) {
            showAlert("Block Used phải là số.");
            return;
        }
        boolean isAdd = ApiService.addFeeSection(lblOwner.getText(), name, blockUsed);
        if(isAdd) {
        	confirmed= true;
        	FeeSection newFee = new FeeSection(name, blockUsed);
        	feeSections.add(newFee);
       

        	
        	txtBlockUsed.clear();
        } else {
        	showAlert("Khoản thu đã bị trùng!");
        }
    }
    
    @FXML
    private void handleUpdateFee() {
        String name = feeTypeCombo.getValue();
        String blockStr = txtBlockUsed.getText();


        if (name.isEmpty() || blockStr.isEmpty() ) {
            showAlert("Vui lòng nhập đầy đủ thông tin.");
            return;
        }

        double blockUsed;
        try {
            blockUsed = Double.parseDouble(blockStr);
        } catch (NumberFormatException e) {
            showAlert("Block Used phải là số.");
            return;
        }
        boolean isUpdate = ApiService.updateFeeSection(lblOwner.getText(), name, blockUsed);
  
        if(isUpdate) {
        	confirmed= true;
        	
        	FeeSection oldFee = null;
        	
        	for(FeeSection x: feeSections) {
        		if(x.getName().equals(name)) oldFee = x;
        	}
        	feeSections.remove(oldFee);
        	FeeSection newFee = new FeeSection(name, blockUsed);
        	feeSections.add(newFee);
        	
        	txtBlockUsed.clear();
        } else {
        	showAlert("Đã có khoản thu này đâu mà cập nhật!");
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Thông báo");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleClose() {
        stage.close();
    }
}
