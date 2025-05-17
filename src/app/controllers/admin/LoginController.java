package app.controllers.admin;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;



import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import app.models.User;
import app.services.ApiService;
import javafx.scene.effect.GaussianBlur;



import java.io.IOException;
import java.lang.ModuleLayer.Controller;
import javafx.util.Duration;

public class LoginController {
	
	private String username;
	
	private User user;
	
    @FXML
    private TextField emailField;
    
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField visiblePasswordField;
    
    @FXML
    private Button loginButton;
    @FXML
    private Button accountButton;
    
    @FXML
    private ImageView togglePassword;
    private boolean passwordVisible = false;
    
    @FXML private VBox loginForm;
    @FXML private VBox forgotPasswordForm;
    
    @FXML private TextField resetEmailField;
    @FXML private TextField verifyCodeField;
    @FXML private PasswordField newPasswordField, confirmPasswordField;
    @FXML private Button sendResetButton, verifyCodeButton, resetPasswordButton;
    @FXML private Label lblErrorMessage;
   

    @FXML
    private void showForgotPasswordForm() {
    	resetEmailField.setVisible(true);
    	resetEmailField.setManaged(true);
    	sendResetButton.setVisible(true);
    	sendResetButton.setManaged(true);
        verifyCodeField.setVisible(false);
        verifyCodeField.setManaged(false);
        verifyCodeButton.setVisible(false);
        verifyCodeButton.setManaged(false);
        newPasswordField.setVisible(false);
        confirmPasswordField.setVisible(false);
        resetPasswordButton.setVisible(false);
        newPasswordField.setManaged(false);
        confirmPasswordField.setManaged(false);
        resetPasswordButton.setManaged(false);
        animateFormSwitch(loginForm, forgotPasswordForm);
        
    }

    @FXML
    private void showLoginForm() {
        animateFormSwitch(forgotPasswordForm, loginForm);
    }

    private void animateFormSwitch(Node from, Node to) {
        TranslateTransition hide = new TranslateTransition(Duration.millis(400), from);
        hide.setToX(-400);

        TranslateTransition show = new TranslateTransition(Duration.millis(400), to);
        show.setFromX(400);
        show.setToX(0);

        hide.setOnFinished(e -> {
            from.setVisible(false);
            from.setManaged(false);
            to.setVisible(true);
            to.setManaged(true);
            show.play();
        });

        hide.play();
    }
    
    @FXML
    public void initialize() {
    	
    	
    	visiblePasswordField.textProperty().bindBidirectional(passwordField.textProperty());
        togglePassword.setOnMouseClicked(e -> togglePasswordVisibility());
        
        
        
        
    }
    
    private void togglePasswordVisibility() {
        passwordVisible = !passwordVisible;

        if (passwordVisible) {
            visiblePasswordField.setVisible(true);
            visiblePasswordField.setManaged(true);

            passwordField.setVisible(false);
            passwordField.setManaged(false);

            togglePassword.setImage(new Image(getClass().getResource("/app/assets/img/eye_closed.png").toExternalForm()));
        } else {
            visiblePasswordField.setVisible(false);
            visiblePasswordField.setManaged(false);

            passwordField.setVisible(true);
            passwordField.setManaged(true);

            togglePassword.setImage(new Image(getClass().getResource("/app/assets/img/eye_open.png").toExternalForm()));
        }
    }

    
    @FXML
    private void handleLogin(ActionEvent e) throws Exception {
        String username = emailField.getText();
        String password = passwordField.getText();
        if(username.isEmpty() || password.isEmpty()) {
        	lblErrorMessage.setText("Vui lòng nhập đầy đủ thông tin!");
            lblErrorMessage.setStyle("-fx-text-fill: red; -fx-font-size: 14px; -fx-font-weight: bold;");
            
            return ;
        }
        boolean isLogin = ApiService.login(username, password).equals("true");
        if (isLogin) {
        	lblErrorMessage.setText("Password updated successfully!");
            lblErrorMessage.setStyle("-fx-text-fill: green; -fx-font-size:15");
        	User user = ApiService.getUserByUsername(username);
        	if(user.getRole().equals("ADMIN"))
               openAdminMainScreen(user); 
        	else openResidentMainScreen(user);
        } else {
        	lblErrorMessage.setText("Tài khoản hoặc mật khẩu không chính xác!");
            lblErrorMessage.setStyle("-fx-text-fill: red;-fx-font-size:14px; -fx-font-weight: bold;");
        }
       
    }

