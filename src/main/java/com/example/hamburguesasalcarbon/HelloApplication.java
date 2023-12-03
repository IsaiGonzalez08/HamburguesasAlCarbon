package com.example.hamburguesasalcarbon;


import javafx.fxml.FXMLLoader;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import javafx.scene.layout.AnchorPane;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameScene;


public class HelloApplication extends GameApplication {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    protected void initSettings(GameSettings settings) {

        settings.setWidth(949);
        settings.setHeight(623);
        settings.setTitle("Hamburguesas al Carbón Isaí");
    }

    @Override
    protected void initGame(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            AnchorPane root = loader.load();
            FXGL.getGameScene().addUINode(root);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        getGameScene().setBackgroundRepeat("Background.png");
    }
}