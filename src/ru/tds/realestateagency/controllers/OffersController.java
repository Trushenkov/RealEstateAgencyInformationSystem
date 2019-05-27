package ru.tds.realestateagency.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.tds.realestateagency.DatabaseHandler;
import ru.tds.realestateagency.Helper;
import ru.tds.realestateagency.entities.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Класс-контроллер для обработки событий на экране "Предложения"
 *
 * @author Трушенков Дмитрий
 */
public class OffersController {

    private static final String PATH_TO_HOME_SCREEN = "/ru/tds/realestateagency/views/main.fxml";

    //Тип объекта недвижимости
    private static final String HOME = "Дом";
    private static final String FLAT = "Квартира";
    private static final String LAND = "Земля";

    //Имена таблиц в базе данных
    private static final String TABLE_CLIENTS = "clients";
    private static final String TABLE_REALTORS = "realtors";
    private static final String TABLE_HOMES = "home";
    private static final String TABLE_FLATS = "flat";
    private static final String TABLE_LANDS = "land";
    private static final String TABLE_OFFERS = "offers";
    private static final String TABLE_OFFERS_ID = "id";

    //Элементы интерфейса
    @FXML
    private AnchorPane mainPane;
    @FXML
    private TableView<Offer> tableOffers;
    @FXML
    private TableColumn<Offer, String> clientTableColumn;
    @FXML
    private TableColumn<Offer, String> realtorTableColumn;
    @FXML
    private TableColumn<Offer, String> realEstateTableColumn;
    @FXML
    private TableColumn<Offer, Integer> priceTableColumn;
    @FXML
    private ComboBox<Client> clientComboBox;
    @FXML
    private ComboBox<Realtor> realtorComboBox;
    @FXML
    private ComboBox<String> typeRealEstateComboBox;
    @FXML
    private ComboBox<RealEstate> realEstateComboBox;
    @FXML
    private TextField priceTextField;
    @FXML
    private Button updateOfferButton;
    @FXML
    private Button deleteOfferButton;
    @FXML
    private Button createOfferButton;


    //Списки для храненеия информации из базы данных
    private ObservableList<Client> listOfClients;//список клиентов

    private ObservableList<Realtor> listOfRealtors;//список риэлторов

    private ObservableList<Home> listOfHomes;//список домов
    private ObservableList<Flat> listOfFlats;//список квартир
    private ObservableList<Land> listOfLands;//список земель

    private ObservableList<RealEstate> listRealEstates;//список объектов недвижимости

    private ObservableList<Offer> listOfOffers;//список предложений
    private ArrayList<Integer> idOffersFromDatabase;
    private int idSelectedOffer;//ID выбранного предложени из таблицы


