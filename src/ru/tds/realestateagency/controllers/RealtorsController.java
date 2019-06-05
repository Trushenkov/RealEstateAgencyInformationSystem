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
import ru.tds.realestateagency.entities.Realtor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.function.Predicate;

/**
 * Класс-контроллер для обработки событий на экране "Риэлторы"
 *
 * @author Трушенков Дмитрий
 */
public class RealtorsController {

    //Константы для работы с таблицей `realtors`
    private static final String REALTOR_TABLE = "realtors";
    private static final String REALTOR_ID = "id";
    private static final String REALTOR_LAST_NAME = "lastName";
    private static final String REALTOR_FIRST_NAME = "firstName";
    private static final String REALTOR_MIDDLE_NAME = "middleName";
    private static final String REALTOR_COMMISSION_PART = "commissionPart";

    private static final String OFFERS_TABLE = "offers";
    private static final String DEMANDS_TABLE= "demands";

    //Элементы разметки интерфейса
    @FXML
    private AnchorPane mainPane;
    @FXML
    private Label totalRealtorsLabel;
    @FXML
    private Label realtorsWithDemandsLabel;
    @FXML
    private Label realtorsWithOffersLabel;
    @FXML
    private Button createBtn;
    @FXML
    private Button updateBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private TableView<Realtor> tableRealtors;
    @FXML
    private TableColumn<Realtor, String> tableColumnLastName;
    @FXML
    private TableColumn<Realtor, String> tableColumnFirstName;
    @FXML
    private TableColumn<Realtor, String> tableColumnMiddleName;
    @FXML
    private TableColumn<Realtor, Integer> tableColumnCommisionPart;
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
    private int idSelectedRealtor;// для хранения ID выбранного риэлтора
    private ArrayList<Integer> idRealtorsFromDatabase;// список ID риэлторов
    private ObservableList<Realtor> listRealtors;//список риэлторов


