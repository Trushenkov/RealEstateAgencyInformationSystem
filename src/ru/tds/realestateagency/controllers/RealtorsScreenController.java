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
import ru.tds.realestateagency.entities.Realtor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Predicate;

/**
 * Класс-контроллер для обработки событий на экране "Работа с риэлторами"
 *
 * @author Трушенков Дмитрий
 */
public class RealtorsScreenController {

    //Константы для работы с таблицей `realtors`
    private static final String REALTOR_TABLE = "realtors";
    private static final String REALTOR_ID = "id";
    private static final String REALTOR_LAST_NAME = "lastName";
    private static final String REALTOR_FIRST_NAME = "firstName";
    private static final String REALTOR_MIDDLE_NAME = "middleName";
    private static final String REALTOR_COMMISSION_PART = "commissionPart";

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
    private TableView<Realtor> tableRealtors;
    @FXML
    private TableColumn<Realtor, String> tableColumnLastName;
    @FXML
    private TableColumn<Realtor, String> tableColumnFirstName;
    @FXML
    private TableColumn<Realtor, String> tableColumnMiddleName;
    @FXML
    private TableColumn<Realtor, Integer> tableColumnCommissionPart;
    @FXML
    private TextField tfCommissionPart;
    @FXML
    private TextField tfLastName;
    @FXML
    private TextField tfMiddleName;
    @FXML
    private TextField tfSearch;
    @FXML
    private TextField tfFirstName;

    private int idRealtor;// для хранения ID риэлтора из базы

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
        alert.initOwner(this.tableRealtors.getScene().getWindow());
        //установка типа модального окна
        alert.initModality(Modality.WINDOW_MODAL);