    @FXML
    void initialize() {

        //Обнуление текстовых полей и списков  при нажатии на область окна приложения
        mainPane.setOnMousePressed(event -> {
            clearTextFieldsAndComboBox();
            tableOffers.getSelectionModel().clearSelection();
            updateOfferButton.setDisable(true);
        });

        updateOfferButton.setDisable(true);
        deleteOfferButton.setDisable(true);

        //определение полей таблицы с соотвествующими полями объекта
        clientTableColumn.setCellValueFactory(new PropertyValueFactory<>("client"));
        realtorTableColumn.setCellValueFactory(new PropertyValueFactory<>("realtor"));
        realEstateTableColumn.setCellValueFactory(new PropertyValueFactory<>("realEstate"));
        priceTableColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        //отключение возможности сортировки столбца
        clientTableColumn.setSortable(false);
        realtorTableColumn.setSortable(false);
        realEstateTableColumn.setSortable(false);
        priceTableColumn.setSortable(false);

        //объявляем коллекции объектов
        listOfClients = createListOfClients(getDataFromDB(TABLE_CLIENTS));
        listOfRealtors = createListOfRealtors(getDataFromDB(TABLE_REALTORS));
        listRealEstates = FXCollections.observableArrayList();
        listOfHomes = createListOfHomes(getDataFromDB(TABLE_HOMES));
        listOfFlats = createListOfFlats(getDataFromDB(TABLE_FLATS));
        listOfLands = createListOfLands(getDataFromDB(TABLE_LANDS));
        listOfOffers = createListOfOffers(getDataFromDB(TABLE_OFFERS));

        ObservableList<String> listOfTypesRealEstate = FXCollections.observableArrayList(HOME, FLAT, LAND);


        //добавляем слушателя для формирования и заполнения списка объектов недвижимости при смене значение типа объекта недвижимости
        typeRealEstateComboBox.valueProperty().addListener((objectProperty, oldValue, newValue) -> {
            if (newValue != null) {
                switch (newValue) {
                    case HOME:
                        //добавляем в ComboBox объектов недвижимости только объекты недвижимости типа "Дом"
                        realEstateComboBox.getItems().clear();
                        for (Home home : listOfHomes) {
                            RealEstate realEstate = new RealEstate();
                            realEstate.setHome(home.toString());
                            listRealEstates.add(realEstate);
                        }
                        realEstateComboBox.setItems(listRealEstates);
                        break;
                    case FLAT:
                        //добавляем в ComboBox объектов недвижимости только объекты недвижимости типа "Квартира"
                        realEstateComboBox.getItems().clear();
                        for (Flat flat : listOfFlats) {
                            RealEstate realEstate = new RealEstate();
                            realEstate.setFlat(flat.toString());
                            listRealEstates.add(realEstate);
                        }
                        realEstateComboBox.setItems(listRealEstates);
                        break;
                    case LAND:
                        //добавляем в ComboBox объектов недвижимости только объекты недвижимости типа "Земля"
                        realEstateComboBox.getItems().clear();
                        for (Land land : listOfLands) {
                            RealEstate realEstate = new RealEstate();
                            realEstate.setLand(land.toString());
                            listRealEstates.add(realEstate);
                        }
                        realEstateComboBox.setItems(listRealEstates);
                        break;
                    default:
                        break;
                }
            }
        });


        //размещаем соответствующие списки объектов в элементы ComboBox
        clientComboBox.setItems(listOfClients);
        realtorComboBox.setItems(listOfRealtors);
        typeRealEstateComboBox.setItems(listOfTypesRealEstate);
        realEstateComboBox.setItems(listRealEstates);

        //заполнение таблицы данными из базы
        tableOffers.setItems(listOfOffers);

        //слушатель для обработки событий при объекта в таблице
        tableOffers.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {

                idSelectedOffer = idOffersFromDatabase.get(tableOffers.getSelectionModel().getSelectedIndex());
                System.out.println("ID выбранного предложения в базе = " + idSelectedOffer);

                //Слушатели для текстовых полей для проверки на изменение какого либо значения и предоставления возможности обновления клиента
                priceTextField.textProperty().addListener((observable1, oldValue1, newValuePriceTextField) -> {
                    try {
                        if (tableOffers.getSelectionModel().getSelectedItem().getPrice() != Integer.parseInt(newValuePriceTextField)) {
                            updateOfferButton.setDisable(false);
                        } else if (tableOffers.getSelectionModel().getSelectedItem().getPrice() == Integer.parseInt(newValuePriceTextField)) {
                            updateOfferButton.setDisable(true);
                        }
                    } catch (Exception ignored) {
                    }
                });

                clientComboBox.valueProperty().addListener((observable1, oldValue1, newValueClient) -> {
                    try {
                        if (!tableOffers.getSelectionModel().getSelectedItem().getClient().equals(newValueClient.getLastName())) {
                            updateOfferButton.setDisable(false);
                        } else if (tableOffers.getSelectionModel().getSelectedItem().getClient().equals(newValueClient.getLastName())) {
                            updateOfferButton.setDisable(true);
                        }
                    } catch (Exception ignored) {
                    }
                });

                realtorComboBox.valueProperty().addListener((observable1, oldValue1, newValueRealtor) -> {
                    try {
                        if (!tableOffers.getSelectionModel().getSelectedItem().getRealtor().equals(newValueRealtor.getLastName())) {
                            updateOfferButton.setDisable(false);
                        } else if (tableOffers.getSelectionModel().getSelectedItem().getClient().equals(newValueRealtor.getLastName())) {
                            updateOfferButton.setDisable(true);
                        }
                    } catch (Exception ignored) {
                    }
                });

                typeRealEstateComboBox.valueProperty().addListener((observable1, oldValue1, newValueTypeRealEstate) -> {
                    try {
                        if (!tableOffers.getSelectionModel().getSelectedItem().getRealEstate().contains(newValueTypeRealEstate)) {
                            updateOfferButton.setDisable(false);
                        } else if (tableOffers.getSelectionModel().getSelectedItem().getRealEstate().contains(newValueTypeRealEstate)) {
                            updateOfferButton.setDisable(true);
                        }
                    } catch (Exception ignored) {
                    }
                });

                realEstateComboBox.valueProperty().addListener((observable1, oldValue1, newValueRealEstate) -> {
                    try {
                        if (!tableOffers.getSelectionModel().getSelectedItem().getRealEstate().equals(newValueRealEstate.toString())) {
                            updateOfferButton.setDisable(false);
                        } else if (tableOffers.getSelectionModel().getSelectedItem().getRealEstate().equals(newValueRealEstate.toString())) {
                            updateOfferButton.setDisable(true);
                        }
                    } catch (Exception ignored) {
                    }
                });

                //установка значения клиента выбранного объекта в соответствующий выпадающий список
                if (tableOffers.getSelectionModel().getSelectedItem().getClient() != null) {
                    //получаем айди клиента из списка всех клиентов
                    for (int i = 0; i < listOfClients.size(); i++) {
                        if (tableOffers.getSelectionModel().getSelectedItem().getClient().equals(listOfClients.get(i).getLastName())) {
                            //устанавливаем в выпадающий список клиента
                            clientComboBox.getSelectionModel().select(i);
                        }
                    }
                }

                //установка значения риэлтора выбранного объекта в соответствующий выпадающий список
                if (tableOffers.getSelectionModel().getSelectedItem().getRealtor() != null) {
                    //получаем айди клиента из списка всех клиентов
                    for (int i = 0; i < listOfRealtors.size(); i++) {
                        if (tableOffers.getSelectionModel().getSelectedItem().getRealtor().equals(listOfRealtors.get(i).getLastName())) {
                            //устанавливаем в выпадающий список клиента
                            realtorComboBox.getSelectionModel().select(i);
                        }
                    }
                }

                //проверка на то какой объект недвижимости указан в предложении и установка этого типа недвижимоти в выпадающий список
                if (tableOffers.getSelectionModel().getSelectedItem().getRealEstate().contains(HOME)) {
                    typeRealEstateComboBox.setValue(HOME);
                    for (int i = 0; i < listRealEstates.size(); i++) {
                        if (tableOffers.getSelectionModel().getSelectedItem().getRealEstate().contains(listRealEstates.get(i).getHome())) {
                            System.out.println(listRealEstates.get(i).getHome());
                            realEstateComboBox.getSelectionModel().select(i);
                        }
                    }

                } else if (tableOffers.getSelectionModel().getSelectedItem().getRealEstate().contains(FLAT)) {
                    typeRealEstateComboBox.setValue(FLAT);
                    for (int i = 0; i < listRealEstates.size(); i++) {
                        if (tableOffers.getSelectionModel().getSelectedItem().getRealEstate().contains(listRealEstates.get(i).getFlat())) {
                            realEstateComboBox.getSelectionModel().select(i);
                        }
                    }

                } else if (tableOffers.getSelectionModel().getSelectedItem().getRealEstate().contains(LAND)) {
                    typeRealEstateComboBox.setValue(LAND);
                    for (int i = 0; i < listRealEstates.size(); i++) {
                        if (tableOffers.getSelectionModel().getSelectedItem().getRealEstate().contains(listRealEstates.get(i).getLand())) {
                            realEstateComboBox.getSelectionModel().select(i );
                        }
                    }
                }

                //установка цены выбранного объекта в текстовое поле
                priceTextField.setText(String.valueOf(tableOffers.getSelectionModel().getSelectedItem().getPrice()));

                createOfferButton.setDisable(true);
                updateOfferButton.setDisable(true);
                deleteOfferButton.setDisable(false);

            } else {
                createOfferButton.setDisable(false);
                updateOfferButton.setDisable(true);
                deleteOfferButton.setDisable(true);
            }
        });
    }

    /**
     * Метод для добавление нового предложения
     */
    @FXML
    private void createOffer() {

        //проверка на то что все поля заполнены
        if (clientComboBox.getValue() != null &&
                realtorComboBox.getValue() != null &&
                typeRealEstateComboBox.getValue() != null &&
                realEstateComboBox.getValue() != null &&
                !priceTextField.getText().isEmpty()) {

            //SQL запрос для добавления нового предложения в базу данных
            String insertOffer = String.format("INSERT INTO %s(%s, %s, %s, %s) VALUES (?,?,?,?);",
                    "offers",
                    "client",
                    "realtor",
                    "realEstate",
                    "price");

            try {
                PreparedStatement addOfferStatement = new DatabaseHandler().createDbConnection().prepareStatement(insertOffer);
                addOfferStatement.setString(1, clientComboBox.getValue().getLastName());
                addOfferStatement.setString(2, realtorComboBox.getValue().getLastName());
                addOfferStatement.setString(3, realEstateComboBox.getValue().toString());
                addOfferStatement.setInt(4, Integer.parseInt(priceTextField.getText()));

                //выполнение запроса
                addOfferStatement.executeUpdate();

                //открываем диалоговое окно для уведомления об успешном добавлении
                showAlert(
                        "Операция успешно выполнена",
                        "Новое предложение успешно добавлено!",
                        Alert.AlertType.INFORMATION);

                //обнуляем текстовые поля после добавления клиента в базу
                clearTextFieldsAndComboBox();

                tableOffers.setItems(createListOfOffers(getDataFromDB(TABLE_OFFERS)));

            } catch (SQLException e) {
                //открываем диалоговое окно для уведомления об ошибке
                showAlert(
                        "Ошибка добавления нового предложения",
                        "Предложение не было добавлено в базу. Повторите еще раз",
                        Alert.AlertType.ERROR);
            }
        } else {
            //открываем диалоговое окно для уведомления об ошибке
            showAlert(
                    "Ошибка добавления нового предложения",
                    "Все поля являются обязательными к заполнению.",
                    Alert.AlertType.ERROR);
        }

    }


    /**
     * Метод для редактирования предложения
     */
    @FXML
    private void updateOffer() {

    }

    /**
     * Метод для удаления предложения из таблицы
     */
    @FXML
    private void deleteOffer() {
        //SQL запрос на удаление предложения
        String deleteOffer = String.format("DELETE FROM %s WHERE %s=?", TABLE_OFFERS, TABLE_OFFERS_ID);

        try {
            PreparedStatement deleteOfferStatement = new DatabaseHandler().createDbConnection().prepareStatement(deleteOffer);
            //установка значений для вставки в запрос
            deleteOfferStatement.setInt(1, idSelectedOffer);
            //выполнение запроса на удаление
            deleteOfferStatement.executeUpdate();

            //открываем диалоговое окно для уведомления об успешном удалении
            showAlert(
                    "Операция успешно выполнена",
                    "Удаление предложения выполнено успешно!",
                    Alert.AlertType.INFORMATION);

            //обнуляем текстовые поля и выпадающие списки после удаления
            clearTextFieldsAndComboBox();

            tableOffers.setItems(createListOfOffers(getDataFromDB(TABLE_OFFERS)));

        } catch (SQLException e) {
            //открываем диалоговое окно для уведомления об ошибке
            showAlert(
                    "Ошибка удаления предложения",
                    String.format("Возникла ошибка при удалении предложения с ID = %d", idSelectedOffer),
                    Alert.AlertType.ERROR
            );
        }

    }

    /**
     * Метод для перехода на главный экран при нажатии на кнопку "Назад"
     *
     * @param event нажатие на кнопку "Назад"
     */
    @FXML
    private void goHomeScreen(ActionEvent event) {
        ((Node) event.getSource()).getScene().getWindow().hide();
        //переход на главный экран
        Helper.openNewScreen(PATH_TO_HOME_SCREEN);
    }

    /**
     * Метод для обнуления текстовых полей и выпадающих списков
     */
    private void clearTextFieldsAndComboBox() {
        clientComboBox.setValue(null);
        realtorComboBox.setValue(null);
        typeRealEstateComboBox.setValue(null);
        realEstateComboBox.setValue(null);
        priceTextField.setText("");
    }

    /**
     * Метод для получения информации из базы данных о клиенте с указанной фамилией
     *
     * @param lastName фамилия клиента
     * @return Объект Client с данными, найденными в базе
     */