    @FXML
    void initialize() {

        //Обнуление текстовых полей и отмена выбора строки в таблице при нажатии на область окна приложения
        mainPane.setOnMousePressed(event -> {
            clearTextFields();
            tableRealtors.getSelectionModel().clearSelection();
            idSelectedRealtor = 0;
            updateBtn.setDisable(true);
        });

        updateBtn.setDisable(true);
        deleteBtn.setDisable(true);

        //определение колонок таблицы с соответствующими полями объекта "Риэлтор"
        tableColumnLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        tableColumnFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        tableColumnMiddleName.setCellValueFactory(new PropertyValueFactory<>("middleName"));
        tableColumnCommisionPart.setCellValueFactory(new PropertyValueFactory<>("commissionPart"));

        //отключение возможности сортировки полей таблицы
        tableColumnLastName.setSortable(false);
        tableColumnFirstName.setSortable(false);
        tableColumnMiddleName.setSortable(false);
        tableColumnCommisionPart.setSortable(false);

        listRealtors = createRealtorsListFromTable(getRealtorsTableContent());

        listRealtors.addListener((ListChangeListener<Realtor>) c -> {
            updateTableContent();
            totalRealtorsLabel.setText(String.valueOf(listRealtors.size()));

            //установка значения для метки "Количество риэлторов, связанных с предложением"
            realtorsWithOffersLabel.setText(String.valueOf(getRealtorsWithOffer().size()));
            //установка значения для метки "Количество риэлторов, связанных с потребностью
            realtorsWithDemandsLabel.setText(String.valueOf(getRealtorsWithDemand().size()));
        });

        //заполняем таблицу данным из БД
        tableRealtors.setItems(listRealtors);
        //устанавливаем количество риэлторов
        totalRealtorsLabel.setText(String.valueOf(listRealtors.size()));
        //установка значения для метки "Количество риэлторов, связанных с предложением"
        realtorsWithOffersLabel.setText(String.valueOf(getRealtorsWithOffer().size()));
        //установка значения для метки "Количество риэлторов, связанных с потребностью
        realtorsWithDemandsLabel.setText(String.valueOf(getRealtorsWithDemand().size()));

        //Заносим данные выделенного объекта в текстовые поля при клике на объект в таблице
        tableRealtors.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedRealtor) -> {

            if (selectedRealtor != null) {

                idSelectedRealtor = idRealtorsFromDatabase.get(tableRealtors.getSelectionModel().getSelectedIndex());

                //устанавливаем значения выделенного объекта в текстовые поля
                tfLastName.setText(tableRealtors.getSelectionModel().getSelectedItem().getLastName());
                tfFirstName.setText(tableRealtors.getSelectionModel().getSelectedItem().getFirstName());
                tfMiddleName.setText(tableRealtors.getSelectionModel().getSelectedItem().getMiddleName());
                tfCommissionPart.setText(String.valueOf(tableRealtors.getSelectionModel().getSelectedItem().getCommissionPart()));

                //Слушатели для текстовых полей для проверки на изменение какого либо значения и предоставления возможности обновления риэлтора
                tfLastName.textProperty().addListener((observable1, oldValue1, newValueLastNameTextField) -> {
                    try {
                        if (!tableRealtors.getSelectionModel().getSelectedItem().getLastName().equals(newValueLastNameTextField)) {
                            updateBtn.setDisable(false);
                        } else if (tableRealtors.getSelectionModel().getSelectedItem().getLastName().equals(newValueLastNameTextField)) {
                            updateBtn.setDisable(true);
                        }
                    } catch (Exception ignored) {
                    }
                });

                tfFirstName.textProperty().addListener((observable1, oldValue1, newValueFirstNameTextField) -> {
                    try {
                        if (!tableRealtors.getSelectionModel().getSelectedItem().getFirstName().equals(newValueFirstNameTextField)) {
                            updateBtn.setDisable(false);
                        } else if (tableRealtors.getSelectionModel().getSelectedItem().getFirstName().equals(newValueFirstNameTextField)) {
                            updateBtn.setDisable(true);
                        }
                    } catch (Exception ignored) {
                    }
                });

                tfMiddleName.textProperty().addListener((observable1, oldValue1, newValueMiddleNameTextField) -> {
                    try {
                        if (!tableRealtors.getSelectionModel().getSelectedItem().getMiddleName().equals(newValueMiddleNameTextField)) {
                            updateBtn.setDisable(false);
                        } else if (tableRealtors.getSelectionModel().getSelectedItem().getMiddleName().equals(newValueMiddleNameTextField)) {
                            updateBtn.setDisable(true);
                        }
                    } catch (Exception ignored) {
                    }
                });

                tfCommissionPart.textProperty().addListener((observable1, oldValue1, newValuePhoneNumberTextField) -> {
                    if (!newValuePhoneNumberTextField.isEmpty()) {
                        try {
                            if (tableRealtors.getSelectionModel().getSelectedItem().getCommissionPart() != Integer.parseInt(newValuePhoneNumberTextField)) {
                                updateBtn.setDisable(false);
                            } else if (tableRealtors.getSelectionModel().getSelectedItem().getCommissionPart() == Integer.parseInt((newValuePhoneNumberTextField))) {
                                updateBtn.setDisable(true);
                            }
                        } catch (Exception ignored) {
                        }
                    }
                });

                createBtn.setDisable(true);
                updateBtn.setDisable(true);
                deleteBtn.setDisable(false);

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
     * Метод для формирования коллекции с фамилиями риэлторов, связанных с потребностью
     *
     * @return уникальная коллекция фамилий риэлторов, связанных с потребностью
     */
    private HashSet<String> getRealtorsWithDemand() {
        //список фамилий риэлторов из таблицы "Потребности"
        ArrayList<String> listOfClientWithDemands = createListOfRealtors(getDataFromDb(DEMANDS_TABLE));
        //массив, содержащий уникальные значения фамилий риэлторов из таблицы потребностей
        return new HashSet<>(listOfClientWithDemands);
    }

    /**
     * Метод для формирования коллекции с фамилиями риэлторов, связанных с предложением
     *
     * @return уникальная коллекция фамилий риэлторов, связанных с предложением
     */
    private HashSet<String> getRealtorsWithOffer() {
        //список фамилий риэлторов из таблицы "Предложения"
        ArrayList<String> listOfClientWithOffers = createListOfRealtors(getDataFromDb(OFFERS_TABLE));
        //массив, содержащий уникальные значения фамилий риэлторов из таблицы предложений
        return new HashSet<>(listOfClientWithOffers);
    }


    /**
     * Метод для добавления нового риэлтора при нажатии на кнопку "Создать"
     */
    public void createRealtor() {
        //Создание объекта "Риэлтор"
        Realtor realtor = new Realtor();

        //проверка на то пустые ли поля "Фамилия", "Имя", "Отчество"
        if (!tfLastName.getText().isEmpty() && !tfFirstName.getText().isEmpty() && !tfMiddleName.getText().isEmpty() && !tfCommissionPart.getText().isEmpty()) {
            //установка значений для объекта
            realtor.setLastName(tfLastName.getText());
            realtor.setFirstName(tfFirstName.getText());
            realtor.setMiddleName(tfMiddleName.getText());

            //проверяем не пустое ли поле "Номер телефона"
//            if (!tfCommissionPart.getText().isEmpty()) {
                //проверка введенного числа
                if (Integer.parseInt(tfCommissionPart.getText()) >= 0 && Integer.parseInt(tfCommissionPart.getText()) <= 100) {
                    //устанавливаем значение для объекта
                    realtor.setCommissionPart(Integer.parseInt(tfCommissionPart.getText()));
                } else {
                    //открываем диалоговое окно для уведомления об ошибке
                    showAlert(
                            "Ошибка добавление нового риэлтора",
                            "Доля от комиссии - числовое поле, может принимать значение от 0 до 100",
                            AlertType.ERROR);
                    return;
                }
//            }
//            else {
//                //открываем диалоговое окно для уведомления об ошибке
//                showAlert(
//                        "Ошибка добавление нового риэлтора",
//                        "Доля от комиссии - числовое поле, может принимать значение от 0 до 100",
//                        AlertType.ERROR);
//                return;
//            }

            //SQL запрос для добавления нового риэлтора в базу данных
            String insertRealtor = String.format("INSERT INTO %s(%s, %s, %s, %s) VALUES (?,?,?,?);",
                    REALTOR_TABLE,
                    REALTOR_LAST_NAME,
                    REALTOR_FIRST_NAME,
                    REALTOR_MIDDLE_NAME,
                    REALTOR_COMMISSION_PART);

            try {
                PreparedStatement addRealtorStatement = new DatabaseHandler().createDbConnection().prepareStatement(insertRealtor);
                //установка значений для вставки в запрос
                addRealtorStatement.setString(1, realtor.getLastName());
                addRealtorStatement.setString(2, realtor.getFirstName());
                addRealtorStatement.setString(3, realtor.getMiddleName());
                addRealtorStatement.setInt(4, realtor.getCommissionPart());
                //выполнение запроса
                addRealtorStatement.executeUpdate();

                listRealtors.add(realtor);

                //открываем диалоговое окно для уведомления об успешном добавлении
                showAlert(
                        "Операция успешно выполнена",
                        "Новый риэлтор успешно добавлен!",
                        AlertType.INFORMATION);

                findByFullName();

                //Обнуляем текстовые поля после добавления нового риэлтора
                clearTextFields();

            } catch (SQLException e) {
                //открываем диалоговое окно для уведомления об ошибке
                showAlert(
                        "Ошибка добавления нового риэлтора",
                        "Риэлтор не был добавлен в базу. Повторите еще раз",
                        AlertType.ERROR);
            }
        } else {
            //открываем диалоговое окно для уведомления об ошибке
            showAlert(
                    "Ошибка добавления нового риэлтора",
                    "Все поля являются обязательными для заполнения.",
                    AlertType.ERROR);
        }
    }

    /**
     * Метод для обновления информации о риэлторе при нажатии на кнопку "Обновить"
     */
    public void updateRealtor() {

        //SQL запрос для обновления риэлтора
        String updateRealtor = String.format("UPDATE %s SET %s=?,%s=?,%s=?,%s=? WHERE %s=?",
                REALTOR_TABLE,
                REALTOR_LAST_NAME,
                REALTOR_FIRST_NAME,
                REALTOR_MIDDLE_NAME,
                REALTOR_COMMISSION_PART,
                REALTOR_ID);

        if (!tfLastName.getText().isEmpty() && !tfFirstName.getText().isEmpty() && !tfMiddleName.getText().isEmpty() && !tfCommissionPart.getText().isEmpty()) {
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
                    showAlert(
                            "Ошибка обновления риэлтора",
                            "Доля от комиссии - обязательно числовое поле, которое может принимать значение от 0 до 100",
                            AlertType.ERROR);
                    return;
                }

                preparedStatement.setInt(5, idSelectedRealtor);
                //выполнение SQL запроса
                preparedStatement.executeUpdate();

                //обновление таблицы
                tableRealtors.setItems(createRealtorsListFromTable(getRealtorsTableContent()));

                //открываем диалоговое окно для уведомления об успешном обновлении
                showAlert(
                        "Операция успешно выполнена",
                        "Обновление риэлтора выполнено успешно!",
                        AlertType.INFORMATION);

            } catch (SQLException e) {
                //открываем диалоговое окно для уведомления об ошибке
                showAlert(
                        "Ошибка обновления риэлтора",
                        "Обновление риэлтора не выполнено! Повторите еще раз",
                        AlertType.ERROR);
            }

            findByFullName();

            //обнуляем текстовые поля после обновления риэлтора
            clearTextFields();
        } else {
            //открываем диалоговое окно для уведомления об ошибке
            showAlert(
                    "Ошибка обновления риэлтора",
                    "Все поля являются обязательными для заполнения.",
                    AlertType.ERROR);
        }
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
     * Метод для удаления риэлтора при нажатии на кнопку "Удалить"
     */
    public void deleteRealtor() {
        //SQL запрос на удаление риэлтора
        String deleteRealtor = String.format("DELETE FROM %s WHERE %s=?", REALTOR_TABLE, REALTOR_ID);

        try {
            PreparedStatement preparedStatement = new DatabaseHandler().createDbConnection().prepareStatement(deleteRealtor);
            //установка значений для вставки в запрос
            preparedStatement.setInt(1, idSelectedRealtor);
            //выполнение запроса на удаление
            preparedStatement.executeUpdate();

            //открываем диалоговое окно для уведомления об успешном удалении
            showAlert(
                    "Операция успешно выполнена",
                    "Удаление риэлтора выполнено успешно!",
                    AlertType.INFORMATION);

            //обнуляем текстовые поля после удаления клиента
            clearTextFields();

            listRealtors.remove(tableRealtors.getSelectionModel().getSelectedIndex());

            findByFullName();

        } catch (SQLException e) {
            //открываем диалоговое окно для уведомления об ошибке
            showAlert(
                    "Ошибка удаления риэлтора",
                    "Возникла ошибка при удалении риэлтора с ID = " + idSelectedRealtor,
                    AlertType.ERROR
            );
        }
    }

    /**
     * Метод для перехода на главный экран приложения при нажатии на кнопку "Назад"
     *
     * @param actionEvent нажатие на кнопку
     */
    public void goHomeScreen(ActionEvent actionEvent) {
        ((Node) actionEvent.getSource()).getScene().getWindow().hide();
        //переход на главный экран
        Helper.openNewScreen("/ru/tds/realestateagency/views/main.fxml");
    }

    /**
     * Метод для получения содержимого таблицы риэлторов из базы данных
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
            showAlert(
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
     * @return ObservableList с объектами класса Realtor
     */
    private ObservableList<Realtor> createRealtorsListFromTable(ResultSet resultSet) {
        ObservableList<Realtor> list = FXCollections.observableArrayList();
        idRealtorsFromDatabase = new ArrayList<>();
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
                //добавляем ID риэлтора в список
                idRealtorsFromDatabase.add(resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            //открываем диалоговое окно для уведомления об ошибке
            showAlert(
                    "Ошибка формирования коллекции риэлторов",
                    "Возникла ошибка при формировании списка риэлторов из набора данных, полученных из базы",
                    AlertType.ERROR);
        }
        return list;
    }

    /**
     * Метод для формирования ArrayList с фамилиями риэлторов из набора данных
     *
     * @param resultSet набор данных из таблицы
     * @return ArrayList с фамилиями риэлторов
     */
    private ArrayList<String> createListOfRealtors(ResultSet resultSet) {
        ArrayList<String> list = new ArrayList<>();
        try {
            while (resultSet.next()) {
                //добавляем предложение в список
                list.add(resultSet.getString("realtor"));
            }
        } catch (SQLException e) {
            //открываем диалоговое окно для уведомления об ошибке
            showAlert(
                    "Ошибка формирования коллекции с фамилиями риэлторов",
                    "Возникла ошибка при формировании списка фамилий риэлторов из набора данных, полученных из базы",
                    Alert.AlertType.ERROR);
        }
        return list;
    }

    /**
     * Метод для предоставления возможности поиска по ФИО
     */
    private void findByFullName() {
        FilteredList<Realtor> filteredList = new FilteredList<>(createRealtorsListFromTable(getRealtorsTableContent()));
        tfSearch.textProperty().addListener((observable, oldValue, newValue) ->
                filteredList.setPredicate((Predicate<? super Realtor>) realtor -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    ObservableList<Realtor> list = createRealtorsListFromTable(getRealtorsTableContent());
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
                }));

        SortedList<Realtor> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tableRealtors.comparatorProperty());
        tableRealtors.setItems(sortedList);
    }

    /**
     * Метод для получения содержимого таблицы из базы данных
     *
     * @return ResultSet - набор данных из таблицы
     */
    private ResultSet getDataFromDb(String tableName) {
        //SQL запрос на выбор всех данных из таблицы `clients`
        String selectClients = String.format("SELECT * FROM %s", tableName);

        ResultSet resultSet = null;
        try {
            PreparedStatement ps = new DatabaseHandler().createDbConnection().prepareStatement(selectClients);
            //выполняем запрос и сохраняем полученные значения в resultSet
            resultSet = ps.executeQuery();
        } catch (SQLException e) {
            //открываем диалоговое окно для уведомления об ошибке
            showAlert(
                    "Ошибка получения данных из таблицы " + tableName,
                    "Данные не получены из базы. Проверьте подключение к базе!",
                    AlertType.ERROR);
        }

        return resultSet;
    }


    /**
     * Метод для заполнения таблицы данными из базы
     */
    private void updateTableContent() {
        tableRealtors.setItems(createRealtorsListFromTable(getRealtorsTableContent()));
    }

    /**
     * Метод для создания и открытия модального окна
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
}
