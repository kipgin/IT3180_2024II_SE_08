package app;

import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
    	try {
			Parent root = FXMLLoader.load(getClass().getResource("views/admin/login.fxml"));
			
			Scene scene = new Scene(root,450,450);
			scene.getStylesheets().add(getClass().getResource("assets/css/admin/style.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setX(500);
			primaryStage.setY(150);
			primaryStage.show();
			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/app/assets/img/logo.png")));
		} catch(Exception e) {
			e.printStackTrace();
		}
    }

    public static void main(String[] args) {
        launch(args);
    }
}