//    private Client getClientFromDB(String lastName) {
//        //SQL запрос на выбор данных объекта с указанной фамилией из таблицы 'clients'
//        String selectClient = String.format("SELECT * FROM %s WHERE lastName=?", "clients");
//        Client client = new Client();
//        ResultSet resultSet;
//        try {
//            PreparedStatement ps = new DatabaseHandler().createDbConnection().prepareStatement(selectClient);
//            ps.setString(1, lastName);
//            //выполняем запрос и сохраняем полученные значения в resultSet
//            resultSet = ps.executeQuery();
//
//            while (resultSet.next()) {
//                client.setLastName(resultSet.getString(2));
//                client.setFirstName(resultSet.getString(3));
//                client.setMiddleName(resultSet.getString(4));
//                client.setPhoneNumber(resultSet.getString(5));
//                client.setEmail(resultSet.getString(6));
//            }
//        } catch (SQLException e) {
//            //открываем диалоговое окно для уведомления об ошибке
//            showAlert(
//                    "Ошибка получения данных из таблицы клиентов",
//                    "Данные не получены из базы. Проверьте подключение к базе!",
//                    Alert.AlertType.ERROR);
//        }
//
//        return client;
//    }

    /**
     * Метод для получения информации из базы данных о риэлторе с указанной фамилией
     *
     * @param lastName фамилия риэлтора
     * @return Объект Realtor с данными, найденными в базе
     */
