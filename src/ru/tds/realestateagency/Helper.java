package ru.tds.realestateagency;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Класс, содержащий вспомогательные методы
 *
 * @author Трушенков Дмитрий
 */
public class Helper {

    /**
     * Метод для перехода на новый экран
     *
     * @param pathToFxml путь до файла разметки нового окна
     */
    public void changeScreen(String pathToFxml) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(pathToFxml));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Scene scene = new Scene(root, 1100, 600);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Информационная система для агентства недвижимости");
        stage.getScene().getStylesheets().add("ru/tds/realestateagency/css/style.css");
        stage.show();
    }
}
