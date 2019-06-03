package ru.tds.realestateagency;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Класс, содержащий вспомогательные методы
 *
 * @author Трушенков Дмитрий
 */
public class Helper {

    /**
     * Метод для поиска расстояния Левенштейна
     *
     * @param str1 первая строка
     * @param str2 вторая строка
     * @return расстояние Левенштейна
     */
    public static int levenstain(String str1, String str2) {
        int[] Di_1 = new int[str2.length() + 1];
        int[] Di = new int[str2.length() + 1];

        for (int j = 0; j <= str2.length(); j++) {
            Di[j] = j; // (i == 0)
        }

        for (int i = 1; i <= str1.length(); i++) {
            System.arraycopy(Di, 0, Di_1, 0, Di_1.length);

            Di[0] = i; // (j == 0)
            for (int j = 1; j <= str2.length(); j++) {
                int cost = (str1.charAt(i - 1) != str2.charAt(j - 1)) ? 1 : 0;
                Di[j] = min(
                        Di_1[j] + 1,
                        Di[j - 1] + 1,
                        Di_1[j - 1] + cost
                );
            }
        }

        return Di[Di.length - 1];
    }

    private static int min(int n1, int n2, int n3) {
        return Math.min(Math.min(n1, n2), n3);
    }

    /**
     * Метод для перехода на новый экран
     *
     * @param path путь до файла разметки нового окна
     */
    public static void openNewScreen(String path) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Helper.class.getResource(path));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Scene scene = new Scene(root);
        scene.getStylesheets().add("ru/tds/realestateagency/css/fullpackstyling.css");
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Информационная система для агентства недвижимости");
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(KeyCombination.keyCombination("ALT + F4"));

        stage.show();
    }

}
