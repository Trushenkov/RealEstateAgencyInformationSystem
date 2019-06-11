package ru.tds.realestateagency;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.prefs.Preferences;

/**
 * Класс, содержащий методы для работы с базой данных
 *
 * @author Трушенков Дмитрий
 */
public class DatabaseHandler {

    private Connection connection;

    /**
     * Метод для создания соединения с базой данных
     *
     * @return соединение с базой данных
     */
    public Connection createDbConnection() {

        Preferences preferences = Preferences.userRoot();
        String login = preferences.get("user_login", "");
        String password = preferences.get("user_password", "");

        String connectionUrl = "jdbc:mysql://" + Const.DB_HOME_HOST + ":" + Const.DB_HOME_PORT + "/" + Const.DB_HOME_NAME + "?serverTimezone=UTC";
        try {
            connection = DriverManager.getConnection(connectionUrl, login, password);
        } catch (Exception e) {
            //оповещение об ошибке при создании соединения с БД
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Уведомление об ошибке");
            alert.setHeaderText("Ошибка соединения с базой данных");
            alert.setContentText("Соединение с базой данных не было установлено. Проверьте настройки подключения к базе.");
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(this.getClass().getResource("/ru/tds/realestateagency/images/warning.png").toString()));
            stage.getScene().getStylesheets().add("ru/tds/realestateagency/css/fullpackstyling.css");
            alert.setResizable(false);

            alert.showAndWait();
        }

        return connection;
    }

    /**
     * Класс для хранения констант, необходимых для работы с базой данных
     *
     * @author Трушенков Дмитрий
     */
    class Const {
        //место практики
        static final String DB_HOST = "192.168.3.171";
        static final int DB_PORT = 3306;
        static final String DB_NAME = "realestateagency";
        static final String DB_USER = "test";
        static final String DB_PASSWORD = "w4r=ktp>qrqG";

        //дом
        static final String DB_HOME_HOST = "localhost";
        static final int DB_HOME_PORT = 3306;
        static final String DB_HOME_NAME = "realestateagency";
        static final String DB_HOME_USER = "root";
        static final String DB_HOME_PASSWORD = "12345";
    }


}