//    private Realtor getRealtorFromDB(String lastName) {
//        //SQL запрос на выбор данных объекта с указанной фамилией из таблицы 'realtors'
//        String selectRealtor = String.format("SELECT * FROM %s WHERE lastName=?", "realtors");
//        Realtor realtor = new Realtor();
//        ResultSet resultSet;
//        try {
//            PreparedStatement ps = new DatabaseHandler().createDbConnection().prepareStatement(selectRealtor);
//            ps.setString(1, lastName);
//            //выполняем запрос и сохраняем полученные значения в resultSet
//            resultSet = ps.executeQuery();
//
//            while (resultSet.next()) {
//                realtor.setLastName(resultSet.getString(2));
//                realtor.setFirstName(resultSet.getString(3));
//                realtor.setMiddleName(resultSet.getString(4));
//                realtor.setCommissionPart(resultSet.getInt(5));
//            }
//        } catch (SQLException e) {
//            //открываем диалоговое окно для уведомления об ошибке
//            showAlert(
//                    "Ошибка получения данных из таблицы риэлторов",
//                    "Данные не получены из базы. Проверьте подключение к базе!",
//                    Alert.AlertType.ERROR);
//        }
//
//        return realtor;
//    }

    /**
     * Метод для получения информации из базы данных о доме
     *
     * @param home информация о доме
     * @return Объект Home с данными, найденными в базе
     */
