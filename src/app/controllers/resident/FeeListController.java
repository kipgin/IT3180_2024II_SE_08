package app.controllers.resident;

import app.controllers.DashboardControllable;
import app.models.FeeName;
import app.models.FeeRecord;
import app.models.User;
import app.services.ApiService;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FeeListController  {

    @FXML
    private TableView<FeeRecord> tableView;
    @FXML private TableColumn<FeeRecord, Number> colHouseholdId;
    @FXML private TableColumn<FeeRecord, String> colOwner;
    @FXML private TableColumn<FeeRecord, String> colStatus;
    @FXML private TableColumn<FeeRecord, Number> colTotalFee;
    @FXML private TableColumn<FeeRecord, String> colPaid;
    @FXML private TableColumn<FeeRecord, String> colAction;
    
    
   
    private User user;
    public void setUser(User user) {
    	this.user = user;
    	
    }

    @FXML
    public void initialize() {
        setupTableColumns();
        
        loadData();
       
    }

    private void setupTableColumns() {
    	colHouseholdId.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getId()));
        colOwner.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getOwnerUserName()));
        colStatus.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAccom_status()));
        colTotalFee.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getTotalFee()));
        colPaid.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().isPaid() ? "COMPLETED" : "INCOMPLETE"));

        colPaid.setCellFactory(column -> new TableCell<FeeRecord, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    Label dot = new Label("●");
                    dot.getStyleClass().add(item.equalsIgnoreCase("Completed") ? "status-dot-green" : "status-dot-red");

                    Label label = new Label(item);
                    label.setGraphic(dot);
                    label.setContentDisplay(ContentDisplay.LEFT);
                    label.getStyleClass().add(item.equalsIgnoreCase("Completed") ? "status-pill-green" : "status-pill-red");

                    setGraphic(label);
                    setAlignment(Pos.CENTER);
                }
            }
        });
        
        colAction.setCellFactory(col -> new TableCell<>() {
            private final HBox actionBox = new HBox(10);

            {
                actionBox.setAlignment(Pos.CENTER);
                
                ImageView iconQR = createIcon("/app/assets/img/qr.png");

                HBox boxQR = wrapIcon(iconQR, "Nộp phí trực tuyến");
    
                boxQR.setOnMouseClicked(e -> openQR());
                
                actionBox.getChildren().addAll(boxQR);
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                
                if (empty || !getTableView().getItems().get(getIndex()).getOwnerUserName().equals(user.getUsername())) {
                    setGraphic(null);
                } else {
                	setGraphic(actionBox);
                }
            }

            private ImageView createIcon(String path) {
                ImageView icon = new ImageView(new Image(getClass().getResourceAsStream(path)));
                icon.setFitWidth(18);
                icon.setFitHeight(18);
                return icon;
            }

            private HBox wrapIcon(ImageView icon, String tooltipText) {
            	
            	HBox box = new HBox(icon);
                box.setAlignment(Pos.CENTER);
                box.setPadding(new Insets(5));
                box.setCursor(Cursor.HAND);
                box.getStyleClass().add("action-icon");

                Tooltip tooltip = new Tooltip(tooltipText);
                tooltip.setShowDelay(Duration.millis(200));       
                tooltip.setHideDelay(Duration.millis(100));       
                tooltip.setShowDuration(Duration.seconds(10));    
                Tooltip.install(box, tooltip);
                
                return box;
            }
        });

        

        colHouseholdId.prefWidthProperty().bind(tableView.widthProperty().multiply(0.17));
        colOwner.prefWidthProperty().bind(tableView.widthProperty().multiply(0.22));
        colStatus.prefWidthProperty().bind(tableView.widthProperty().multiply(0.17));
        colTotalFee.prefWidthProperty().bind(tableView.widthProperty().multiply(0.15));
        colPaid.prefWidthProperty().bind(tableView.widthProperty().multiply(0.19));
        colAction.prefWidthProperty().bind(tableView.widthProperty().multiply(0.1));
        
    }

    public void loadData() {
        new Thread(() -> {
            try {
            	List<FeeRecord> records = ApiService.getAllPayments();
                Platform.runLater(() -> {
                    ObservableList<FeeRecord> data = FXCollections.observableArrayList(records);
                    tableView.setItems(data);        
                });
            } catch (Exception e) {
                Platform.runLater(() -> showError("Lỗi khi lấy dữ liệu: " + e.getMessage()));
            }
        }).start();
    }

    public void openQR() {
    	    
    	   
    	    ImageView qrView = createIcon("/app/assets/img/qr_code.png");
    	    qrView.setFitWidth(200);
    	    qrView.setPreserveRatio(true);

    	   
    	    Label title = new Label("Hướng dẫn nộp phí");
    	    title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

    	    Label line1 = new Label("Quét mã QR để chuyển khoản.");
    	    Label line2 = new Label("Nội dung chuyển khoản:");
    	    Label line3 = new Label("Tòa + Số phòng + Tên chủ hộ (không dấu)");
    	    Label example = new Label("Ví dụ: toaA_203_NGUYENVANA");
    	    example.setStyle("-fx-font-style: italic; -fx-text-fill: #2980b9;");

    	    VBox instructionBox = new VBox(5, line1, line2, line3, example);
    	    instructionBox.setAlignment(Pos.CENTER_LEFT);

    	    VBox root = new VBox(20, title, qrView, instructionBox);
    	    root.setPadding(new Insets(20));
    	    root.setAlignment(Pos.CENTER);

    	    Stage popup = new Stage();
    	    popup.initModality(Modality.APPLICATION_MODAL);
    	    popup.setTitle("Nộp phí online");
    	    popup.setScene(new Scene(root, 400, 400));
    	    popup.showAndWait();
    	

    }
    
    private ImageView createIcon(String path) {
        ImageView icon = new ImageView(new Image(getClass().getResourceAsStream(path)));
        icon.setFitWidth(200);
        icon.setFitHeight(280);
        return icon;
    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Lỗi");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

 
}