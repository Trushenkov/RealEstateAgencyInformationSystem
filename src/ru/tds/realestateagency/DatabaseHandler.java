package ru.tds.realestateagency;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Класс, содержащий методы для работы с базой данных
 *
 * @author Трушенков Дмитрий
 */
public class DatabaseHandler {

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

    private Connection connection;

    /**
     * Метод для создания соединения с базой данных
     *
     * @return соединение с базой данных
     */
    public Connection createDbConnection() {
        String connectionUrl = "jdbc:mysql://" + Const.DB_HOST + ":" + Const.DB_PORT+ "/" + Const.DB_NAME+ "?serverTimezone=UTC";
        try {
            connection = DriverManager.getConnection(connectionUrl, Const.DB_USER, Const.DB_HOME_PASSWORD);
        } catch (SQLException e) {
            //оповещение об ошибке при создании соединения с БД
//            new Helper().changeScreen("/ru/tds/realestateagency/views/alerts/errorCreateDBConnection.fxml");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Уведомление об ошибке");
            alert.setHeaderText("Ошибка соединения с базой данных");
            alert.setContentText("Соединение с базой данных не было установлено. Проверьте настройки подключения к базе.");
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(this.getClass().getResource("/ru/tds/realestateagency/images/warning.png").toString()));
            stage.getScene().getStylesheets().add("ru/tds/realestateagency/css/style.css");
            alert.setResizable(false);

            alert.showAndWait();

        }

        return connection;
    }



}
