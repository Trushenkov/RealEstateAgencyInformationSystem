package ru.tds.realestateagency;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Класс для запуска приложения
 *
 * @author Трушенков Дмитрий
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("views/main.fxml"));
        primaryStage.setTitle("Информационная система для агентства недвижимости");
        primaryStage.setScene(new Scene(root));
        primaryStage.getScene().getStylesheets().add("ru/tds/realestateagency/css/fullpackstyling.css");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