    private void openAdminMainScreen(User user) {
        try {
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/views/admin/dashboard.fxml"));
            Parent root = loader.load();

            DashBoardController controller = loader.getController();
            controller.setUser(user);

            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setResizable(true);
            Scene scene = new Scene(root,1350,750);
            scene.getStylesheets().add(getClass().getResource("/app/assets/css/admin/dashboard.css").toExternalForm());

            stage.getIcons().add(new Image(getClass().getResourceAsStream("/app/assets/img/logo.png")));
            stage.setScene(scene);
            
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX(screenBounds.getMinX());
            stage.setY(screenBounds.getMinY());
            stage.setWidth(screenBounds.getWidth());
            stage.setHeight(screenBounds.getHeight());
            stage.setResizable(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void openResidentMainScreen(User user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/views/resident/dashboard.fxml"));
            Parent root = loader.load();
            Object controller = loader.getController();
            ((app.controllers.resident.DashBoardController) controller).setUser(user);
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setResizable(true);
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/app/assets/css/resident/dashboard.css").toExternalForm());
            stage.setScene(scene);
            
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/app/assets/img/logo.png")));
            
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX(screenBounds.getMinX());
            stage.setY(screenBounds.getMinY());
            stage.setWidth(screenBounds.getWidth());
            stage.setHeight(screenBounds.getHeight());
            
            stage.setResizable(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
   
    
    @FXML
    private void handleSendResetEmail() {
        
    	String mail = resetEmailField.getText();
    	if(mail.isEmpty()) {
    		showAlert(Alert.AlertType.ERROR, "Lỗi", "đã điền mail đâu!");
    		return ;
    	}
    	
    	username = ApiService.getUserByEmail(mail);
    	
    	Platform.runLater(() -> {
    		
            user = ApiService.getUserByUsername(username);
            System.out.print(username);
           
          });
    	if(ApiService.sendToken(username)) {
    		resetEmailField.setVisible(false);
    		resetEmailField.setManaged(false);
    		sendResetButton.setVisible(false);
    		sendResetButton.setManaged(false);
    		verifyCodeField.setVisible(true);
    		verifyCodeField.setManaged(true);
    		verifyCodeButton.setVisible(true);
    		verifyCodeButton.setManaged(true);
    	} else {
    		showAlert(Alert.AlertType.ERROR, "Lỗi", "Lỗi trong quá trình gửi mail!");
    	}
        
    }

    @FXML
    private void handleVerifyCode() {
        // Kiểm tra mã đúng
    	String token = verifyCodeField.getText();
    	System.out.print(username + " " + token);
        if (ApiService.verifyToken(username, token)) { 
            newPasswordField.setVisible(true);
            confirmPasswordField.setVisible(true);
            resetPasswordButton.setVisible(true);
            newPasswordField.setManaged(true);
            confirmPasswordField.setManaged(true);
            resetPasswordButton.setManaged(true);
        } else {
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Mã xác nhận sai!");
        }
    }

    @FXML
    private void handleResetPassword() {
        // Kiểm tra 2 ô khớp, thực hiện đổi mật khẩu
        if (!newPasswordField.getText().equals(confirmPasswordField.getText())) {
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Mật khẩu không khớp!");
            return;
        }
        
        boolean isUpdate = ApiService.changePassword(username, user.getPassword(), newPasswordField.getText());
        
        if(isUpdate) {
        	showAlert(Alert.AlertType.INFORMATION, "Thành công", "Mật khẩu đã được cập nhật");
        } else {
        	showAlert(Alert.AlertType.ERROR, "Lỗi", "Vui lòng thử lại");
        }
    }
    
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
