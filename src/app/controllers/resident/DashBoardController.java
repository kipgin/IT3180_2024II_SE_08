package app.controllers.resident;

import javafx.animation.*;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;

import java.io.IOException;
import javafx.util.Duration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import app.controllers.DashboardControllable;
import app.controllers.resident.HouseholdDetailController;
import app.models.CharityName;
import app.models.User;
import app.services.ApiService;
import app.controllers.admin.*;


public class DashBoardController {
	
	 @FXML
	 private Label clockLabel;

	 @FXML
	 private Label statusLabel;

	 @FXML
	 private Label versionLabel;

	 private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy");

	@FXML
	private TextField searchField;
	
	@FXML
	private MenuButton profileMenuButton,settingsButton;
	
    @FXML
    private BorderPane mainBorderPane;
    
    @FXML
    private MenuItem fullscreenBtn;
    
    private User user;
    
    public void setUser(User user) {
    	this.user = user;
    }
    
    @FXML private Button overviewButton;
    @FXML private Button accountButton;
    @FXML private Button residentButton;
    @FXML private Button feeButton;
    @FXML private Button charityButton;
    @FXML private Button complaintsButton;
    @FXML private Button settingButton;
    @FXML private Button notificationButton;

    private Button currentActiveButton;

    private void setActiveButton(Button clickedButton) {
        if (currentActiveButton != null) {
            currentActiveButton.getStyleClass().remove("sidebar-button-active");
        }

        clickedButton.getStyleClass().add("sidebar-button-active");
        currentActiveButton = clickedButton;
    }
    
    private void toggleFullscreen() {
        Stage stage = (Stage) fullscreenBtn.getParentPopup().getOwnerWindow();
        stage.setFullScreen(!stage.isFullScreen());
    }

    
    @FXML
    private void handleLogout() {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/views/admin/login.fxml"));
            Parent root = loader.load();
            
            Stage stage = (Stage) mainBorderPane.getScene().getWindow();
            stage.setResizable(true);
            Scene scene = new Scene(root,450,450);
            scene.getStylesheets().add(getClass().getResource("/app/assets/css/admin/style.css").toExternalForm());
            stage.setScene(scene);
            stage.sizeToScene();
            stage.setX(500);
            stage.setY(150);
            stage.setResizable(false);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void startClock() {
        Timeline clock = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            clockLabel.setText(LocalDateTime.now().format(formatter));
        }));
        clock.setCycleCount(Timeline.INDEFINITE);
        clock.play();
    }

    public void updateStatus(String message) {
        statusLabel.setText(message);
    }
    
    @FXML
    public void initialize() {
    	
    	loadFXML("/app/views/admin/overview.fxml","/app/assets/css/admin/overview.css");
    	startClock();
    	fullscreenBtn.setOnAction(e -> toggleFullscreen());
    	
    }
    
    @FXML
    private void handleOverviewButton() {
    	setActiveButton(overviewButton);
        loadFXML("/app/views/admin/overview.fxml","/app/assets/css/admin/overview.css");
    }


    @FXML
    public void handleResidentButton() {
    	setActiveButton(residentButton);
        loadFXML("/app/views/resident/householdDetail.fxml","/app/assets/css/resident/householdDetail.css");
    }
    @FXML
    private void handleFeeButton() {
    	 setActiveButton(feeButton);
    	 loadFXML("/app/views/resident/fee_list.fxml","/app/assets/css/resident/fee_list.css");
    }
    
    @FXML
    private void handleCharityButton() {
    	setActiveButton(charityButton);
    	 loadFXML("/app/views/resident/charity_list.fxml","/app/assets/css/resident/charity_list.css");
    }
    @FXML
    private void handleNotificationButton() {
    	setActiveButton(notificationButton);
    	 loadFXML("/app/views/resident/notification.fxml","/app/assets/css/admin/notification.css");
    }
    @FXML
    private void handleChangePasswordButton() {
    	 loadFXML("/app/views/admin/change_password.fxml","/app/assets/css/admin/change_password.css");
    }
    
    @FXML
    private void handleAccountInfoButton() {
    	 loadFXML("/app/views/admin/account_info.fxml","/app/assets/css/admin/account_info.css");
    }
    
    // Phương thức để load FXML và đặt vào center
    public void loadFXML(String fxmlFile,String cssFile) {
        try {
        	
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent content = loader.load();
            
            Object controller = loader.getController();
//            if (controller instanceof DashboardControllable) {
//                ((DashboardControllable) controller).setDashboardController(this);
////            }
            
           
            if (controller instanceof ChangePasswordController) {
                ((ChangePasswordController) controller).setUser(user);
            }
            
            if (controller instanceof AccountInfoController) {
                ((AccountInfoController) controller).setUser(user);
            }
            
            if (controller instanceof NotificationController) {
                ((NotificationController) controller).setUser(user);
            }
            
            if (controller instanceof HouseholdDetailController) {
                ((HouseholdDetailController) controller).setUser(user);
            }
            
            if (controller instanceof FeeListController) {
                ((FeeListController) controller).setUser(user);
            }
            
            
            
            content.getStylesheets().add(getClass().getResource(cssFile).toExternalForm());

            mainBorderPane.setCenter(content); 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
