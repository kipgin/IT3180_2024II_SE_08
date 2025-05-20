package app.controllers.admin;

import java.net.URL;

import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import app.models.*;
import app.services.ApiService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class NotificationController  {

	 	@FXML private AnchorPane modalPane;
	 	@FXML private TextField titleField;
	 	@FXML private TextArea contentField;
	 	@FXML private TextField toField;
	 	@FXML private TextField ccField;
	 	@FXML private TextField bccField;
	 	@FXML
	 	private RadioButton sendToAllRadio, sendToOneRadio;
	 	@FXML
	 	private ToggleGroup sendModeGroup;
	 	@FXML
	 	private VBox recipientFields;
	 	
	 	private User user;
	 	
	 	public void setUser(User user) {
	 		this.user = user;
	 	}

	 	@FXML
	 	private void handleSendToAll() {
	 	    sendToOneRadio.setSelected(false);
	 	    sendToAllRadio.setSelected(true);
	 	    recipientFields.setVisible(false);
	 	    recipientFields.setManaged(false);
	 	}

	 	@FXML
	 	private void handleSendToOne() {
	 	    sendToAllRadio.setSelected(false);
	 	    sendToOneRadio.setSelected(true);
	 	    recipientFields.setVisible(true);
	 	    recipientFields.setManaged(true);
	 	}

		@FXML
		private Label totalLabel;

		@FXML
		private TextField searchField;

		@FXML
		private ListView<Notification> notificationListView;
		
		@FXML private HBox onTabAll, onTabSent, onTabReceived;

		private void setSelectedTab(HBox selectedTab) {
		    List<HBox> tabs = List.of(onTabAll, onTabSent, onTabReceived);
		    for (HBox tab : tabs) {
		        tab.getStyleClass().remove("selected");
		    }
		    if (!selectedTab.getStyleClass().contains("selected")) {
		        selectedTab.getStyleClass().add("selected");
		    }
		}

		
		 @FXML
		    public void initialize() {
			 onTabAll();
		 }
		 
		@FXML
		private void onTabAll() {
			setSelectedTab(onTabAll);
		    loadNotifications("all");
		}

		@FXML
		private void onTabSent() {
			setSelectedTab(onTabSent);
		    loadNotifications("sent");
		}

		@FXML
		private void onTabReceived() {
			setSelectedTab(onTabReceived);
		    loadNotifications("received");
		}
		
		
        public void loadNotifications(String format) {
        	notificationListView.setCellFactory(list -> new NotificationCell());
        
        if(format.equals("received")) {
        	new Thread(() -> {
                try {
                	List<Notification> notiList = ApiService.getUserNotifications(user.getUsername());
                    
                    Platform.runLater(() -> {
                    	
                    	notificationListView.setItems(FXCollections.observableArrayList(notiList));
              
                    });
                } catch (Exception e) {
                   
                }
            }).start();
        } else if(format.equals("all")) {
        new Thread(() -> {
            try {
            	List<Notification> notiList = ApiService.getGeneralNotifications();
                
                Platform.runLater(() -> {
                	
                	notificationListView.setItems(FXCollections.observableArrayList(notiList));
          
                });
            } catch (Exception e) {
               
            }
        }).start();
        } else {
        	new Thread(() -> {
                try {
                	List<Notification> notiList = ApiService.getAllNotifications().stream()
                		    .filter(noti -> noti.getSenderId().equals(user.getUsername()))
                		    .collect(Collectors.toList());
                	
                    
                    Platform.runLater(() -> {
                    	
                    	notificationListView.setItems(FXCollections.observableArrayList(notiList));
              
                    });
                } catch (Exception e) {
                   
                }
            }).start();
        }

        
    }
       
        
        
        public void openNewNotification() {
            modalPane.setVisible(true);
        }

        public void cancelNewNotification() {
            titleField.clear();
            contentField.clear();
            modalPane.setVisible(false);
        }

        @FXML
        private void sendNewNotification() {
            String title = titleField.getText().trim();
            String content = contentField.getText().trim();
            String recipient = toField.getText().trim();
            boolean isSent = false;
            
            try {
                // Kiểm tra dữ liệu bắt buộc
                if (title.isEmpty() || content.isEmpty()) {
                    showError("Tiêu đề và nội dung không được để trống.");
                    return;
                }

                if (sendToAllRadio.isSelected()) {
                    // Gửi thông báo đến tất cả
                    isSent = ApiService.sendNotification(user.getUsername(), title, content,"GENERAL", "");
                    
                } else if (sendToOneRadio.isSelected()) {
                    
                    if (recipient.isEmpty()) {
                        showError("Vui lòng nhập tên người nhận.");
                        return;
                    }

                    // Gửi thông báo cá nhân
                    isSent = ApiService.sendNotification(user.getUsername(), title, content,"SPECIFIC", recipient);
                    
                } else {
                    showError("Vui lòng chọn chế độ gửi thông báo.");
                }
                if(isSent) {
                	showSuccess("Đã gửi thông báo đến " + recipient + ".");
                	onTabSent();
                } else {
                	showError("Vui lòng gửi lại");
                }
                // làm sạch dữ liệu sau khi gửi
                resetForm();
                modalPane.setVisible(false);

            } catch (Exception e) {
                e.printStackTrace();
                showError("Đã xảy ra lỗi khi gửi thông báo. Vui lòng thử lại.");
            }
        }
        
        private void showError(String message) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        }

        private void showSuccess(String message) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thành công");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        }

        private void resetForm() {
            titleField.clear();
            contentField.clear();
            toField.clear();
            ccField.clear();
            bccField.clear();
        }

}

