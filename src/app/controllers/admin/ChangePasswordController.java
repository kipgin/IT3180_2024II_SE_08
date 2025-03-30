package app.controllers.admin;

import app.models.User;
import app.services.ApiService;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class ChangePasswordController {
    
    @FXML private PasswordField txtOldPassword;
    @FXML private PasswordField txtNewPassword;
    @FXML private PasswordField txtConfirmPassword;
    @FXML private TextField txtOldPasswordVisible;
    @FXML private TextField txtNewPasswordVisible;
    @FXML private TextField txtConfirmPasswordVisible;
    @FXML private Button btnToggleOldPassword;
    @FXML private Button btnToggleNewPassword;
    @FXML private Button btnToggleConfirmPassword;
    @FXML private Label lblErrorMessage;
    
    @FXML
    private ImageView dinoImageView;

    // Kích thước của mỗi frame trong sprite sheet
    private static final int FRAME_WIDTH = 300;  // Điều chỉnh theo ảnh của bạn
    private static final int FRAME_HEIGHT = 358;
    private static final int FRAME_COUNT = 6;   // Tổng số frame
    private static final int SPRITE_SHEET_WIDTH = FRAME_WIDTH * FRAME_COUNT;

    private boolean isOldPasswordVisible = false;
    private boolean isNewPasswordVisible = false;
    private boolean isConfirmPasswordVisible = false;
    
    private User user;
    public void setUser(User user) {
        this.user = user;
    }
    
    @FXML
    public void initialize() {
        startDinoAnimation();
        txtOldPasswordVisible.setManaged(false);
        txtNewPasswordVisible.setManaged(false);
        txtConfirmPasswordVisible.setManaged(false);
    }
    
    private void startDinoAnimation() {
        // Load sprite sheet
        Image dinoSpriteSheet = new Image(getClass().getResource("/app/assets/img/dino_spritesheet.png").toExternalForm());
        dinoImageView.setImage(dinoSpriteSheet);
        dinoImageView.setViewport(new Rectangle2D(0, 0, FRAME_WIDTH, FRAME_HEIGHT));
        
        // Animation cho khủng long chạy
        DinoAnimation dinoAnimation = new DinoAnimation(dinoImageView);
        dinoAnimation.setCycleCount(Animation.INDEFINITE);
        dinoAnimation.play();
        
        // Animation chạy ngang màn hình
        TranslateTransition dinoRun = new TranslateTransition(Duration.seconds(12), dinoImageView);
        dinoRun.setFromX(0);
        dinoRun.setToX(1100);
        dinoRun.setCycleCount(TranslateTransition.INDEFINITE);
        dinoRun.setAutoReverse(false);
        dinoRun.play();
    }
    
    private class DinoAnimation extends Transition {
        private final ImageView dino;

        public DinoAnimation(ImageView dino) {
            this.dino = dino;
            setCycleDuration(Duration.seconds(1));
            setInterpolator(Interpolator.LINEAR);
        }

        @Override
        protected void interpolate(double frac) {
            int index = (int) (frac * FRAME_COUNT);
            dino.setViewport(new Rectangle2D(index * FRAME_WIDTH, 0, FRAME_WIDTH, FRAME_HEIGHT));
        }
    }
    
    @FXML
    private void toggleOldPassword() {
        isOldPasswordVisible = !isOldPasswordVisible;
        togglePasswordField(txtOldPassword, txtOldPasswordVisible, isOldPasswordVisible);
    }

    @FXML
    private void toggleNewPassword() {
        isNewPasswordVisible = !isNewPasswordVisible;
        togglePasswordField(txtNewPassword, txtNewPasswordVisible, isNewPasswordVisible);
    }

    @FXML
    private void toggleConfirmPassword() {
        isConfirmPasswordVisible = !isConfirmPasswordVisible;
        togglePasswordField(txtConfirmPassword, txtConfirmPasswordVisible, isConfirmPasswordVisible);
    }

    private void togglePasswordField(PasswordField passwordField, TextField textField, boolean isVisible) {
        if (isVisible) {
            textField.setText(passwordField.getText());
            textField.setManaged(true);
            textField.setVisible(true);
            passwordField.setManaged(false);
            passwordField.setVisible(false);
        } else {
            passwordField.setText(textField.getText());
            passwordField.setManaged(true);
            passwordField.setVisible(true);
            textField.setManaged(false);
            textField.setVisible(false);
        }
    }

    @FXML
    private void updatePassword() {
        try {
            // Lấy giá trị mật khẩu từ field nào đang hiển thị
            String oldPass = txtOldPassword.isVisible() ? txtOldPassword.getText() : txtOldPasswordVisible.getText();
            String newPass = txtNewPassword.isVisible() ? txtNewPassword.getText() : txtNewPasswordVisible.getText();
            String confirmPass = txtConfirmPassword.isVisible() ? txtConfirmPassword.getText() : txtConfirmPasswordVisible.getText();

            // Kiểm tra rỗng
            if (oldPass.isEmpty() || newPass.isEmpty() || confirmPass.isEmpty()) {
                throw new IllegalArgumentException("Fields cannot be empty!");
            }

            // Kiểm tra mật khẩu nhập lại có khớp không
            if (!newPass.equals(confirmPass)) {
                throw new IllegalArgumentException("Passwords do not match!");
            }

            // Gọi API để cập nhật mật khẩu
            boolean isUpdated = ApiService.changePassword(user.getUsername(), oldPass, newPass);

            if (isUpdated) {
                lblErrorMessage.setText("Password updated successfully!");
                lblErrorMessage.setStyle("-fx-text-fill: green;");
            } else {
                throw new Exception("Failed to update password. Please try again!");
            }

        } catch (IllegalArgumentException e) {
            lblErrorMessage.setText(e.getMessage());
            lblErrorMessage.setStyle("-fx-text-fill: red;");
        } catch (Exception e) {
            lblErrorMessage.setText("An error occurred: " + e.getMessage());
            lblErrorMessage.setStyle("-fx-text-fill: red;");
        }
    }
}
