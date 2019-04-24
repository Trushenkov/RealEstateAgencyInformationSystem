package ru.tds.realestateagency.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.tds.realestateagency.DatabaseHandler;
import ru.tds.realestateagency.Helper;
import ru.tds.realestateagency.entities.Client;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Predicate;

/**
 * Класс-контроллер для обработки событий на экране "Работа с клиентами"
 *
 * @author Трушенков Дмитрий
 */
public class ClientsScreenController {

    //константы для работы с таблицей `clients`
    private static final String CLIENT_TABLE = "clients";
    private static final String CLIENT_LAST_NAME = "lastName";
    private static final String CLIENT_FIRST_NAME = "firstName";
    private static final String CLIENT_MIDDLE_NAME = "middleName";
    private static final String CLIENT_PHONE_NUMBER = "phoneNumber";
    private static final String CLIENT_EMAIL = "email";
    private static final String CLIENT_ID = "id";


    //Элементы разметки интерфейса
    @FXML
    private Button createBtn;
    @FXML
    private Button updateBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private Button goBackBtn;
    @FXML
    private TableView<Client> tableClients;
    @FXML
    private TableColumn<Client, String> tableColumnLastName;
    @FXML
    private TableColumn<Client, String> tableColumnFirstName;
    @FXML
    private TableColumn<Client, String> tableColumnMiddleName;
    @FXML
    private TableColumn<Client, Integer> tableColumnPhoneNumber;
    @FXML
    private TableColumn<Client, String> tableColumnEmail;
    @FXML
    private TextField tfSearch;
    @FXML
    private TextField tfLastName;
    @FXML
    private TextField tfFirstName;
    @FXML
    private TextField tfMiddleName;
    @FXML
    private TextField tfPhoneNumber;
    @FXML
    private TextField tfEmail;

    private int idClient; // для хранения ID клиента из базы

    /**
     * Метод для создания и открытия модального окна
     *
     * @param headerText  текст заголовка
     * @param contentText текст основого содержимого окна
     */
    private void showModalWindow(String headerText, String contentText, Alert.AlertType alertType) {
        //создание уведомления
        Alert alert = new Alert(alertType);

        //установка названия окна в зависимости от типа модального окна (ERROR или INFORMATION)
        if (alertType == AlertType.ERROR) {
            //установка названия окна
            alert.setTitle("Уведомление об ошибке");
        } else if (alertType == AlertType.INFORMATION) {
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
        alert.initOwner(this.tableClients.getScene().getWindow());
        //установка типа модального окна
        alert.initModality(Modality.WINDOW_MODAL);

        if (alert.getAlertType().equals(AlertType.ERROR)) {
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(getClass().getResource("/ru/tds/realestateagency/images/warning.png").toString()));
        } else {
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(getClass().getResource("/ru/tds/realestateagency/images/success.png").toString()));
        }

