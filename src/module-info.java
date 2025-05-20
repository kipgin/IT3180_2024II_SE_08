module IT3180_2024II_SE_08 {
    requires java.net.http;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;
    
    
 
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.annotation;
    
    requires java.base;
	requires java.desktop;

    opens app.models to com.fasterxml.jackson.databind;
    opens app.controllers.admin to javafx.fxml;
    opens app.views.admin to javafx.fxml;
    opens app.controllers.resident to javafx.fxml;
    opens app.views.resident to javafx.fxml;
    opens app.assets.img to javafx.fxml, javafx.graphics;

    opens app.services to com.fasterxml.jackson.databind;

    exports app;
}
