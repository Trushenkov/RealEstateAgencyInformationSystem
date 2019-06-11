package ru.tds.realestateagency.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.tds.realestateagency.DatabaseHandler;
import ru.tds.realestateagency.Helper;
import ru.tds.realestateagency.entities.User;

import java.sql.Connection;
import java.util.prefs.Preferences;

/**
 * Класс-контроллер для обработки событий на экране "Авторизация"
 *
 * @author Трушенков Дмитрий
 */
public class AuthorizationController {

    private static final String MAIN_SCREEN_PATH = "/ru/tds/realestateagency/views/main.fxml";
    @FXML
    private TextField loginTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private Button authButton;

    @FXML
    public void initialize() {

    }

    /**
     * Метод для авторизации пользователя
     */
    public void authorizationUser() {
        //получаем данные из текстовых полей, удаляем лишние пробелы
        String loginText = loginTextField.getText().trim();
        String passwordText = passwordTextField.getText().trim();

        //проверяем заполнены ли поля: логин и пароль
        if (!loginText.isEmpty() && !passwordText.isEmpty()) {

            User user = new User(loginText, passwordText);

            Preferences preferences = Preferences.userRoot();
            preferences.put("user_login", user.getLogin());
            preferences.put("user_password", user.getPassword());

            //создаем соединение с БД, используя введенный логин и пароль
            Connection connection = new DatabaseHandler().createDbConnection();

            //проверка сущетсвует ли соединение с базой данных
            if (connection != null) {
                //открываем уведомление об успешно выполненной авторизации
                showAlert("Авторизация выполнена успешно",
                        "Для продолжения, нажмите ОК",
                        Alert.AlertType.INFORMATION);

                this.authButton.getScene().getWindow().hide();
                //после успешной авторизации, открываем главный экран приложения
                Helper.openNewScreen(MAIN_SCREEN_PATH);
            }
        } else {
            //открываем уведомление об ошибке авторизации
            showAlert("Ошибка при авторизации",
                    "Необходимо заполнить поля логин и пароль",
                    Alert.AlertType.ERROR);
        }
    }

    /**
     * Метод для создания и открытия уведомления
     *
     * @param headerText  текст заголовка
     * @param contentText текст основого содержимого окна
     */
    private void showAlert(String headerText, String contentText, Alert.AlertType alertType) {
        //создание уведомления
        Alert alert = new Alert(alertType);

        //установка названия окна в зависимости от типа модального окна (ERROR или INFORMATION)
        if (alertType == Alert.AlertType.ERROR) {
            //установка названия окна
            alert.setTitle("Уведомление об ошибке");
        } else if (alertType == Alert.AlertType.INFORMATION) {
            //установка названия окна
            alert.setTitle("Уведомление об успешном выполнении");
        }
        //установка заголовка окна
        alert.setHeaderText(headerText);
        //установка текста содержимого окна
        alert.setContentText(contentText);
        //размер окна не изменяемый
        alert.setResizable(false);
        //определене родительского окна
        alert.initOwner(this.authButton.getScene().getWindow());
        //установка типа модального окна
        alert.initModality(Modality.WINDOW_MODAL);

        if (alert.getAlertType().equals(Alert.AlertType.ERROR)) {
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(getClass().getResource("/ru/tds/realestateagency/images/warning.png").toString()));
        } else {
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(getClass().getResource("/ru/tds/realestateagency/images/success.png").toString()));
        }

        //отображаем уведомление
        alert.showAndWait();
    }

}
