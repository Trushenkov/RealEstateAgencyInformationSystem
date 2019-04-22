package ru.tds.realestateagency;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

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

    public static int min(int n1, int n2, int n3) {
        return Math.min(Math.min(n1, n2), n3);
    }

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

    /**
     * Метод для открытия модального окна для уведмоления пользователя об ошибке
     *
     * @param path путь до файла разметки модального окна
     */
    public void showModalWindow(String path, ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(path));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Уведомление об ошибке");
        stage.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setResizable(false);
        stage.show();
    }
    
}
