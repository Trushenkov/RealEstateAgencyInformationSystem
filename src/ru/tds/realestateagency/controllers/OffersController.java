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
    private ComboBox<Client> clientsComboBox;
    @FXML
    private ComboBox<Realtor> realtorsComboBox;
    @FXML
    private ComboBox<String> typeRealEstateComboBox;
    @FXML
    private ComboBox<RealEstate> realEstateComboBox;
    @FXML
    private TextField priceTextField;

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


    /**
     * Метод для обнуления текстовых полей и списков
     */
    private void clearTextFieldsAndComboBox() {
        clientsComboBox.setValue(null);
        realtorsComboBox.setValue(null);
        typeRealEstateComboBox.setValue(null);
        realEstateComboBox.setValue(null);
        priceTextField.setText("");
    }

    @FXML
    private void goHomeScreen(ActionEvent event) {
        ((Node) event.getSource()).getScene().getWindow().hide();
        //переход на главный экран
        Helper.openNewScreen(PATH_TO_HOME_SCREEN);
    }


    @FXML
    void initialize() {

        //Обнуление текстовых полей и списков  при нажатии на область окна приложения
        mainPane.setOnMousePressed(event -> {
            clearTextFieldsAndComboBox();
            tableOffers.getSelectionModel().clearSelection();
        });

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

        //формируем коллекции объектов
        listRealEstates = FXCollections.observableArrayList();
        listOfClients = createListOfClients(getDataFromDB(TABLE_CLIENTS));
        listOfRealtors = createListOfRealtors(getDataFromDB(TABLE_REALTORS));
        listOfHomes = createListOfHomes(getDataFromDB(TABLE_HOMES));
        listOfFlats = createListOfFlats(getDataFromDB(TABLE_FLATS));
        listOfLands = createListOfLands(getDataFromDB(TABLE_LANDS));
        listOfOffers = createListOfOffers(getDataFromDB(TABLE_OFFERS));
        System.out.println(listOfOffers);

        ObservableList<String> listOfTypesRealEstate = FXCollections.observableArrayList(HOME, FLAT, LAND);

        clientsComboBox.setItems(listOfClients);
        realtorsComboBox.setItems(listOfRealtors);

        typeRealEstateComboBox.setItems(listOfTypesRealEstate);

        typeRealEstateComboBox.valueProperty().addListener((objectProperty, oldValue, newValue) -> {
            if (newValue != null) {
                switch (newValue) {
                    case HOME:
                        realEstateComboBox.getItems().clear();
                        for (Home home : listOfHomes) {
                            RealEstate realEstate = new RealEstate();
                            realEstate.setHome(home.toString());
                            listRealEstates.add(realEstate);
                        }
                        realEstateComboBox.setItems(listRealEstates);
                        break;
                    case FLAT:
                        realEstateComboBox.getItems().clear();
                        for (Flat flat : listOfFlats) {
                            RealEstate realEstate = new RealEstate();
                            realEstate.setFlat(flat.toString());
                            listRealEstates.add(realEstate);
                        }
                        realEstateComboBox.setItems(listRealEstates);
                        break;
                    case LAND:
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

        realEstateComboBox.setItems(listRealEstates);

        tableOffers.setItems(listOfOffers);

        tableOffers.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {

                idSelectedOffer = idOffersFromDatabase.get(tableOffers.getSelectionModel().getSelectedIndex());

                System.out.println(tableOffers.getSelectionModel().getSelectedItem().getClient());
                Client client = getClientFromDB(tableOffers.getSelectionModel().getSelectedItem().getClient());
                Realtor realtor = getRealtorFromDB(tableOffers.getSelectionModel().getSelectedItem().getRealtor());

//                clientsComboBox.setValue(client);
//                realtorsComboBox.setValue(realtor);
                clientsComboBox.getSelectionModel().select(client);
                realtorsComboBox.getSelectionModel().select(realtor);

                System.out.println(tableOffers.getSelectionModel().getSelectedItem().getRealEstate());

                if (tableOffers.getSelectionModel().getSelectedItem().getRealEstate().contains("Дом")) {
                    typeRealEstateComboBox.setValue("Дом");
                } else if (tableOffers.getSelectionModel().getSelectedItem().getRealEstate().contains("Квартира")) {
                    typeRealEstateComboBox.setValue("Квартира");
                } else if (tableOffers.getSelectionModel().getSelectedItem().getRealEstate().contains("Земля")) {
                    typeRealEstateComboBox.setValue("Земля");
                }

                priceTextField.setText(String.valueOf(tableOffers.getSelectionModel().getSelectedItem().getPrice()));

                System.out.println(tableOffers.getSelectionModel().getSelectedItem());
            }
        });
    }

    /**
     * Метод для добавление нового предложения
     */
    @FXML
    private void createOffer() {

        if (
                clientsComboBox.getValue() != null &&
                        realtorsComboBox.getValue() != null &&
                        realEstateComboBox.getValue() != null &&
                        !priceTextField.getText().isEmpty()
        ) {
            //тип объекта недвижимости
            String typeRealEstate = typeRealEstateComboBox.getSelectionModel().getSelectedItem();

            RealEstate realEstate;

            switch (typeRealEstate) {
                case HOME:
                    realEstate = new RealEstate();
                    System.out.println(realEstateComboBox.getSelectionModel().getSelectedItem());
                    realEstate.setHome(realEstateComboBox.getSelectionModel().getSelectedItem().getHome());
                    break;
                case FLAT:
                    realEstate = new RealEstate();
                    System.out.println(realEstateComboBox.getSelectionModel().getSelectedItem());
                    realEstate.setFlat(realEstateComboBox.getSelectionModel().getSelectedItem().getFlat());
                    break;
                case LAND:
                    realEstate = new RealEstate();
                    System.out.println(realEstateComboBox.getSelectionModel().getSelectedItem());
                    realEstate.setLand(realEstateComboBox.getSelectionModel().getSelectedItem().getLand());
                    break;
                default:
                    break;
            }

            //SQL запрос для добавления нового предложения в базу данных
            String insertOffer = String.format("INSERT INTO %s(%s, %s, %s, %s) VALUES (?,?,?,?);",
                    "offers",
                    "client",
                    "realtor",
                    "realEstate",
                    "price");


            try {
                PreparedStatement addOfferStatement = new DatabaseHandler().createDbConnection().prepareStatement(insertOffer);
                addOfferStatement.setString(1, clientsComboBox.getValue().getLastName());
                addOfferStatement.setString(2, realtorsComboBox.getValue().getLastName());
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
                e.printStackTrace();
                //открываем диалоговое окно для уведомления об ошибке
                showAlert(
                        "Ошибка добавления нового предложения",
                        "Предложение не было добавлено в базу. Повторите еще раз",
                        Alert.AlertType.ERROR);
            }
        } else {
            showAlert(
                    "Ошибка добавления нового предложения",
                    "Все поля являются обязательными к заполнению.",
                    Alert.AlertType.ERROR);
        }

    }

    @FXML
    private void updateOffer() {

    }

    @FXML
    private void deleteOffer() {

    }

    /**
     * Метод для получения информации из базы данных о клиенте с указанной фамилией
     *
     * @param lastName фамилия клиента
     * @return найденная информация о клиенте в базе
     */
    private Client getClientFromDB(String lastName) {
        //SQL запрос на выбор всех данных из таблицы `clients`
        String selectClient = String.format("SELECT * FROM %s WHERE lastName=?", "clients");
        Client client = new Client();
        ResultSet resultSet;
        try {
            PreparedStatement ps = new DatabaseHandler().createDbConnection().prepareStatement(selectClient);
            ps.setString(1, lastName);
            //выполняем запрос и сохраняем полученные значения в resultSet
            resultSet = ps.executeQuery();

            while (resultSet.next()) {
                client.setLastName(resultSet.getString(2));
                client.setFirstName(resultSet.getString(3));
                client.setMiddleName(resultSet.getString(4));
                client.setPhoneNumber(resultSet.getString(5));
                client.setEmail(resultSet.getString(6));
            }
        } catch (SQLException e) {
            //открываем диалоговое окно для уведомления об ошибке
            showAlert(
                    "Ошибка получения данных из таблицы клиентов",
                    "Данные не получены из базы. Проверьте подключение к базе!",
                    Alert.AlertType.ERROR);
        }


        return client;
    }


    /**
     * Метод для получения информации из базы данных о риэлторе с указанной фамилией
     *
     * @param lastName фамилия риэлтора
     * @return найденная информация о риэлторе в базе
     */
    private Realtor getRealtorFromDB(String lastName) {
        //SQL запрос на выбор всех данных из таблицы `clients`
        String selectRealtor = String.format("SELECT * FROM %s WHERE lastName=?", "realtors");
        Realtor realtor = new Realtor();
        ResultSet resultSet;
        try {
            PreparedStatement ps = new DatabaseHandler().createDbConnection().prepareStatement(selectRealtor);
            ps.setString(1, lastName);
            //выполняем запрос и сохраняем полученные значения в resultSet
            resultSet = ps.executeQuery();

            while (resultSet.next()) {
                realtor.setLastName(resultSet.getString(2));
                realtor.setFirstName(resultSet.getString(3));
                realtor.setMiddleName(resultSet.getString(4));
                realtor.setCommissionPart(resultSet.getInt(5));
            }
        } catch (SQLException e) {
            //открываем диалоговое окно для уведомления об ошибке
            showAlert(
                    "Ошибка получения данных из таблицы риэлторов",
                    "Данные не получены из базы. Проверьте подключение к базе!",
                    Alert.AlertType.ERROR);
        }

        return realtor;
    }

    /**
     * Метод для формирования ObservableList из набора данных
     *
     * @param resultSet набор данных из таблицы клиентов
     * @return ObservableList с объектами класса Client
     */
    private ObservableList<Client> createListOfClients(ResultSet resultSet) {
        //создаем список клиентов
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
     * Метод для формирования ObservableList из набора данных
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
     * Метод для формирования ObservableList из набора данных
     *
     * @param resultSet набор данных из таблицы 'home'
     * @return ObservableList с объектами класса Home
     */
    private ObservableList<Home> createListOfHomes(ResultSet resultSet) {
        //создаем список клиентов
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
                //добавляем клиента в список
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
     * Метод для формирования ObservableList из набора данных
     *
     * @param resultSet набор данных из таблицы 'flat'
     * @return ObservableList с объектами класса Flat
     */
    private ObservableList<Flat> createListOfFlats(ResultSet resultSet) {
        //создаем список клиентов
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
                //добавляем клиента в список
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
     * Метод для формирования ObservableList из набора данных
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
     * Метод для формирования ObservableList из набора данных
     *
     * @param resultSet набор данных из таблицы 'offers'
     * @return ObservableList с объектами класса Offer
     */
    private ObservableList<Offer> createListOfOffers(ResultSet resultSet) {
        //создаем список предложений
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
     * Метод для получения содержимого указанной таблицы из базы данных
     *
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
