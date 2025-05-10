package app.models;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class NotificationCell extends ListCell<Notification> {
    private HBox root;
    private Circle statusDot;
    private ImageView starIcon;
    private VBox contentBox;
    private Label titleLabel;
    private Label contentLabel;
    private Label timeLabel;
    private Button deleteButton;
    private boolean expanded = false;

    public NotificationCell() {
    	
    	setBackground(null); 
    	setStyle("-fx-background-color: #eeeeee;"); 
        // Chấm tròn trạng thái
        statusDot = new Circle(3);
        statusDot.setFill(Color.LIMEGREEN);

        // Ngôi sao
        starIcon = new ImageView();
        starIcon.setFitWidth(20);
        starIcon.setFitHeight(20);
        StackPane starWrapper = new StackPane(starIcon);
        starWrapper.setPadding(new Insets(3));
        starWrapper.setOnMouseClicked(event -> {
            Notification notification = getItem();
            if (notification != null) {
                boolean newImportance = !notification.isImportant();
                notification.setImportant(newImportance);

                String starPath = newImportance
                    ? "/app/assets/img/star_filled.png"
                    : "/app/assets/img/star_empty.png";
                starIcon.setImage(new Image(getClass().getResourceAsStream(starPath)));

                event.consume();
            }
        });

        // Tiêu đề
        titleLabel = new Label();
        titleLabel.getStyleClass().add("notification-title");

        // Nội dung
        contentLabel = new Label();
        contentLabel.setWrapText(true);
        contentLabel.setMaxHeight(30);
        contentLabel.setMaxWidth(950);
        contentLabel.getStyleClass().add("notification-content");

        // Thời gian (căn phải)
        timeLabel = new Label();
        timeLabel.getStyleClass().add("notification-time");
        HBox timeBox = new HBox(timeLabel);
        timeBox.setAlignment(Pos.CENTER);

        // Box nội dung và thời gian
        contentBox = new VBox(4, titleLabel, contentLabel);
        contentBox.setPrefWidth(950);
        HBox centerBox = new HBox(4, contentBox, timeBox);
        centerBox.setPrefWidth(1120);

        // Nút xoá
        deleteButton = new Button();
        ImageView deleteIcon = new ImageView(new Image(getClass().getResource("/app/assets/img/delete.png").toString()));
        deleteIcon.setFitHeight(20);
        deleteIcon.setFitWidth(20);
        deleteButton.setGraphic(deleteIcon);
        
        deleteButton.getStyleClass().add("notification-delete");
        deleteButton.setOnAction(e -> getListView().getItems().remove(getItem()));

        // Root layout
        root = new HBox(10, statusDot, starWrapper, centerBox, deleteButton);
        root.getStyleClass().add("notification-card");
        root.setPadding(new Insets(10));
        root.setAlignment(Pos.CENTER_LEFT);

        // Sự kiện click
        root.setOnMouseClicked(event -> {
            expanded = !expanded;
            contentLabel.setMaxHeight(expanded ? Double.MAX_VALUE : 40);

            // Cập nhật trạng thái đọc khi mở
            if (!getItem().isRead()) {
                getItem().setRead(true);
                statusDot.setFill(Color.GRAY);
                updateStyleClasses();
            }

            // Reset tất cả các cell khác
            for (ListCell<Notification> cell : getListView().getItems().stream()
                    .map(item -> (NotificationCell) getListView().lookupAll(".list-cell")
                            .stream()
                            .filter(c -> ((ListCell<?>) c).getItem() == item)
                            .findFirst()
                            .orElse(null))
                    .toList()) {
                if (cell != null && cell != this) {
                    cell.getStyleClass().remove("notification-opened");
                }
            }

            // Đánh dấu cell này là mở
            root.getStyleClass().removeAll("notification-read", "notification-unread");
            root.getStyleClass().add("notification-opened");
        });
    }

    @Override
    protected void updateItem(Notification item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setGraphic(null);
        } else {
            titleLabel.setText(item.getTitle());
            contentLabel.setText(item.getContent());
            timeLabel.setText(formatTime(item.getCreatedAt()));
            contentLabel.setMaxHeight(40);
            expanded = false;

            // Cập nhật trạng thái sao
            String starPath = item.isImportant()
                    ? "/app/assets/img/star_filled.png"
                    : "/app/assets/img/star_empty.png";
            starIcon.setImage(new Image(getClass().getResourceAsStream(starPath)));

            updateStyleClasses();
            setGraphic(root);
            setFocusTraversable(false); // Loại bỏ viền xanh khi focus
        }
    }

    private void updateStyleClasses() {
        root.getStyleClass().removeAll("notification-unread", "notification-read", "notification-opened");

        if (getItem().isRead()) {
            root.getStyleClass().add("notification-read");
            statusDot.setFill(Color.GRAY);
        } else {
            root.getStyleClass().add("notification-unread");
            statusDot.setFill(Color.LIMEGREEN);
        }
    }

    private String formatTime(String isoTime) {
        return isoTime.replace("T", " ").substring(0, 16);
    }
}