        //отображаем окно
        alert.showAndWait();
    }

    @FXML
    void initialize() {

        updateBtn.setDisable(true);
        deleteBtn.setDisable(true);

        //определение колонок таблицы с соответствующими полями объекта "Клиент"
        tableColumnLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        tableColumnFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        tableColumnMiddleName.setCellValueFactory(new PropertyValueFactory<>("middleName"));
        tableColumnPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        tableColumnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        //заполняем таблицу данным из БД
        tableClients.setItems(createListClients(getClientsTableContent()));

        //Заносим данные выделенного объекта в текстовые поля при клике на объект в таблице
        tableClients.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {

                createBtn.setDisable(true);
                updateBtn.setDisable(false);
                deleteBtn.setDisable(false);

                try {

                    String getIdClientWithEmail =
                            "SELECT " + CLIENT_ID +
                                    " FROM " + CLIENT_TABLE +
                                    " WHERE " + CLIENT_EMAIL + " = ?";

                    //SQL запрос для получения ID клиента из таблицы в базе
                    String getIdClientWithPhone =
                            "SELECT " + CLIENT_ID +
                                    " FROM " + CLIENT_TABLE +
                                    " WHERE " + CLIENT_PHONE_NUMBER + " = ?";

                    PreparedStatement statement = null;

                    //проверка на пустое значение поля электронная почта
                    if (tableClients.getSelectionModel().getSelectedItem().getEmail() != null) {
                        statement = new DatabaseHandler().createDbConnection().prepareStatement(getIdClientWithEmail);

                        //установка значений для вставки в запрос
                        statement.setString(1, tableClients.getSelectionModel().getSelectedItem().getEmail());
                    }

                    //проверка на пустое значение поля номер телефона
                    if (tableClients.getSelectionModel().getSelectedItem().getPhoneNumber() != null) {
                        statement = new DatabaseHandler().createDbConnection().prepareStatement(getIdClientWithPhone);

                        //установка значений для вставки в запрос
                        statement.setString(1, tableClients.getSelectionModel().getSelectedItem().getPhoneNumber());
                    }

                    //выполнение запроса и сохранение значений в resultSet
                    ResultSet resultSet = statement.executeQuery();

                    while (resultSet.next()) {
                        //получаем id из набора данных, полученного из базы
                        idClient = resultSet.getInt("id");
                        System.out.println(idClient);
                    }

                } catch (SQLException e) {
                    showModalWindow(
                            "Ошибка получения ID клиента из базы",
                            "Данные из базы не были получены!",
                            AlertType.ERROR);
                }

                //устанавливаем значения выделенного объекта в текстовые поля
                tfLastName.setText(tableClients.getSelectionModel().getSelectedItem().getLastName());
                tfFirstName.setText(tableClients.getSelectionModel().getSelectedItem().getFirstName());
                tfMiddleName.setText(tableClients.getSelectionModel().getSelectedItem().getMiddleName());
                tfPhoneNumber.setText(String.valueOf(tableClients.getSelectionModel().getSelectedItem().getPhoneNumber()));
                tfEmail.setText(String.valueOf(tableClients.getSelectionModel().getSelectedItem().getEmail()));
            } else {
                createBtn.setDisable(false);
                updateBtn.setDisable(true);
                deleteBtn.setDisable(true);
            }
        });

        //Поиск клиента по ФИО
        findClientByFullName();

    }

    /**
     * Метод для предоставления возможности поиска клиента по ФИО
     */
    private void findClientByFullName() {
        FilteredList<Client> filteredList = new FilteredList<>(createListClients(getClientsTableContent()));
        tfSearch.textProperty().addListener((observable, oldValue, newValue) -> {

            filteredList.setPredicate((Predicate<? super Client>) client -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                ObservableList<Client> list = createListClients(getClientsTableContent());

                for (int i = 0; i < list.size(); i++) {
                    if (Helper.levenstain(newValue.toLowerCase(), client.getLastName().toLowerCase()) <= 3) {
                        return true;
                    }
                    if (Helper.levenstain(newValue.toLowerCase(), client.getFirstName().toLowerCase()) <= 3) {
                        return true;
                    }
                    if (Helper.levenstain(newValue.toLowerCase(), client.getMiddleName().toLowerCase()) <= 3) {
                        return true;
                    }
                }

                return false;
            });
        });

        SortedList<Client> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tableClients.comparatorProperty());
        tableClients.setItems(sortedList);
    }

    /**
     * Метод для обработки события при нажатии на кнопку "Создать"
     *
     * @param actionEvent нажатие на кнопку
     */
    public void createBtnClicked(ActionEvent actionEvent) {
        //Создание объекта "Клиент"
        Client client = new Client();

        //проверка на то пустые ли поля "Номер телефона" и "Электронная почта"
        if (!tfPhoneNumber.getText().isEmpty() || !tfEmail.getText().isEmpty()) {
            //установка значений для объекта
            client.setLastName(tfLastName.getText());
            client.setFirstName(tfFirstName.getText());
            client.setMiddleName(tfMiddleName.getText());

            //проверяем не пустое ли поле "Номер телефона"
            if (!tfPhoneNumber.getText().isEmpty()) {
                //установка значения "номер телефона" для объекта
                client.setPhoneNumber(tfPhoneNumber.getText());
            }

            //проверяем не пустое ли поле "Электронная почта"
            if (!tfEmail.getText().isEmpty()) {
                //установка значения "электронная почта" для объекта
                client.setEmail(tfEmail.getText());
            }

            System.out.println(client);

            //SQL запрос для добавления нового клиента в базу данных
            String insertClient = "INSERT INTO " +
                    CLIENT_TABLE + "(" +
                    CLIENT_LAST_NAME + ", " +
                    CLIENT_FIRST_NAME + ", " +
                    CLIENT_MIDDLE_NAME + ", " +
                    CLIENT_PHONE_NUMBER + ", " +
                    CLIENT_EMAIL + ")" +
                    " VALUES (?,?,?,?,?);";

            try {
                PreparedStatement addClientStatement = new DatabaseHandler().createDbConnection().prepareStatement(insertClient);
                //установка значений для вставки в запрос
                addClientStatement.setString(1, client.getLastName());
                addClientStatement.setString(2, client.getFirstName());
                addClientStatement.setString(3, client.getMiddleName());
                addClientStatement.setString(4, client.getPhoneNumber());
                addClientStatement.setString(5, client.getEmail());
                //выполнение запроса
                addClientStatement.executeUpdate();

                //обновление таблицы после выполнения запроса
                tableClients.setItems(createListClients(getClientsTableContent()));

                //открываем диалоговое окно для уведомления об успешном добавлении
                showModalWindow(
                        "Операция успешно выполнена",
                        "Новый клиент успешно добавлен!",
                        AlertType.INFORMATION);

                //обнуляем текстовые поля после добавления клиента в базу
                clearTextFields();

                //Поиск клиента по ФИО
                findClientByFullName();

            } catch (SQLException e) {
                //открываем диалоговое окно для уведомления об ошибке
                showModalWindow(
                        "Ошибка добавления нового клиента",
                        "Клиент не был добавлен в базу. Повторите еще раз",
                        AlertType.ERROR);
            }
        } else {
            //открываем диалоговое окно для уведомления об ошибке
            showModalWindow(
                    "Ошибка добавление нового клиента",
                    "Поля номер телефона и элеткронная почта не обязательны к заполнению, но одно из них должно быть указано.",
                    AlertType.ERROR
            );
        }

    }

    /**
     * Метод для обнуления текстовых полей
     */
    private void clearTextFields() {
        tfLastName.setText("");
        tfFirstName.setText("");
        tfMiddleName.setText("");
        tfPhoneNumber.setText("");
        tfEmail.setText("");
    }

    /**
     * Метод для обработки события при нажатии на кнопку "Обновить"
     *
     * @param actionEvent нажатие на кнопку
     */
    public void updateBtnClicked(ActionEvent actionEvent) {

        //SQL запрос для обновления клиента
        String update = "UPDATE " + CLIENT_TABLE + " SET " + CLIENT_LAST_NAME + "=?,"
                + CLIENT_FIRST_NAME + "=?,"
                + CLIENT_MIDDLE_NAME + "=?,"
                + CLIENT_PHONE_NUMBER + "=?,"
                + CLIENT_EMAIL + "=? " +
                "WHERE " + CLIENT_ID + "=?";

        try {
            PreparedStatement updateClientStatement = new DatabaseHandler().createDbConnection().prepareStatement(update);

            //установка значений для вставки в запрос
            updateClientStatement.setString(1, tfLastName.getText());
            updateClientStatement.setString(2, tfFirstName.getText());
            updateClientStatement.setString(3, tfMiddleName.getText());
            updateClientStatement.setString(4, tfPhoneNumber.getText());
            updateClientStatement.setString(5, tfEmail.getText());
            updateClientStatement.setInt(6, idClient);
            //выполнение SQL запроса
            updateClientStatement.executeUpdate();
            //обновление таблицы
            tableClients.setItems(createListClients(getClientsTableContent()));

            //открываем диалоговое окно для уведомления об успешном обновлении
            showModalWindow(
                    "Операция успешно выполнена",
                    "Обновление клиента выполнено успешно!",
                    AlertType.INFORMATION);

            //Обнуляем текстовые поля после обновления клиента
            clearTextFields();

            //Поиск клиента по ФИО
            findClientByFullName();

        } catch (SQLException e) {
            //открываем диалоговое окно для уведомления об ошибке
            showModalWindow(
                    "Ошибка обновления клиента",
                    "Обновление клиента не выполнено! Повторите еще раз",
                    AlertType.ERROR);
        }


    }

    /**
     * Метод для обработки события при нажатии на кнопку "Удалить"
     *
     * @param actionEvent нажатие на кнопку
     */
    public void deleteBtnClicked(ActionEvent actionEvent) {
        //SQL запрос на удаление клиента
        String deleteClient = "DELETE FROM " + CLIENT_TABLE + " WHERE " + CLIENT_ID + "=?";

        try {
            PreparedStatement preparedStatement = new DatabaseHandler().createDbConnection().prepareStatement(deleteClient);

            //установка значений для вставки в запрос
            preparedStatement.setInt(1, idClient);
            //выполнение запроса на удаление
            preparedStatement.executeUpdate();
            //обновление таблицы после удаления
            tableClients.setItems(createListClients(getClientsTableContent()));


            //открываем диалоговое окно для уведомления об успешном удалении
            showModalWindow(
                    "Операция успешно выполнена",
                    "Удаление клиента выполнено успешно!",
                    AlertType.INFORMATION);

            //обнуляем текстовые поля после удаления клиента
            clearTextFields();

            //Поиск клиента по ФИО
            findClientByFullName();

        } catch (SQLException e) {
            //открываем диалоговое окно для уведомления об ошибке
            showModalWindow(
                    "Ошибка удаления клиента",
                    "Возникла ошибка при удалении клиента с ID = " + idClient,
                    AlertType.ERROR
            );
        }

    }

    /**
     * Метод для обработки события при нажатии на кнопку "Назад"
     *
     * @param actionEvent нажатие на кнопку
     */
    public void goBackBtnClicked(ActionEvent actionEvent) {
        ((Node) actionEvent.getSource()).getScene().getWindow().hide();
        //переход на главный экран
        Helper.changeScreen("/ru/tds/realestateagency/views/mainpageScreen.fxml");
    }

    /**
     * Метод для получения содержимого таблицы клиентов из базы данных
     *
     * @return ResultSet - набор данных из таблицы
     */
    private ResultSet getClientsTableContent() {
        //SQL запрос на выбор всех данных из таблицы `clients`
        String selectClients = "SELECT * FROM " + CLIENT_TABLE;

        ResultSet resultSet = null;
        try {
            PreparedStatement ps = new DatabaseHandler().createDbConnection().prepareStatement(selectClients);
            //выполняем запрос и сохраняем полученные значения в resultSet
            resultSet = ps.executeQuery();
        } catch (SQLException e) {
            //открываем диалоговое окно для уведомления об ошибке
            showModalWindow(
                    "Ошибка получения данных из таблицы клиентов",
                    "Данные не получены из базы. Проверьте подключение к базе!",
                    AlertType.ERROR);
        }

        return resultSet;
    }

    /**
     * Метод для формирования ObservableList из набора данных
     *
     * @param resultSet набор данных из таблицы клиентов
     * @return ObservableList с объектами типа Client
     */
    private ObservableList<Client> createListClients(ResultSet resultSet) {
        //создаем список клиентов
        ObservableList<Client> list = FXCollections.observableArrayList();

        try {
            while (resultSet.next()) {
                //создаем нового клиента
                Client client = new Client(
                        resultSet.getString("lastName"),
                        resultSet.getString("firstName"),
                        resultSet.getString("middleName"),
                        resultSet.getString("phoneNumber"),
                        resultSet.getString("email")
                );
                //добавляем клиента в список
                list.add(client);
            }
        } catch (SQLException e) {
            //открываем диалоговое окно для уведомления об ошибке
            showModalWindow(
                    "Ошибка формирования коллекции клиентов",
                    "Возникла ошибка при формировании списка клиентов из набора данных, полученных из базы",
                    AlertType.ERROR);
        }
        return list;
    }
}