//    private Home getHomeFromDB(String home) {
//        //SQL запрос на выбор данных объекта с указанной фамилией из таблицы 'clients'
//        String selectClient = String.format("SELECT * FROM %s WHERE lastName=?", "clients");
//        Client client = new Client();
//        ResultSet resultSet;
//        try {
//            PreparedStatement ps = new DatabaseHandler().createDbConnection().prepareStatement(selectClient);
//            ps.setString(1, lastName);
//            //выполняем запрос и сохраняем полученные значения в resultSet
//            resultSet = ps.executeQuery();
//
//            while (resultSet.next()) {
//                client.setLastName(resultSet.getString(2));
//                client.setFirstName(resultSet.getString(3));
//                client.setMiddleName(resultSet.getString(4));
//                client.setPhoneNumber(resultSet.getString(5));
//                client.setEmail(resultSet.getString(6));
//            }
//        } catch (SQLException e) {
//            //открываем диалоговое окно для уведомления об ошибке
//            showAlert(
//                    "Ошибка получения данных из таблицы клиентов",
//                    "Данные не получены из базы. Проверьте подключение к базе!",
//                    Alert.AlertType.ERROR);
//        }
//
//        return client;
//    }


    /**
     * Метод для формирования ObservableList с объектами класса Client из набора данных
     *
     * @param resultSet набор данных из таблицы клиентов
     * @return ObservableList с объектами класса Client
     */
    private ObservableList<Client> createListOfClients(ResultSet resultSet) {
        ObservableList<Client> list = FXCollections.observableArrayList();
        try {
            while (resultSet.next()) {
                //создаем нового клиента
                Client client = new Client();
                client.setLastName(resultSet.getString("lastName"));
                client.setFirstName(resultSet.getString("firstName"));
                client.setMiddleName(resultSet.getString("middleName"));
                //добавляем клиента в список
                list.add(client);
            }
        } catch (SQLException e) {
            //открываем диалоговое окно для уведомления об ошибке
            showAlert(
                    "Ошибка формирования коллекции клиентов",
                    "Возникла ошибка при формировании списка клиентов из набора данных, полученных из базы",
                    Alert.AlertType.ERROR);
        }
        return list;
    }

    /**
     * Метод для формирования ObservableList с объектами класса Realtor из набора данных
     *
     * @param resultSet Набор данных из таблицы риэлторов
     * @return ObservableList с объектами класса Realtor
     */
    private ObservableList<Realtor> createListOfRealtors(ResultSet resultSet) {
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
            showAlert(
                    "Ошибка формирования коллекции риэлторов",
                    "Возникла ошибка при формировании списка риэлторов из набора данных, полученных из базы",
                    Alert.AlertType.ERROR);
        }
        return list;
    }

    /**
     * Метод для формирования ObservableList с объектами класса Home из набора данных
     *
     * @param resultSet набор данных из таблицы 'home'
     * @return ObservableList с объектами класса Home
     */
    private ObservableList<Home> createListOfHomes(ResultSet resultSet) {
        ObservableList<Home> list = FXCollections.observableArrayList();
        try {
            while (resultSet.next()) {
                //Создание объекта "Дом"
                Home home = new Home(
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getDouble(6),
                        resultSet.getDouble(7),
                        resultSet.getInt(8),
                        resultSet.getInt(9),
                        resultSet.getDouble(10)
                );
                //добавляем объект в список
                list.add(home);
            }
        } catch (SQLException e) {
            //открываем диалоговое окно для уведомления об ошибке
            showAlert(
                    "Ошибка формирования коллекции объектов недвижимости 'Дом'",
                    "Возникла ошибка при формировании списка объектов недвижимости 'Дом' из набора данных, полученных из базы",
                    Alert.AlertType.ERROR);
        }
        return list;
    }

    /**
     * Метод для формирования ObservableList с объектами класса Flat из набора данных
     *
     * @param resultSet набор данных из таблицы 'flat'
     * @return ObservableList с объектами класса Flat
     */
    private ObservableList<Flat> createListOfFlats(ResultSet resultSet) {
        ObservableList<Flat> list = FXCollections.observableArrayList();
        try {
            while (resultSet.next()) {
                //Создание объекта "Квартира"
                Flat flat = new Flat(
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getDouble(6),
                        resultSet.getDouble(7),
                        resultSet.getInt(8),
                        resultSet.getInt(9),
                        resultSet.getDouble(10)
                );
                //добавляем объект в список
                list.add(flat);
            }
        } catch (SQLException e) {
            //открываем диалоговое окно для уведомления об ошибке
            showAlert(
                    "Ошибка формирования коллекции объектов недвижимости 'Квартира'",
                    "Возникла ошибка при формировании списка объектов недвижимости 'Квартира' из набора данных, полученных из базы",
                    Alert.AlertType.ERROR);
        }
        return list;
    }

    /**
     * Метод для формирования ObservableList с объектами класса Land из набора данных
     *
     * @param resultSet набор данных из таблицы 'land'
     * @return ObservableList с объектами класса Land
     */
    private ObservableList<Land> createListOfLands(ResultSet resultSet) {
        ObservableList<Land> list = FXCollections.observableArrayList();
        try {
            while (resultSet.next()) {
                //Создание объекта "Земля"
                Land land = new Land(
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getDouble(6),
                        resultSet.getDouble(7),
                        resultSet.getDouble(8)
                );
                //добавляем объект в список
                list.add(land);
            }
        } catch (SQLException e) {
            //открываем диалоговое окно для уведомления об ошибке
            showAlert(
                    "Ошибка формирования коллекции объектов недвижимости 'Земля'",
                    "Возникла ошибка при формировании списка объектов недвижимости 'Земля' из набора данных, полученных из базы",
                    Alert.AlertType.ERROR);
        }
        return list;
    }

    /**
     * Метод для формирования ObservableList с объектами Offer из набора данных
     *
     * @param resultSet набор данных из таблицы 'offers'
     * @return ObservableList с объектами класса Offer
     */
    private ObservableList<Offer> createListOfOffers(ResultSet resultSet) {
        ObservableList<Offer> list = FXCollections.observableArrayList();
        idOffersFromDatabase = new ArrayList<>();
        try {
            while (resultSet.next()) {

                //Создание объекта "Предложение"
                Offer offer = new Offer(
                        resultSet.getString("client"),
                        resultSet.getString("realtor"),
                        resultSet.getString("realEstate"),
                        resultSet.getInt("price")
                );
                //добавляем предложение в список
                list.add(offer);
                //добавляем ID предложения в список
                idOffersFromDatabase.add(resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            //открываем диалоговое окно для уведомления об ошибке
            showAlert(
                    "Ошибка формирования коллекции 'Предложение'",
                    "Возникла ошибка при формировании списка 'Предложений' из набора данных, полученных из базы",
                    Alert.AlertType.ERROR);
        }
        return list;
    }

    /**
     * Метод для получения содержимого таблицы из базы данных
     *
     * @param tableName название таблицы в базе данных
     * @return ResultSet - набор данных из таблицы
     */
    private ResultSet getDataFromDB(String tableName) {
        //SQL запрос на выбор всех данных из таблицы
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
                    Alert.AlertType.ERROR);
        }

        return resultSet;
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
//        alert.initOwner(this.tableOffers.getScene().getWindow());
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
