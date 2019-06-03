package ru.tds.realestateagency;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

/**
 * Класс для запуска приложения
 *
 * @author Трушенков Дмитрий
 */
public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("views/main.fxml"));
        primaryStage.setTitle("Информационная система для агентства недвижимости");
        primaryStage.setScene(new Scene(root));
        primaryStage.getScene().getStylesheets().add("ru/tds/realestateagency/css/fullpackstyling.css");
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint("");
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.keyCombination("ALT+F4"));
        primaryStage.show();
    }
}
