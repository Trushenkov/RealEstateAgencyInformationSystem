package ru.tds.realestateagency.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.tds.realestateagency.DatabaseHandler;
import ru.tds.realestateagency.Helper;
import ru.tds.realestateagency.entities.Client;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
    private AnchorPane mainPane;
    @FXML
    private Label numberOfClientsLabel;
    @FXML
    private Label clientsWithNeedsLabel;
    @FXML
    private Label clientsWithOffersLabel;
    @FXML
    private Button createBtn;
    @FXML
    private Button updateBtn;
    @FXML
    private Button deleteBtn;
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

    private ArrayList<Integer> idClientsFromDatabase;
    private int idSelectedClient;// для хранения ID клиента из базы
    private ObservableList<Client> listClients;

    @FXML
    void initialize() {

        //Обработка нажатия на главную панель окна, обнуление текстовых полей при нажатии и отмена выбора строки в таблице
        mainPane.setOnMousePressed(event -> {
            idSelectedClient = 0;
            clearTextFields();
            tableClients.getSelectionModel().clearSelection();
            updateBtn.setDisable(true);
        });

        updateBtn.setDisable(true);
        deleteBtn.setDisable(true);

        //определение колонок таблицы с соответствующими полями объекта "Клиент"
        tableColumnLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        tableColumnFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        tableColumnMiddleName.setCellValueFactory(new PropertyValueFactory<>("middleName"));
        tableColumnPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        tableColumnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        listClients = createListClients(getClientsTableContent());

        listClients.addListener((ListChangeListener<Client>) c -> {
            updateTableContent();
            numberOfClientsLabel.setText(String.valueOf(listClients.size()));
        });


        //подсказки при наведении на поля в таблице
        createTableTooltip();

        System.out.println("Количество клиентов " + listClients.size());

        //заполняем таблицу данным из БД
        tableClients.setItems(listClients);
        //считаем и устанавливаем количество клиентов
        numberOfClientsLabel.setText(String.valueOf(listClients.size()));


        //Заносим данные выделенного объекта в текстовые поля при клике на объект в таблице
        tableClients.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedClient) -> {

            if (selectedClient != null) {

                idSelectedClient = idClientsFromDatabase.get(tableClients.getSelectionModel().getSelectedIndex());
                System.out.println("Выбран объект, у которого id в базе = " + idSelectedClient);
                System.out.println(idClientsFromDatabase);

                //устанавливаем значения выделенного объекта в текстовые поля
                tfLastName.setText(tableClients.getSelectionModel().getSelectedItem().getLastName());
                tfFirstName.setText(tableClients.getSelectionModel().getSelectedItem().getFirstName());
                tfMiddleName.setText(tableClients.getSelectionModel().getSelectedItem().getMiddleName());
                tfPhoneNumber.setText(String.valueOf(tableClients.getSelectionModel().getSelectedItem().getPhoneNumber()));
                tfEmail.setText(String.valueOf(tableClients.getSelectionModel().getSelectedItem().getEmail()));

                updateBtn.setDisable(true);
                createBtn.setDisable(true);
                deleteBtn.setDisable(false);

                //Слушатели для текстовых полей для проверки на изменение какого либо значения
                // и предоставление пользователю возможности для обновления клиента

                tfLastName.textProperty().addListener((observable1, oldValue1, newValueLastNameTextField) -> {
                    if (!selectedClient.getLastName().equals(newValueLastNameTextField)){
                        updateBtn.setDisable(false);
                    } else if (selectedClient.getLastName().equals(newValueLastNameTextField)){
                        updateBtn.setDisable(true);
                    }
                });

                tfFirstName.textProperty().addListener((observable1, oldValue1, newValueFirstNameTextField) -> {
                    if (!selectedClient.getFirstName().equals(newValueFirstNameTextField)){
                        updateBtn.setDisable(false);
                    } else if (selectedClient.getFirstName().equals(newValueFirstNameTextField)){
                        updateBtn.setDisable(true);
                    }
                });

                tfMiddleName.textProperty().addListener((observable1, oldValue1, newValueMiddleNameTextField) -> {
                    if (!selectedClient.getMiddleName().equals(newValueMiddleNameTextField)){
                        updateBtn.setDisable(false);
                    } else if (selectedClient.getMiddleName().equals(newValueMiddleNameTextField)){
                        updateBtn.setDisable(true);
                    }
                });

                tfPhoneNumber.textProperty().addListener((observable1, oldValue1, newValuePhoneNumberTextField) -> {
                    if (!selectedClient.getPhoneNumber().equals(newValuePhoneNumberTextField)){
                        updateBtn.setDisable(false);
                    } else if (selectedClient.getPhoneNumber().equals(newValuePhoneNumberTextField)){
                        updateBtn.setDisable(true);
                    }
                });

                tfEmail.textProperty().addListener((observable1, oldValue1, newValueEmailTextField) -> {
                    if (!selectedClient.getEmail().equals(newValueEmailTextField)){
                        updateBtn.setDisable(false);
                    } else if (selectedClient.getEmail().equals(newValueEmailTextField)){
                        updateBtn.setDisable(true);
                    }
                });

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
     * Метод для создания всплывающих подсказок при наведении на соответствующие поля таблицы
     */
    private void createTableTooltip() {
        //Названия полей в таблице
        Label lastNameLabel = new Label("Фамилия");
        Label firstNameLabel = new Label("Имя");
        Label middleNameLabel = new Label("Отчество");
        Label numberPhoneLabel = new Label("Номер телефона");
        Label emailLabel = new Label("Электронная почта");

        //Подсказки при наведении
        lastNameLabel.setTooltip(new Tooltip(lastNameLabel.getText()));
        firstNameLabel.setTooltip(new Tooltip(firstNameLabel.getText()));
        middleNameLabel.setTooltip(new Tooltip(middleNameLabel.getText()));
        numberPhoneLabel.setTooltip(new Tooltip(numberPhoneLabel.getText()));
        emailLabel.setTooltip(new Tooltip(emailLabel.getText()));

        //установка подсказок на соответствующие поля таблицы
        tableColumnLastName.setGraphic(lastNameLabel);
        tableColumnFirstName.setGraphic(firstNameLabel);
        tableColumnMiddleName.setGraphic(middleNameLabel);
        tableColumnPhoneNumber.setGraphic(numberPhoneLabel);
        tableColumnEmail.setGraphic(emailLabel);
}

    /**
     * Метод для заполнения таблицы данными из базы
     */
    private void updateTableContent() {
        tableClients.setItems(createListClients(getClientsTableContent()));
    }

    /**
     * Метод для предоставления возможности поиска клиента по ФИО
     */
    private void findClientByFullName() {
        FilteredList<Client> filteredList = new FilteredList<>(createListClients(getClientsTableContent()));
        tfSearch.textProperty().addListener((observable, oldValue, newValue) ->
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
        }));

        SortedList<Client> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tableClients.comparatorProperty());
        tableClients.setItems(sortedList);
    }

    /**
     * Метод для обработки события при нажатии на кнопку "Создать"
     *
     */
    public void createClient() {
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
            } else {
                client.setPhoneNumber("");
            }

            //проверяем не пустое ли поле "Электронная почта"
            if (!tfEmail.getText().isEmpty()) {
                //установка значения "электронная почта" для объекта
                client.setEmail(tfEmail.getText());
            } else {
                client.setEmail("");
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

                listClients.add(client);

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
     */
    public void updateClient() {



        //SQL запрос для обновления клиента
        String update = "UPDATE " + CLIENT_TABLE + " SET " + CLIENT_LAST_NAME + "=?,"
                + CLIENT_FIRST_NAME + "=?,"
                + CLIENT_MIDDLE_NAME + "=?,"
                + CLIENT_PHONE_NUMBER + "=?,"
                + CLIENT_EMAIL + "=? " +
                "WHERE " + CLIENT_ID + "=?";

        if (!tfPhoneNumber.getText().isEmpty() || !tfEmail.getText().isEmpty()) {

            try {
                PreparedStatement updateClientStatement = new DatabaseHandler().createDbConnection().prepareStatement(update);

                //установка значений для вставки в запрос
                updateClientStatement.setString(1, tfLastName.getText());
                updateClientStatement.setString(2, tfFirstName.getText());
                updateClientStatement.setString(3, tfMiddleName.getText());


                updateClientStatement.setString(4, tfPhoneNumber.getText());
                updateClientStatement.setString(5, tfEmail.getText());
                updateClientStatement.setInt(6, idSelectedClient);
                //выполнение SQL запроса
                updateClientStatement.executeUpdate();

                //заполняем таблицу данным из БД
                tableClients.setItems(listClients);
                //считаем и устанавливаем количество клиентов
                numberOfClientsLabel.setText(String.valueOf(listClients.size()));


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

        } else {
            showModalWindow(
                    "Ошибка обновления клиента",
                    "Поля номер телефона и электронная почта не обязательны к заполнению, но одно из них должно быть указано.",
                    AlertType.ERROR
            );
        }

    }

    /**
     * Метод для обработки события при нажатии на кнопку "Удалить"
     *
     */
    public void deleteClient() {
        //SQL запрос на удаление клиента
        String deleteClient = "DELETE FROM " + CLIENT_TABLE + " WHERE " + CLIENT_ID + "=?";

        try {
            PreparedStatement preparedStatement = new DatabaseHandler().createDbConnection().prepareStatement(deleteClient);

            //установка значений для вставки в запрос
            preparedStatement.setInt(1, idSelectedClient);
            //выполнение запроса на удаление
            preparedStatement.executeUpdate();

            //открываем диалоговое окно для уведомления об успешном удалении
            showModalWindow(
                    "Операция успешно выполнена",
                    "Удаление клиента выполнено успешно!",
                    AlertType.INFORMATION);

            //обнуляем текстовые поля после удаления клиента
            clearTextFields();

            listClients.remove(tableClients.getSelectionModel().getSelectedIndex());

            //Поиск клиента по ФИО
            findClientByFullName();

        } catch (SQLException e) {
            //открываем диалоговое окно для уведомления об ошибке
            showModalWindow(
                    "Ошибка удаления клиента",
                    String.format("Возникла ошибка при удалении клиента с ID = %d", idSelectedClient),
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
        Helper.changeScreen("/ru/tds/realestateagency/views/main.fxml");
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
        idClientsFromDatabase = new ArrayList<>();
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
                idClientsFromDatabase.add(resultSet.getInt("id"));
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
            stage.getScene().getStylesheets().add("ru/tds/realestateagency/css/fullpackstyling.css");
        } else {
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(getClass().getResource("/ru/tds/realestateagency/images/success.png").toString()));
            stage.getScene().getStylesheets().add("ru/tds/realestateagency/css/fullpackstyling.css");
        }

        //отображаем окно
        alert.showAndWait();
    }

}
