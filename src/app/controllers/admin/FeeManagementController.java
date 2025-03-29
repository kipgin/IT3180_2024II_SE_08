package app.controllers.admin;

import javafx.application.Platform;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

import app.controllers.DashboardControllable;
import app.models.PaymentRecord;
import app.services.ApiService;

public class FeeManagementController implements DashboardControllable {

  

    @FXML
    private TableView<PaymentRecord> tableView;

    @FXML
    private TableColumn<PaymentRecord, Number> colId;

    @FXML
    private TableColumn<PaymentRecord, String> colOwner;

    @FXML
    private TableColumn<PaymentRecord, Number> colArea;

    @FXML
    private TableColumn<PaymentRecord, Number> colServiceFeePerSquare;

    @FXML
    private TableColumn<PaymentRecord, Number> colTotalFee;

    @FXML
    private TableColumn<PaymentRecord, String> colStatus;

    @FXML
    private TableColumn<PaymentRecord, String> colAction;
    
    @FXML
    private PieChart paymentPieChart;

    @FXML
    public void initialize() {
       
    	
        colId.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()));
        colOwner.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOwnerUserName()));
        colArea.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getArea()));
        colServiceFeePerSquare.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getServiceFeePerSquare()));
        colTotalFee.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getTotalFee()));
        colStatus.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAccom_status()));
        colAction.setCellFactory(param -> new TableCell<>() {
            private final Button payButton = new Button("Ghi Nhận");

            {
                payButton.setOnAction(event -> {
                    PaymentRecord paymentRecord = getTableView().getItems().get(getIndex());
                    openReceiptDialog(paymentRecord);
                });
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(payButton);
                }
            }
        });
        
        colId.prefWidthProperty().bind(tableView.widthProperty().multiply(0.1));
        colOwner.prefWidthProperty().bind(tableView.widthProperty().multiply(0.2));
        colArea.prefWidthProperty().bind(tableView.widthProperty().multiply(0.1));
        colServiceFeePerSquare.prefWidthProperty().bind(tableView.widthProperty().multiply(0.15));
        colTotalFee.prefWidthProperty().bind(tableView.widthProperty().multiply(0.1));
        colStatus.prefWidthProperty().bind(tableView.widthProperty().multiply(0.15));
        colAction.prefWidthProperty().bind(tableView.widthProperty().multiply(0.2));

        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Double click
                PaymentRecord selectedPayment = tableView.getSelectionModel().getSelectedItem();
                if (selectedPayment != null) {
                    showPaymentDetailFrame(selectedPayment);
                }
            }
        });
   
        loadData();
       
    }

    public void loadData() {
        
        new Thread(() -> {
            try {
                List<PaymentRecord> payments = ApiService.getAllPayments();
                Platform.runLater(() -> {
                    ObservableList<PaymentRecord> data = FXCollections.observableArrayList(payments);
                    tableView.setItems(data);
                    updatePieChart(data);
                });
            } catch (Exception e) {
                Platform.runLater(() -> showError("Lỗi khi lấy dữ liệu: " + e.getMessage()));
            }
        }).start();
    }
    
    private void updatePieChart(ObservableList<PaymentRecord> data) {
        long paidCount = data.stream().filter(record -> record.getTotalFee() == 0).count();
        long unpaidCount = data.size() - paidCount;
        
        PieChart.Data paidData = new PieChart.Data("Đã nộp", paidCount);
        PieChart.Data unpaidData = new PieChart.Data("Chưa nộp", unpaidCount);
        
        paymentPieChart.setData(FXCollections.observableArrayList(paidData, unpaidData));
    }
    
    private void openReceiptDialog(PaymentRecord paymentRecord) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/views/admin/receiptDialog.fxml"));
            Parent root = loader.load();

            ReceiptDialogController controller = loader.getController();
            controller.setPaymentRecord(paymentRecord);

            Stage stage = new Stage();
            stage.setTitle("Biên Lai Thanh Toán");
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/app/assets/css/admin/receiptDialog.css").toExternalForm());
            stage.setScene(scene);
            stage.showAndWait();

            if (controller.isConfirmed()) {
                loadData();
                System.out.println("Thanh toán thành công!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showPaymentDetailFrame(PaymentRecord payment) {
        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/views/paymentDetail.fxml"));
//            Parent root = loader.load();
//
//            PaymentDetailController controller = loader.getController();
//            controller.setPaymentData(payment);
//
//            Scene scene = new Scene(root);
//            scene.getStylesheets().add(getClass().getResource("/app/assets/paymentDetail.css").toExternalForm());
//
//            Stage stage = new Stage();
//            stage.setTitle("Thông tin chi tiết khoản thu");
//            stage.setScene(scene);
//            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Lỗi");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

	@Override
	public void setDashboardController(DashBoardController controller) {
		// TODO Auto-generated method stub
		
	}
	
	 @FXML
	 public void handleAddPaymentButton() {
	    	try {
	    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/views/admin/add_payment.fxml"));
	    		 Parent root = loader.load();
	    		 Object controller = loader.getController();
	 
	             ((AddPaymentController) controller).setFeeManagementController(this);
	             
	           
	             
	            Scene scene = new Scene(root);
	            scene.getStylesheets().add(getClass().getResource("/app/assets/css/admin/add_payment.css").toExternalForm());
	            Stage stage = new Stage();
	            stage.setTitle("Thêm Hộ Khẩu Mới");
	            stage.setScene(scene);
	            stage.show();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
}