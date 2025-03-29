module IT3180_2024II_SE_08 {
    requires java.net.http;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;
    requires com.fasterxml.jackson.databind;
    requires java.base;

    opens app.models to com.fasterxml.jackson.databind;
    opens app.controllers.admin to javafx.fxml;
    opens app.views.admin to javafx.fxml;
    opens app.controllers.resident to javafx.fxml;
    opens app.views.resident to javafx.fxml;

    exports app;
}
