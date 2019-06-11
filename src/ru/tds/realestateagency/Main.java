package ru.tds.realestateagency;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

/**
 * Класс для запуска приложения
 *
 * @author Трушенков Дмитрий
 */
public class Main extends Application {

    private static final String START_SCREEN_PATH = "views/authorization.fxml";
    private static final String CSS_FILE_PATH = "ru/tds/realestateagency/css/fullpackstyling.css";
    private static final String TITLE = "Информационная система для агентства недвижимости";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource(START_SCREEN_PATH));
        primaryStage.setTitle(TITLE);
        primaryStage.setScene(new Scene(root));
        primaryStage.getScene().getStylesheets().add(CSS_FILE_PATH);
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint("");
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.keyCombination("ALT+F4"));
        primaryStage.show();
    }
}