        if (alert.getAlertType().equals(Alert.AlertType.ERROR)) {
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

        //определение колонок таблицы с соответствующими полями объекта "Риэлтор"
        tableColumnLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        tableColumnFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        tableColumnMiddleName.setCellValueFactory(new PropertyValueFactory<>("middleName"));
        tableColumnCommissionPart.setCellValueFactory(new PropertyValueFactory<>("commissionPart"));

        //заполняем таблицу данным из БД
        tableRealtors.setItems(createListRealtors(getRealtorsTableContent()));

        //Заносим данные выделенного объекта в текстовые поля при клике на объект в таблице
        tableRealtors.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue != null) {
                createBtn.setDisable(true);
                updateBtn.setDisable(false);
                deleteBtn.setDisable(false);
                try {

                    //SQL запрос для получения ID риэлтора из таблицы в базе
                    String getIdRealtor = "SELECT " + REALTOR_ID +
                            " FROM " + REALTOR_TABLE +
                            " WHERE " + REALTOR_LAST_NAME + " = ? AND "
                            + REALTOR_FIRST_NAME + " = ? AND "
                            + REALTOR_MIDDLE_NAME + "=?";

                    DatabaseHandler handler = new DatabaseHandler();
                    PreparedStatement statement = handler.createDbConnection().prepareStatement(getIdRealtor);

                    //установка значений для вставки в запрос
                    statement.setString(1, tableRealtors.getSelectionModel().getSelectedItem().getLastName());
                    statement.setString(2, tableRealtors.getSelectionModel().getSelectedItem().getFirstName());
                    statement.setString(3, tableRealtors.getSelectionModel().getSelectedItem().getMiddleName());

                    //выполнение запроса и сохранение значений в resultSet
                    ResultSet resultSet = statement.executeQuery();
                    while (resultSet.next()) {
                        //получаем id из набора данных, полученного из базы
                        idRealtor = resultSet.getInt("id");
                    }
                } catch (SQLException e) {
                    showModalWindow(
                            "Ошибка получения ID риэлтора из базы",
                            "Данные из базы не были получены!",
                            Alert.AlertType.ERROR);
                }

                //устанавливаем значения выделенного объекта в текстовые поля
                tfLastName.setText(tableRealtors.getSelectionModel().getSelectedItem().getLastName());
                tfFirstName.setText(tableRealtors.getSelectionModel().getSelectedItem().getFirstName());
                tfMiddleName.setText(tableRealtors.getSelectionModel().getSelectedItem().getMiddleName());
                tfCommissionPart.setText(String.valueOf(tableRealtors.getSelectionModel().getSelectedItem().getCommissionPart()));
            } else {
                createBtn.setDisable(false);
                updateBtn.setDisable(true);
                deleteBtn.setDisable(true);
            }
        });

        //Поиск риэтора по ФИО
        findByFullName();
    }


    /**
     * Метод для обработки события при нажатии на кнопку "Создать"
     *
     * @param actionEvent нажатие на кнопку
     */
    public void createBtnClicked(ActionEvent actionEvent) {
        //Создание объекта "Риэлтор"
        Realtor realtor = new Realtor();

        //проверка на то пустые ли поля "Фамилия", "Имя", "Отчество"
        if (!tfLastName.getText().isEmpty() && !tfFirstName.getText().isEmpty() && !tfMiddleName.getText().isEmpty()) {
            //установка значений для объекта
            realtor.setLastName(tfLastName.getText());
            realtor.setFirstName(tfFirstName.getText());
            realtor.setMiddleName(tfMiddleName.getText());

            //проверяем не пустое ли поле "Номер телефона"
            if (!tfCommissionPart.getText().isEmpty()) {
                //проверка введенного числа
                if (Integer.parseInt(tfCommissionPart.getText()) >= 0 && Integer.parseInt(tfCommissionPart.getText()) <= 100) {
                    //устанавливаем значение для объекта
                    realtor.setCommissionPart(Integer.parseInt(tfCommissionPart.getText()));
                } else {
                    //открываем диалоговое окно для уведомления об ошибке
                    showModalWindow(
                            "Ошибка добавление нового риэлтора",
                            "Доля от комиссии - числовое поле, может принимать значение от 0 до 100",
                            AlertType.ERROR);
                    return;
                }
            } else {
                //открываем диалоговое окно для уведомления об ошибке
                showModalWindow(
                        "Ошибка добавление нового риэлтора",
                        "Доля от комиссии - числовое поле, может принимать значение от 0 до 100",
                        AlertType.ERROR);
                return;
            }

            //SQL запрос для добавления нового риэлтора в базу данных
            String insertRealtor = "INSERT INTO " +
                    REALTOR_TABLE + "(" +
                    REALTOR_LAST_NAME + ", " +
                    REALTOR_FIRST_NAME + ", " +
                    REALTOR_MIDDLE_NAME + ", " +
                    REALTOR_COMMISSION_PART + ")" +
                    " VALUES (?,?,?,?);";


            try {
                PreparedStatement addRealtorStatement = new DatabaseHandler().createDbConnection().prepareStatement(insertRealtor);
                //установка значений для вставки в запрос
                addRealtorStatement.setString(1, realtor.getLastName());
                addRealtorStatement.setString(2, realtor.getFirstName());
                addRealtorStatement.setString(3, realtor.getMiddleName());
                addRealtorStatement.setInt(4, realtor.getCommissionPart());
                //выполнение запроса
                addRealtorStatement.executeUpdate();

                //обновление таблицы после выполнения запроса
                tableRealtors.setItems(createListRealtors(getRealtorsTableContent()));

                //открываем диалоговое окно для уведомления об успешном добавлении
                showModalWindow(
                        "Операция успешно выполнена",
                        "Новый риэлтор успешно добавлен!",
                        AlertType.INFORMATION);

                findByFullName();

                //Обнуляем текстовые поля после добавления нового риэлтора
                clearTextFields();

            } catch (SQLException e) {
                //открываем диалоговое окно для уведомления об ошибке
                showModalWindow(
                        "Ошибка добавления нового риэлтора",
                        "Риэлтор не был добавлен в базу. Повторите еще раз",
                        AlertType.ERROR);
            }
        } else {

            //открываем диалоговое окно для уведомления об ошибке
            showModalWindow(
                    "Ошибка добавления нового риэлтора",
                    "Поля: фамилия, имя, отчество обязательны к заполнению.",
                    AlertType.ERROR);
        }
    }

    /**
     * Метод для обработки события при нажатии на кнопку "Обновить"
     *
     * @param actionEvent нажатие на кнопку
     */
    public void updateBtnClicked(ActionEvent actionEvent) {

        //SQL запрос для обновления риэлтора
        String updateRealtor = "UPDATE " + REALTOR_TABLE + " SET "
                + REALTOR_LAST_NAME + "=?,"
                + REALTOR_FIRST_NAME + "=?,"
                + REALTOR_MIDDLE_NAME + "=?,"
                + REALTOR_COMMISSION_PART + "=?" + " WHERE " + REALTOR_ID + "=?";

        try {
            PreparedStatement preparedStatement = new DatabaseHandler().createDbConnection().prepareStatement(updateRealtor);
            //установка значений для вставки в запрос
            preparedStatement.setString(1, tfLastName.getText());
            preparedStatement.setString(2, tfFirstName.getText());
            preparedStatement.setString(3, tfMiddleName.getText());
            if (Integer.parseInt(tfCommissionPart.getText()) >= 0 && Integer.parseInt(tfCommissionPart.getText()) <= 100) {
                preparedStatement.setInt(4, Integer.parseInt(tfCommissionPart.getText()));
            } else {
                //открываем диалоговое окно для уведомления об ошибке
                showModalWindow(
                        "Ошибка добавление нового риэлтора",
                        "Доля от комиссии - обязательно числовое поле, которое может принимать значение от 0 до 100",
                        AlertType.ERROR);
                return;
            }
            preparedStatement.setInt(5, idRealtor);
            //выполнение SQL запроса
            preparedStatement.executeUpdate();

            //обновление таблицы
            tableRealtors.setItems(createListRealtors(getRealtorsTableContent()));

            //открываем диалоговое окно для уведомления об успешном обновлении
            showModalWindow(
                    "Операция успешно выполнена",
                    "Обновление риэлтора выполнено успешно!",
                    AlertType.INFORMATION);

        } catch (SQLException e) {
            //открываем диалоговое окно для уведомления об ошибке
            showModalWindow(
                    "Ошибка обновления риэлтора",
                    "Обновление риэлтора не выполнено! Повторите еще раз",
                    AlertType.ERROR);
        }

        findByFullName();

        //обнуляем текстовые поля после обновления риэлтора
        clearTextFields();
    }

    /**
     * Метод для обнуления текстовых полей
     */
    private void clearTextFields() {
        tfLastName.setText("");
        tfFirstName.setText("");
        tfMiddleName.setText("");
        tfCommissionPart.setText("");
    }

    /**
     * Метод для обработки события при нажатии на кнопку "Удалить"
     *
     * @param actionEvent нажатие на кнопку
     */
    public void deleteBtnClicked(ActionEvent actionEvent) {
        //SQL запрос на удаление риэлтора
        String deleteRealtor = "DELETE FROM " + REALTOR_TABLE + " WHERE " + REALTOR_ID + "=?";

        try {
            PreparedStatement preparedStatement = new DatabaseHandler().createDbConnection().prepareStatement(deleteRealtor);
            //установка значений для вставки в запрос
            preparedStatement.setInt(1, idRealtor);
            //выполнение запроса на удаление
            preparedStatement.executeUpdate();

            //обновление таблицы после удаления
            tableRealtors.setItems(createListRealtors(getRealtorsTableContent()));

            //открываем диалоговое окно для уведомления об успешном удалении
            showModalWindow(
                    "Операция успешно выполнена",
                    "Удаление риэлтора выполнено успешно!",
                    AlertType.INFORMATION);

            //обнуляем текстовые поля после удаления клиента
            clearTextFields();

            findByFullName();

        } catch (SQLException e) {
            //открываем диалоговое окно для уведомления об ошибке
            showModalWindow(
                    "Ошибка удаления риэлтора",
                    "Возникла ошибка при удалении риэлтора с ID = " + idRealtor,
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
    private ResultSet getRealtorsTableContent() {

        //SQL запрос на выбор всех данных из таблицы `realtors`
        String selectRealtors = "SELECT * FROM " + REALTOR_TABLE;

        ResultSet resultSet = null;
        try {
            PreparedStatement ps = new DatabaseHandler().createDbConnection().prepareStatement(selectRealtors);
            //выполняем запрос и сохраняем полученные значения в resultSet
            resultSet = ps.executeQuery();
        } catch (Exception e) {
            //открываем диалоговое окно для уведомления об ошибке
            showModalWindow(
                    "Ошибка получения данных из таблицы риэлторов",
                    "Данные не получены из базы. Проверьте подключение к базе!",
                    AlertType.ERROR);
        }

        return resultSet;
    }

    /**
     * Метод для формирования ObservableList из набора данных
     *
     * @param resultSet Набор данных из таблицы риэлторов
     * @return ObservableList с объектами типа Realtor
     */
    private ObservableList<Realtor> createListRealtors(ResultSet resultSet) {
        ObservableList<Realtor> list = FXCollections.observableArrayList();
        try {
            while (resultSet.next()) {
                //создаем нового риэлтора
                Realtor realtor = new Realtor(
                        resultSet.getString("lastName"),
                        resultSet.getString("firstName"),
                        resultSet.getString("middleName"),
                        resultSet.getInt("commissionPart"
                        ));
                //добавляем риэлтора в список
                list.add(realtor);
            }
        } catch (SQLException e) {
            //открываем диалоговое окно для уведомления об ошибке
            showModalWindow(
                    "Ошибка формирования коллекции риэлторов",
                    "Возникла ошибка при формировании списка риэлторов из набора данных, полученных из базы",
                    AlertType.ERROR);
        }
        return list;
    }

    /**
     * Метод для предоставления возможности поиска по ФИО
     */
    private void findByFullName() {
        FilteredList<Realtor> filteredList = new FilteredList<>(createListRealtors(getRealtorsTableContent()));
        tfSearch.textProperty().addListener((observable, oldValue, newValue) -> {

            filteredList.setPredicate((Predicate<? super Realtor>) realtor -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                ObservableList<Realtor> list = createListRealtors(getRealtorsTableContent());
                for (int i = 0; i < list.size(); i++) {
                    if (Helper.levenstain(newValue.toLowerCase(), realtor.getLastName().toLowerCase()) <= 3) {
                        return true;
                    }
                    if (Helper.levenstain(newValue.toLowerCase(), realtor.getFirstName().toLowerCase()) <= 3) {
                        return true;
                    }
                    if (Helper.levenstain(newValue.toLowerCase(), realtor.getMiddleName().toLowerCase()) <= 3) {
                        return true;
                    }
                }

                return false;
            });
        });

        SortedList<Realtor> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tableRealtors.comparatorProperty());
        tableRealtors.setItems(sortedList);
    }
}
