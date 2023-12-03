module com.example.hamburguesasalcarbon {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;

    opens com.example.hamburguesasalcarbon to javafx.fxml;
    opens assets.textures;
    exports com.example.hamburguesasalcarbon;
    exports com.example.hamburguesasalcarbon.Controllers;
    opens com.example.hamburguesasalcarbon.Controllers to javafx.fxml;
    exports com.example.hamburguesasalcarbon.factory;
}