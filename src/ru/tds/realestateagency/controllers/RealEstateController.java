package ru.tds.realestateagency.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.tds.realestateagency.DatabaseHandler;
import ru.tds.realestateagency.Helper;
import ru.tds.realestateagency.entities.Flat;
import ru.tds.realestateagency.entities.Home;
import ru.tds.realestateagency.entities.Land;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class RealEstateController {

    //константы для работы с таблицей `home`
    private static final String HOME_TABLE = "home";
    private static final String HOME_ID = "id";
    private static final String HOME_CITY_COLUMN = "city";
    private static final String HOME_STREET_COLUMN = "street";
    private static final String HOME_HOME_NUMBER_COLUMN = "homeNumber";
    private static final String HOME_FLAT_NUMBER_COLUMN = "flatNumber";
    private static final String HOME_LATITUDE_COLUMN = "latitude";
    private static final String HOME_LONGITUDE_COLUMN = "longitude";
    private static final String HOME_NUMBER_OF_FLOORS_COLUMN = "numberOfFloors";
    private static final String HOME_NUMBER_OF_ROOMS_COLUMN = "numberOfRooms";
    private static final String HOME_SQUARE_COLUMN = "square";

    //константы для работы с таблицей `flat`
    private static final String FLAT_TABLE = "flat";
    private static final String FLAT_ID = "id";
    private static final String FLAT_CITY_COLUMN = "city";
    private static final String FLAT_STREET_COLUMN = "street";
    private static final String FLAT_HOME_NUMBER_COLUMN = "homeNumber";
    private static final String FLAT_FLAT_NUMBER_COLUMN = "flatNumber";
    private static final String FLAT_LATITUDE_COLUMN = "latitude";
    private static final String FLAT_LONGITUDE_COLUMN = "longitude";
    private static final String FLAT_FLOOR_COLUMN = "floor";
    private static final String FLAT_NUMBER_OF_ROOMS_COLUMN = "numberOfRooms";
    private static final String FLAT_SQUARE_COLUMN = "square";

    //константы для работы с таблицей `land`
    private static final String LAND_TABLE = "land";
    private static final String LAND_ID_COLUMN = "id";
    private static final String LAND_CITY_COLUMN = "city";
    private static final String LAND_STREET_COLUMN = "street";
    private static final String LAND_HOME_NUMBER_COLUMN = "homeNumber";
    private static final String LAND_FLAT_NUMBER_COLUMN = "flatNumber";
    private static final String LAND_LATITUDE_COLUMN = "latitude";
    private static final String LAND_LONGITUDE_COLUMN = "longitude";
    private static final String LAND_SQUARE_COLUMN = "square";

    @FXML
    private AnchorPane mainPane;
    @FXML
    private Label headerTypeBuildingLabel;
    @FXML
    private Button goHomeScreenButton;
    @FXML
    private TextField searchTextField;
    @FXML
    private Button showHomesButton;
    @FXML
    private Button showFlatsButton;
    @FXML
    private Button showLandsButton;
    @FXML
    private StackPane stackPane;

    /*
     Элементы интерфейса для экрана "Дом"
     */
    @FXML
    private AnchorPane homesAnchorPane;
    //Таблица и поля
    @FXML
    private TableView<Home> tableHomes;
    @FXML
    private TableColumn<Home, String> homeTableColumnCity;
    @FXML
    private TableColumn<Home, String> homeTableColumnStreet;
    @FXML
    private TableColumn<Home, String> homeTableColumnHomeNumber;
    @FXML
    private TableColumn<Home, String> homeTableColumnFlatNumber;
    @FXML
    private TableColumn<Home, Double> homeTableColumnLatitude;
    @FXML
    private TableColumn<Home, Double> homeTableColumnLongitude;
    @FXML
    private TableColumn<Home, Integer> homeTableColumnNumberOfFloors;
    @FXML
    private TableColumn<Home, Integer> homeTableColumnNumberOfRooms;
    @FXML
    private TableColumn<Home, Double> homeTableColumnSquare;
    //Текстовые поля
    @FXML
    private TextField homeTextFieldSquare;
    @FXML
    private TextField homeTextFieldNumberOfRooms;
    @FXML
    private TextField homeTextFieldLatitude;
    @FXML
    private TextField homeTextFieldLongitude;
    @FXML
    private TextField homeTextFieldCity;
    @FXML
    private TextField homeTextFieldStreet;
    @FXML
    private TextField homeTextFieldHomeNumber;
    @FXML
    private TextField homeTextFieldNumberOfFloors;
    @FXML
    private TextField homeTextFieldFlatNumber;
    //Кнопки
    @FXML
    private Button createHomeButton;
    @FXML
    private Button updateHomeButton;
    @FXML
    private Button deleteHomeButton;
    private ArrayList<Integer> idHomesFromDatabaseArrayList; // arrayList со всеми id для домов из базы данных
    private int idSelectedHome; //id из базы данных для выбранного в таблице дома

    /*
    Элементы интерфейса для экрана "Земля"
     */
    @FXML
    private AnchorPane landsAnchorPane;
    //Таблица и поля
    @FXML
    private TableView<Land> tableLands;
    @FXML
    private TableColumn<Land, String> landTableColumnCity;
    @FXML
    private TableColumn<Land, String> landTableColumnStreet;
    @FXML
    private TableColumn<Land, String> landTableColumnHomeNumber;
    @FXML
    private TableColumn<Land, String> landTableColumnFlatNumber;
    @FXML
    private TableColumn<Land, Double> landTableColumnLatitude;
    @FXML
    private TableColumn<Land, Double> landTableColumnLongitude;
    @FXML
    private TableColumn<Land, Double> landTableColumnSquare;
    //Текстовые поля
    @FXML
    private TextField landTextFieldCity;
    @FXML
    private TextField landTextFieldStreet;
    @FXML
    private TextField landTextFieldHomeNumber;
    @FXML
    private TextField landTextFieldFlatNumber;
    @FXML
    private TextField landTextFieldLatitude;
    @FXML
    private TextField landTextFieldLongitude;
    @FXML
    private TextField landTextFieldSquare;
    //Кнопки
    @FXML
    private Button createLandButton;
    @FXML
    private Button updateLandButton;
    @FXML
    private Button deleteLandButton;
    private ArrayList<Integer> idLandsFromDatabaseArrayList; // arrayList со всеми id для типа объекта недвижимости "Земля" из базы данных
    private int idSelectedLand; //id из базы данных для выбранного в таблице дома


    //Элементы интерфейса для экрана "Квартира"
    @FXML
    private AnchorPane flatsAnchorPane;
    //Таблица и поля
    @FXML
    private TableView<Flat> tableFlats;
    @FXML
    private TableColumn<Flat, String> flatTableColumnCity;
    @FXML
    private TableColumn<Flat, String> flatTableColumnStreet;
    @FXML
    private TableColumn<Flat, String> flatTableColumnHomeNumber;
    @FXML
    private TableColumn<Flat, String> flatTableColumnFlatNumber;
    @FXML
    private TableColumn<Flat, Double> flatTableColumnLatitude;
    @FXML
    private TableColumn<Flat, Double> flatTableColumnLongitude;
    @FXML
    private TableColumn<Flat, Integer> flatTableColumnFloor;
    @FXML
    private TableColumn<Flat, Integer> flatTableColumnNumberOfRooms;
    @FXML
    private TableColumn<Flat, Double> flatTableColumnSquare;

    //Текстовые поля
    @FXML
    private TextField flatTextFieldCity;
    @FXML
    private TextField flatTextFieldStreet;
    @FXML
    private TextField flatTextFieldHomeNumber;
    @FXML
    private TextField flatTextFieldFlatNumber;
    @FXML
    private TextField flatTextFieldLatitude;
    @FXML
    private TextField flatTextFieldLongitude;
    @FXML
    private TextField flatTextFieldFloor;
    @FXML
    private TextField flatTextFieldNumberOfRooms;
    @FXML
    private TextField flatTextFieldSquare;
    //Кнопки
    @FXML
    private Button createFlatButton;
    @FXML
    private Button updateFlatButton;
    @FXML
    private Button deleteFlatButton;
    private ArrayList<Integer> idFlatsFromDatabaseArrayList; // arrayList со всеми id для квартир из базы данных
    private int idSelectedFlat; //id из базы данных для выбранной в таблице квартиры

    private ObservableList<TextField> listOfHomeTextFields;
    private ObservableList<TextField> listOfFlatTextFields;
    private ObservableList<TextField> listOfLandTextFields;

    @FXML
    public void initialize() {

        //Кнопки
        updateHomeButton.setDisable(true);
        deleteHomeButton.setDisable(true);

        updateFlatButton.setDisable(true);
        deleteFlatButton.setDisable(true);

        updateLandButton.setDisable(true);
        deleteLandButton.setDisable(true);


        //формирование коллекций с текстовыми полями для каждого типа объекта недвижимости
        listOfHomeTextFields = createListOfHomesTextFields();
        listOfFlatTextFields = createListOfFlatTextFields();
        listOfLandTextFields = createListOfLandTextFields();

        //Обнуление текстовых полей и выделенных строк в таблице при нажатии на любую часть экрана вне области этих объектов
        mainPane.setOnMousePressed(event -> {
            clearTextFields(listOfHomeTextFields);
            clearTextFields(listOfFlatTextFields);
            clearTextFields(listOfLandTextFields);
            tableHomes.getSelectionModel().clearSelection();
            tableFlats.getSelectionModel().clearSelection();
            tableLands.getSelectionModel().clearSelection();
        });

        showHomesButton.setStyle("-fx-background-color: #0d47a1");
        headerTypeBuildingLabel.setText("Главная > Объекты недвижимости > Дом");
        homesAnchorPane.toFront();
        stackPane.setBackground(new Background(new BackgroundFill(Color.rgb(255, 255, 255), CornerRadii.EMPTY, Insets.EMPTY)));

        //определение колонок таблицы с соответствующими полями объекта "Дом"
        homeTableColumnCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        homeTableColumnStreet.setCellValueFactory(new PropertyValueFactory<>("street"));
        homeTableColumnHomeNumber.setCellValueFactory(new PropertyValueFactory<>("homeNumber"));
        homeTableColumnFlatNumber.setCellValueFactory(new PropertyValueFactory<>("flatNumber"));
        homeTableColumnLatitude.setCellValueFactory(new PropertyValueFactory<>("latitude"));
        homeTableColumnLongitude.setCellValueFactory(new PropertyValueFactory<>("longitude"));
        homeTableColumnNumberOfFloors.setCellValueFactory(new PropertyValueFactory<>("numberOfFloors"));
        homeTableColumnNumberOfRooms.setCellValueFactory(new PropertyValueFactory<>("numberOfRooms"));
        homeTableColumnSquare.setCellValueFactory(new PropertyValueFactory<>("square"));

        //заполняем таблицу данным из БД
        tableHomes.setItems(createListOfHomes(getHomeTableContent()));

        //Заносим данные выделенного объекта "Дом" в текстовые поля при клике на объект в таблице
        tableHomes.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {

                createHomeButton.setDisable(true);
                updateHomeButton.setDisable(false);
                deleteHomeButton.setDisable(false);

                //устанавливаем значения выделенного объекта в текстовые поля
                homeTextFieldCity.setText(tableHomes.getSelectionModel().getSelectedItem().getCity());
                homeTextFieldStreet.setText(tableHomes.getSelectionModel().getSelectedItem().getStreet());
                homeTextFieldHomeNumber.setText(tableHomes.getSelectionModel().getSelectedItem().getHomeNumber());
                homeTextFieldFlatNumber.setText(tableHomes.getSelectionModel().getSelectedItem().getFlatNumber());
                homeTextFieldLatitude.setText(String.valueOf(tableHomes.getSelectionModel().getSelectedItem().getLatitude()));
                homeTextFieldLongitude.setText(String.valueOf(tableHomes.getSelectionModel().getSelectedItem().getLongitude()));
                homeTextFieldNumberOfFloors.setText(String.valueOf(tableHomes.getSelectionModel().getSelectedItem().getNumberOfFloors()));
                homeTextFieldNumberOfRooms.setText(String.valueOf(tableHomes.getSelectionModel().getSelectedItem().getNumberOfRooms()));
                homeTextFieldSquare.setText(String.valueOf(tableHomes.getSelectionModel().getSelectedItem().getSquare()));

                idSelectedHome = idHomesFromDatabaseArrayList.get(tableHomes.getSelectionModel().getSelectedIndex());
                System.out.println("Выбран объект, у которого id в базе = " + idSelectedHome);
                System.out.println(idHomesFromDatabaseArrayList);

            } else {
                createHomeButton.setDisable(false);
                updateHomeButton.setDisable(true);
                deleteHomeButton.setDisable(true);
            }
        });


        //определение колонок таблицы с соответствующими полями объекта "Квартира"
        flatTableColumnCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        flatTableColumnStreet.setCellValueFactory(new PropertyValueFactory<>("street"));
        flatTableColumnHomeNumber.setCellValueFactory(new PropertyValueFactory<>("homeNumber"));
        flatTableColumnFlatNumber.setCellValueFactory(new PropertyValueFactory<>("flatNumber"));
        flatTableColumnLatitude.setCellValueFactory(new PropertyValueFactory<>("latitude"));
        flatTableColumnLongitude.setCellValueFactory(new PropertyValueFactory<>("longitude"));
        flatTableColumnFloor.setCellValueFactory(new PropertyValueFactory<>("floor"));
        flatTableColumnNumberOfRooms.setCellValueFactory(new PropertyValueFactory<>("numberOfRooms"));
        flatTableColumnSquare.setCellValueFactory(new PropertyValueFactory<>("square"));

        //заполняем таблицу данным из БД
        tableFlats.setItems(createListOfFlats(getFlatTableContent()));

        //Заносим данные выделенного объекта "Квартира" в текстовые поля при клике на объект в таблице
        tableFlats.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {

                createFlatButton.setDisable(true);
                updateFlatButton.setDisable(false);
                deleteFlatButton.setDisable(false);

                //устанавливаем значения выделенного объекта в текстовые поля
                flatTextFieldCity.setText(tableFlats.getSelectionModel().getSelectedItem().getCity());
                flatTextFieldStreet.setText(tableFlats.getSelectionModel().getSelectedItem().getStreet());
                flatTextFieldHomeNumber.setText(tableFlats.getSelectionModel().getSelectedItem().getHomeNumber());
                flatTextFieldFlatNumber.setText(tableFlats.getSelectionModel().getSelectedItem().getFlatNumber());
                flatTextFieldLatitude.setText(String.valueOf(tableFlats.getSelectionModel().getSelectedItem().getLatitude()));
                flatTextFieldLongitude.setText(String.valueOf(tableFlats.getSelectionModel().getSelectedItem().getLongitude()));
                flatTextFieldFloor.setText(String.valueOf(tableFlats.getSelectionModel().getSelectedItem().getFloor()));
                flatTextFieldNumberOfRooms.setText(String.valueOf(tableFlats.getSelectionModel().getSelectedItem().getNumberOfRooms()));
                flatTextFieldSquare.setText(String.valueOf(tableFlats.getSelectionModel().getSelectedItem().getSquare()));

                idSelectedFlat = idFlatsFromDatabaseArrayList.get(tableFlats.getSelectionModel().getSelectedIndex());
                System.out.println("Выбран объект, у которого id в базе = " + idSelectedFlat);
                System.out.println(idFlatsFromDatabaseArrayList);

            } else {
                createFlatButton.setDisable(false);
                updateFlatButton.setDisable(true);
                deleteFlatButton.setDisable(true);
            }
        });



        //определение колонок таблицы с соответствующими полями объекта "Квартира"
        landTableColumnCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        landTableColumnStreet.setCellValueFactory(new PropertyValueFactory<>("street"));
        landTableColumnHomeNumber.setCellValueFactory(new PropertyValueFactory<>("homeNumber"));
        landTableColumnFlatNumber.setCellValueFactory(new PropertyValueFactory<>("flatNumber"));
        landTableColumnLatitude.setCellValueFactory(new PropertyValueFactory<>("latitude"));
        landTableColumnLongitude.setCellValueFactory(new PropertyValueFactory<>("longitude"));
        landTableColumnSquare.setCellValueFactory(new PropertyValueFactory<>("square"));

        //заполняем таблицу данным из БД
        tableLands.setItems(createListOfLands(getLandTableContent()));

        //Заносим данные выделенного объекта "Квартира" в текстовые поля при клике на объект в таблице
        tableLands.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {

                createLandButton.setDisable(true);
                updateLandButton.setDisable(false);
                deleteLandButton.setDisable(false);

                //устанавливаем значения выделенного объекта в текстовые поля
                landTextFieldCity.setText(tableLands.getSelectionModel().getSelectedItem().getCity());
                landTextFieldStreet.setText(tableLands.getSelectionModel().getSelectedItem().getStreet());
                landTextFieldHomeNumber.setText(tableLands.getSelectionModel().getSelectedItem().getHomeNumber());
                landTextFieldFlatNumber.setText(tableLands.getSelectionModel().getSelectedItem().getFlatNumber());
                landTextFieldLatitude.setText(String.valueOf(tableLands.getSelectionModel().getSelectedItem().getLatitude()));
                landTextFieldLongitude.setText(String.valueOf(tableLands.getSelectionModel().getSelectedItem().getLongitude()));
                landTextFieldSquare.setText(String.valueOf(tableLands.getSelectionModel().getSelectedItem().getSquare()));

                idSelectedLand = idLandsFromDatabaseArrayList.get(tableLands.getSelectionModel().getSelectedIndex());
                System.out.println("Выбран объект, у которого id в базе = " + idSelectedLand);
                System.out.println(idLandsFromDatabaseArrayList);

            } else {
                createLandButton.setDisable(false);
                updateLandButton.setDisable(true);
                deleteLandButton.setDisable(true);
            }
        });

    }


    /**
     * Метод для смены вкладок внутри экрана при нажатии на соответствующий тип объекта недвижимости
     *
     * @param actionEvent нажатие на кнопку
     */
    public void switchContent(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(showHomesButton)) {
            headerTypeBuildingLabel.setText("Главная > Объекты недвижимости > Дом");
            showLandsButton.setStyle("");
            showFlatsButton.setStyle("");
            showHomesButton.setStyle("-fx-background-color: #0d47a1");
            homesAnchorPane.toFront();
        } else if (actionEvent.getSource().equals(showLandsButton)) {
            headerTypeBuildingLabel.setText("Главная > Объекты недвижимости > Земля");
            showHomesButton.setStyle("");
            showFlatsButton.setStyle("");
            showLandsButton.setStyle("-fx-background-color: #0d47a1");
            landsAnchorPane.toFront();
        } else if (actionEvent.getSource().equals(showFlatsButton)) {
            headerTypeBuildingLabel.setText("Главная > Объекты недвижимости > Квартира");
            showHomesButton.setStyle("");
            showLandsButton.setStyle("");
            showFlatsButton.setStyle("-fx-background-color: #0d47a1");
            flatsAnchorPane.toFront();
        }
    }

    /**
     * Метод для перехода на главный экран при нажитии на кнопку "Назад"
     *
     * @param event нажатие на кнопку "Назад"
     */
    public void goHomeScreen(ActionEvent event) {
        ((Node) event.getSource()).getScene().getWindow().hide();
        //переход на главный экран
        Helper.changeScreen("/ru/tds/realestateagency/views/main.fxml");
    }


    /**
     * Метод для добавления нового объекта недвижимости типа "Дом" в базу данных
     */
    public void createHome() {

        Home home = new Home();

        if (!homeTextFieldCity.getText().isEmpty() ||
                !homeTextFieldStreet.getText().isEmpty() ||
                !homeTextFieldHomeNumber.getText().isEmpty() ||
                !homeTextFieldFlatNumber.getText().isEmpty() ||
                !homeTextFieldLatitude.getText().isEmpty() ||
                !homeTextFieldLongitude.getText().isEmpty() ||
                !homeTextFieldNumberOfFloors.getText().isEmpty() ||
                !homeTextFieldNumberOfRooms.getText().isEmpty() ||
                !homeTextFieldSquare.getText().isEmpty()
        ) {

            home.setCity(homeTextFieldCity.getText());
            home.setStreet(homeTextFieldStreet.getText());
            home.setHomeNumber(homeTextFieldHomeNumber.getText());
            home.setFlatNumber(homeTextFieldFlatNumber.getText());

            if (!homeTextFieldNumberOfFloors.getText().isEmpty()) {
                home.setNumberOfFloors(Integer.parseInt(homeTextFieldNumberOfFloors.getText()));
            }
            if (!homeTextFieldNumberOfRooms.getText().isEmpty()) {
                home.setNumberOfRooms(Integer.parseInt(homeTextFieldNumberOfRooms.getText()));
            }
            if (!homeTextFieldSquare.getText().isEmpty()) {
                home.setSquare(Double.valueOf(homeTextFieldSquare.getText()));
            }

            if (!homeTextFieldLatitude.getText().isEmpty()) {
                if (isCorrectLatitude(homeTextFieldLatitude)) {
                    home.setLatitude(Double.valueOf(homeTextFieldLatitude.getText()));
                } else {
                    showModalWindow(
                            "Ошибка добавления нового объекта 'Дом' ",
                            "Широта может принимать значения от -90 до 90",
                            Alert.AlertType.ERROR);
                    return;
                }
            }


            if (!homeTextFieldLongitude.getText().isEmpty()) {
                if (isCorrectLongitude(homeTextFieldLongitude)) {
                    home.setLongitude(Double.valueOf(homeTextFieldLongitude.getText()));
                } else {
                    showModalWindow(
                            "Ошибка добавления нового объекта 'Дом' ",
                            "Долгота может принимать значения от -180 до 180",
                            Alert.AlertType.ERROR);
                    return;
                }
            }

            System.out.println(home);

            //SQL запрос для добавления нового клиента в базу данных
            String insertHome = "INSERT INTO " +
                    HOME_TABLE + "(" +
                    HOME_CITY_COLUMN + ", " +
                    HOME_STREET_COLUMN + ", " +
                    HOME_HOME_NUMBER_COLUMN + ", " +
                    HOME_FLAT_NUMBER_COLUMN + ", " +
                    HOME_LATITUDE_COLUMN + ", " +
                    HOME_LONGITUDE_COLUMN + ", " +
                    HOME_NUMBER_OF_FLOORS_COLUMN + ", " +
                    HOME_NUMBER_OF_ROOMS_COLUMN + ", " +
                    HOME_SQUARE_COLUMN + ")" +
                    " VALUES (?,?,?,?,?,?,?,?,?);";

            try {
                PreparedStatement addHomeStatement = new DatabaseHandler().createDbConnection().prepareStatement(insertHome);

                //установка значений для вставки в запрос
                addHomeStatement.setString(1, home.getCity());
                addHomeStatement.setString(2, home.getStreet());
                addHomeStatement.setString(3, home.getHomeNumber());
                addHomeStatement.setString(4, home.getFlatNumber());
                addHomeStatement.setDouble(5, home.getLatitude());
                addHomeStatement.setDouble(6, home.getLongitude());
                addHomeStatement.setInt(7, home.getNumberOfFloors());
                addHomeStatement.setInt(8, home.getNumberOfRooms());
                addHomeStatement.setDouble(9, home.getSquare());

                //выполнение запроса
                addHomeStatement.executeUpdate();

                //открываем диалоговое окно для уведомления об успешном добавлении
                showModalWindow(
                        "Операция успешно выполнена",
                        "Объект недвижимости 'Дом' успешно добавлен!",
                        Alert.AlertType.INFORMATION);

                //обновление таблицы после создания нового объекта
                tableHomes.setItems(createListOfHomes(getHomeTableContent()));

                //обнуляем текстовые поля после добавления клиента в базу
                clearTextFields(createListOfHomesTextFields());

                //Поиск объекта недвижимости типа "Дом" адресу
//                findClientByFullName();

            } catch (SQLException e) {
                //открываем диалоговое окно для уведомления об ошибке
                showModalWindow(
                        "Ошибка добавления нового объекта недвижимости 'Дом'",
                        "Объект недвижимости 'Дом'  не был добавлен в базу. Повторите еще раз",
                        Alert.AlertType.ERROR);
            }

        } else {
            showModalWindow(
                    "Ошибка добавления нового объекта 'Дом' ",
                    "Ниодно поле объекта не заполнено. Для добавления нового объекта необходимо заполнить хотя бы одно поле",
                    Alert.AlertType.ERROR);
        }
    }

    /**
     * Метод для обновления объекта недвижимости типа "Дом" в базе данных
     */
    public void updateHome() {
        //SQL запрос для обновления объекта недвижимости типа 'Дом'
        String updateHomeRequest =
                "UPDATE " + HOME_TABLE +
                        " SET " + HOME_CITY_COLUMN + "=?,"
                        + HOME_STREET_COLUMN + "=?,"
                        + HOME_HOME_NUMBER_COLUMN + "=?,"
                        + HOME_FLAT_NUMBER_COLUMN + "=?,"
                        + HOME_LATITUDE_COLUMN + "=?, "
                        + HOME_LONGITUDE_COLUMN + "=?, "
                        + HOME_NUMBER_OF_FLOORS_COLUMN + "=?, "
                        + HOME_NUMBER_OF_ROOMS_COLUMN + "=?, "
                        + HOME_SQUARE_COLUMN + "=? " +
                        " WHERE " + HOME_ID + "=?";

        try {
            PreparedStatement updateHomeStatement = new DatabaseHandler().createDbConnection().prepareStatement(updateHomeRequest);

            //установка значений для вставки в запрос
            updateHomeStatement.setString(1, homeTextFieldCity.getText());
            updateHomeStatement.setString(2, homeTextFieldStreet.getText());
            updateHomeStatement.setString(3, homeTextFieldHomeNumber.getText());
            updateHomeStatement.setString(4, homeTextFieldFlatNumber.getText());
            updateHomeStatement.setInt(7, Integer.parseInt(homeTextFieldNumberOfFloors.getText()));
            updateHomeStatement.setInt(8, Integer.parseInt(homeTextFieldNumberOfRooms.getText()));
            updateHomeStatement.setDouble(9, Double.parseDouble(homeTextFieldSquare.getText()));
            updateHomeStatement.setInt(10, idSelectedHome);

            if (isCorrectLatitude(homeTextFieldLatitude)) {
                updateHomeStatement.setDouble(5, Double.parseDouble(homeTextFieldLatitude.getText()));
            } else {
                //открываем диалоговое окно для уведомления об ошибке
                showModalWindow(
                        "Ошибка обновления объекта 'Дом' ",
                        "Широта может принимать значения от -90 до 90",
                        Alert.AlertType.ERROR);
                return;
            }
            if (isCorrectLongitude(homeTextFieldLongitude)) {
                updateHomeStatement.setDouble(6, Double.parseDouble(homeTextFieldLongitude.getText()));
            } else {
                //открываем диалоговое окно для уведомления об ошибке
                showModalWindow(
                        "Ошибка обновления объекта 'Дом' ",
                        "Долгота может принимать значения от -180 до 180",
                        Alert.AlertType.ERROR);
                return;
            }

            //выполнение SQL запроса
            updateHomeStatement.executeUpdate();

            //обновление таблицы после обновления
            tableHomes.setItems(createListOfHomes(getHomeTableContent()));

            //открываем диалоговое окно для уведомления об успешном обновлении
            showModalWindow(
                    "Операция успешно выполнена",
                    "Обновление объекта недвижимости типа 'Дом' выполнено успешно!",
                    Alert.AlertType.INFORMATION);

            //Обнуляем текстовые поля после обновления клиента
            clearTextFields(listOfHomeTextFields);

            //Поиск объекта недвижимости типа "Дом" адресу
//            findClientByFullName();

        } catch (SQLException e) {
            //открываем диалоговое окно для уведомления об ошибке
            showModalWindow(
                    "Ошибка обновления объекта недвижимости типа 'Дом'",
                    "Обновление объекта недвижимости типа 'Дом' не выполнено! Повторите еще раз",
                    Alert.AlertType.ERROR);
        }
    }

    /**
     * Метод для удаления объекта недвижимости типа "Дом" из базы данных
     */
    public void deleteHome() {

        //SQL запрос на удаление объекта недвижимости типа "Дом"
        String deleteHomeRequest = "DELETE FROM " + HOME_TABLE + " WHERE " + HOME_ID + "=?";

        try {
            PreparedStatement deleteHomeStatement = new DatabaseHandler().createDbConnection().prepareStatement(deleteHomeRequest);

            //установка значений для вставки в запрос
            deleteHomeStatement.setInt(1, idSelectedHome);
            //выполнение запроса на удаление
            deleteHomeStatement.executeUpdate();

            //обновление таблицы после удаления
            tableHomes.setItems(createListOfHomes(getHomeTableContent()));

            //открываем диалоговое окно для уведомления об успешном удалении
            showModalWindow(
                    "Операция успешно выполнена",
                    "Удаление объекта недвижимости типа 'Дом' выполнено успешно!",
                    Alert.AlertType.INFORMATION);

            //обнуляем текстовые поля после удаления объекта "Дом"
            clearTextFields(listOfHomeTextFields);

            //Поиск объекта недвижимости типа "Дом" адресу
//            findClientByFullName();

        } catch (SQLException e) {
            //открываем диалоговое окно для уведомления об ошибке
            showModalWindow(
                    "Ошибка удаления",
                    "Возникла ошибка при удалении объекта недвижимости типа 'Дом' с ID = " + idSelectedHome,
                    Alert.AlertType.ERROR
            );
        }

    }


    /**
     * Метод для обнуления текстовых полей
     */
    private void clearTextFields(ObservableList<TextField> listOfTextFields) {
        for (TextField textField : listOfTextFields) {
            textField.setText("");
        }
    }


    /**
     * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
     *  ВСПОМОГАТЕЛЬНЫЕ МЕТОДЫ ДЛЯ РАБОТЫ С ОБЪЕКТОМ НЕВДВИЖИМОСТИ "ДОМ"
     * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
     */


    /**
     * Метод для формирования ObservableList текстовых полей для объекта недвижимости типа "Дом"
     *
     * @return ObservableList с текстовыми полями для объекта недвижимости типа "Дом"
     */
    private ObservableList<TextField> createListOfHomesTextFields() {
        ObservableList<TextField> listOfTextField = FXCollections.observableArrayList();
        listOfTextField.add(homeTextFieldCity);
        listOfTextField.add(homeTextFieldStreet);
        listOfTextField.add(homeTextFieldHomeNumber);
        listOfTextField.add(homeTextFieldFlatNumber);
        listOfTextField.add(homeTextFieldLatitude);
        listOfTextField.add(homeTextFieldLongitude);
        listOfTextField.add(homeTextFieldNumberOfFloors);
        listOfTextField.add(homeTextFieldNumberOfRooms);
        listOfTextField.add(homeTextFieldSquare);
        return listOfTextField;
    }


    /**
     * Метод для проверки значения введенной широты
     *
     * @return true - если значение от -90 до 90, иначе - false
     */
    private boolean isCorrectLatitude(TextField latitudeTextField) {
        return Double.parseDouble(latitudeTextField.getText()) >= -90 && Double.parseDouble(latitudeTextField.getText()) <= 90;
    }

    /**
     * Метод для проверки значения введенной широты
     *
     * @return true - если значение от -90 до 90, иначе - false
     */
    private boolean isCorrectLongitude(TextField longitudeTextField) {
        return Double.parseDouble(longitudeTextField.getText()) >= -180 && Double.parseDouble(longitudeTextField.getText()) <= 180;
    }

    /**
     * Метод для получения содержимого таблицы домов из базы данных
     *
     * @return ResultSet - набор данных из таблицы
     */
    private ResultSet getHomeTableContent() {
        //SQL запрос на выбор всех данных из таблицы `home`
        String selectHomes = "SELECT * FROM " + HOME_TABLE;

        ResultSet resultSet = null;
        try {
            PreparedStatement ps = new DatabaseHandler().createDbConnection().prepareStatement(selectHomes);
            //выполняем запрос и сохраняем полученные значения в resultSet
            resultSet = ps.executeQuery();
        } catch (SQLException e) {
            //открываем диалоговое окно для уведомления об ошибке
            showModalWindow(
                    "Ошибка получения данных из таблицы " + HOME_TABLE,
                    "Данные не получены из базы. Проверьте подключение к базе!",
                    Alert.AlertType.ERROR);
        }

        return resultSet;
    }

    /**
     * Метод для формирования ObservableList из набора данных
     *
     * @param resultSet набор данных из таблицы `home`
     * @return ObservableList с объектами класса Home
     */
    private ObservableList<Home> createListOfHomes(ResultSet resultSet) {
        //создаем список клиентов
        ObservableList<Home> list = FXCollections.observableArrayList();
        idHomesFromDatabaseArrayList = new ArrayList<>();
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
                idHomesFromDatabaseArrayList.add(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            //открываем диалоговое окно для уведомления об ошибке
            showModalWindow(
                    "Ошибка формирования коллекции объектов недвижимости 'Дом'",
                    "Возникла ошибка при формировании списка объектов недвижимости 'Дом' из набора данных, полученных из базы",
                    Alert.AlertType.ERROR);
        }
        return list;
    }

    /**
     * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
     *  КОНЕЦ ВСПОМОГАТЕЛЬНЫХ МЕТОДОВ ДЛЯ РАБОТЫ С ОБЪЕКТОМ НЕВДВИЖИМОСТИ "ДОМ"
     * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
     */


    /**
     * Метод для добавления нового объекта недвижимости типа "Квартира" в базу данных
     *
     * @param actionEvent нажатие на кнопку "Создать"
     */
    public void createFlat(ActionEvent actionEvent) {

        Flat flat = new Flat();

        if (!flatTextFieldCity.getText().isEmpty() ||
                !flatTextFieldStreet.getText().isEmpty() ||
                !flatTextFieldHomeNumber.getText().isEmpty() ||
                !flatTextFieldFlatNumber.getText().isEmpty() ||
                !flatTextFieldLatitude.getText().isEmpty() ||
                !flatTextFieldLongitude.getText().isEmpty() ||
                !flatTextFieldFloor.getText().isEmpty() ||
                !flatTextFieldNumberOfRooms.getText().isEmpty() ||
                !flatTextFieldSquare.getText().isEmpty()
        ) {

            flat.setCity(flatTextFieldCity.getText());
            flat.setStreet(flatTextFieldStreet.getText());
            flat.setHomeNumber(flatTextFieldHomeNumber.getText());
            flat.setFlatNumber(flatTextFieldFlatNumber.getText());

            if (!flatTextFieldFloor.getText().isEmpty()) {
                flat.setFloor(Integer.parseInt(flatTextFieldFloor.getText()));
            }
            if (!flatTextFieldNumberOfRooms.getText().isEmpty()) {
                flat.setNumberOfRooms(Integer.parseInt(flatTextFieldNumberOfRooms.getText()));
            }
            if (!flatTextFieldSquare.getText().isEmpty()) {
                flat.setSquare(Double.valueOf(flatTextFieldSquare.getText()));
            }

            if (!flatTextFieldLatitude.getText().isEmpty()) {
                if (isCorrectLatitude(flatTextFieldLatitude)) {
                    flat.setLatitude(Double.valueOf(flatTextFieldLatitude.getText()));
                } else {
                    showModalWindow(
                            "Ошибка добавления нового объекта 'Квартира' ",
                            "Широта может принимать значения от -90 до 90",
                            Alert.AlertType.ERROR);
                    return;
                }
            }


            if (!flatTextFieldLongitude.getText().isEmpty()) {
                if (isCorrectLongitude(flatTextFieldLongitude)) {
                    flat.setLongitude(Double.valueOf(flatTextFieldLongitude.getText()));
                } else {
                    showModalWindow(
                            "Ошибка добавления нового объекта 'Квартира' ",
                            "Долгота может принимать значения от -180 до 180",
                            Alert.AlertType.ERROR);
                    return;
                }
            }

            System.out.println(flat);

            //SQL запрос для добавления нового клиента в базу данных
            String insertFlat = "INSERT INTO " +
                    FLAT_TABLE + "(" +
                    FLAT_CITY_COLUMN + ", " +
                    FLAT_STREET_COLUMN + ", " +
                    FLAT_HOME_NUMBER_COLUMN + ", " +
                    FLAT_FLAT_NUMBER_COLUMN + ", " +
                    FLAT_LATITUDE_COLUMN + ", " +
                    FLAT_LONGITUDE_COLUMN + ", " +
                    FLAT_FLOOR_COLUMN + ", " +
                    FLAT_NUMBER_OF_ROOMS_COLUMN + ", " +
                    FLAT_SQUARE_COLUMN + ")" +
                    " VALUES (?,?,?,?,?,?,?,?,?);";

            try {
                PreparedStatement addHomeStatement = new DatabaseHandler().createDbConnection().prepareStatement(insertFlat);

                //установка значений для вставки в запрос
                addHomeStatement.setString(1, flat.getCity());
                addHomeStatement.setString(2, flat.getStreet());
                addHomeStatement.setString(3, flat.getHomeNumber());
                addHomeStatement.setString(4, flat.getFlatNumber());
                addHomeStatement.setDouble(5, flat.getLatitude());
                addHomeStatement.setDouble(6, flat.getLongitude());
                addHomeStatement.setInt(7, flat.getFloor());
                addHomeStatement.setInt(8, flat.getNumberOfRooms());
                addHomeStatement.setDouble(9, flat.getSquare());

                //выполнение запроса
                addHomeStatement.executeUpdate();

                //открываем диалоговое окно для уведомления об успешном добавлении
                showModalWindow(
                        "Операция успешно выполнена",
                        "Объект недвижимости 'Квартира' успешно добавлен!",
                        Alert.AlertType.INFORMATION);

                //обновление таблицы после создания нового объекта
                tableFlats.setItems(createListOfFlats(getFlatTableContent()));

                //обнуляем текстовые поля после добавления клиента в базу
                clearTextFields(createListOfFlatTextFields());

                //Поиск объекта недвижимости типа "Дом" адресу
//                findClientByFullName();

            } catch (SQLException e) {
                //открываем диалоговое окно для уведомления об ошибке
                showModalWindow(
                        "Ошибка добавления нового объекта недвижимости 'Квартира'",
                        "Объект недвижимости 'Квартира'  не был добавлен в базу. Повторите еще раз",
                        Alert.AlertType.ERROR);
            }

        } else {
            showModalWindow(
                    "Ошибка добавления нового объекта 'Квартира' ",
                    "Ниодно поле объекта не заполнено. Для добавления нового объекта необходимо заполнить хотя бы одно поле",
                    Alert.AlertType.ERROR);
        }

    }


    /**
     * Метод для обновления объекта недвижимости типа "Квартира" в базе данных
     *
     * @param actionEvent нажатие на кнопку "Обновить"
     */
    public void updateFlat(ActionEvent actionEvent) {

        //SQL запрос для обновления объекта недвижимости типа 'Дом'
        String updateFlatRequest =
                "UPDATE " + FLAT_TABLE +
                        " SET " + FLAT_CITY_COLUMN + "=?,"
                        + FLAT_STREET_COLUMN + "=?,"
                        + FLAT_HOME_NUMBER_COLUMN + "=?,"
                        + FLAT_FLAT_NUMBER_COLUMN + "=?,"
                        + FLAT_LATITUDE_COLUMN + "=?, "
                        + FLAT_LONGITUDE_COLUMN + "=?, "
                        + FLAT_FLOOR_COLUMN + "=?, "
                        + FLAT_NUMBER_OF_ROOMS_COLUMN + "=?, "
                        + FLAT_SQUARE_COLUMN + "=? " +
                        " WHERE " + FLAT_ID + "=?";

        try {
            PreparedStatement updateFlatStatement = new DatabaseHandler().createDbConnection().prepareStatement(updateFlatRequest);

            //установка значений для вставки в запрос
            updateFlatStatement.setString(1, flatTextFieldCity.getText());
            updateFlatStatement.setString(2, flatTextFieldStreet.getText());
            updateFlatStatement.setString(3, flatTextFieldHomeNumber.getText());
            updateFlatStatement.setString(4, flatTextFieldFlatNumber.getText());
            updateFlatStatement.setInt(7, Integer.parseInt(flatTextFieldFloor.getText()));
            updateFlatStatement.setInt(8, Integer.parseInt(flatTextFieldNumberOfRooms.getText()));
            updateFlatStatement.setDouble(9, Double.parseDouble(flatTextFieldSquare.getText()));
            updateFlatStatement.setInt(10, idSelectedFlat);

            if (isCorrectLatitude(flatTextFieldLatitude)) {
                updateFlatStatement.setDouble(5, Double.parseDouble(flatTextFieldLatitude.getText()));
            } else {
                //открываем диалоговое окно для уведомления об ошибке
                showModalWindow(
                        "Ошибка обновления объекта 'Квартира' ",
                        "Широта может принимать значения от -90 до 90",
                        Alert.AlertType.ERROR);
                return;
            }
            if (isCorrectLongitude(flatTextFieldLongitude)) {
                updateFlatStatement.setDouble(6, Double.parseDouble(flatTextFieldLongitude.getText()));
            } else {
                //открываем диалоговое окно для уведомления об ошибке
                showModalWindow(
                        "Ошибка обновления объекта 'Квартира' ",
                        "Долгота может принимать значения от -180 до 180",
                        Alert.AlertType.ERROR);
                return;
            }

            //выполнение SQL запроса
            updateFlatStatement.executeUpdate();

            //обновление таблицы после обновления
            tableFlats.setItems(createListOfFlats(getFlatTableContent()));

            //открываем диалоговое окно для уведомления об успешном обновлении
            showModalWindow(
                    "Операция успешно выполнена",
                    "Обновление объекта недвижимости типа 'Квартира' выполнено успешно!",
                    Alert.AlertType.INFORMATION);

            //Обнуляем текстовые поля после обновления клиента
            clearTextFields(listOfFlatTextFields);

            //Поиск объекта недвижимости типа "Дом" адресу
//            findClientByFullName();

        } catch (SQLException e) {
            //открываем диалоговое окно для уведомления об ошибке
            showModalWindow(
                    "Ошибка обновления объекта недвижимости типа 'Квартира'",
                    "Обновление объекта недвижимости типа 'Квартира' не выполнено! Повторите еще раз",
                    Alert.AlertType.ERROR);
        }

    }

    /**
     * Метод для удаления объекта недвижимости типа "Квартира" из базы данных
     *
     * @param actionEvent нажатие на кнопку "Удалить"
     */
    public void deleteFlat(ActionEvent actionEvent) {

        //SQL запрос на удаление объекта недвижимости типа "Квартира"
        String deleteFlatRequest = "DELETE FROM " + FLAT_TABLE + " WHERE " + FLAT_ID + "=?";

        try {
            PreparedStatement deleteFlatStatement = new DatabaseHandler().createDbConnection().prepareStatement(deleteFlatRequest);

            //установка значений для вставки в запрос
            deleteFlatStatement.setInt(1, idSelectedFlat);
            //выполнение запроса на удаление
            deleteFlatStatement.executeUpdate();

            //обновление таблицы после удаления
            tableFlats.setItems(createListOfFlats(getFlatTableContent()));

            //открываем диалоговое окно для уведомления об успешном удалении
            showModalWindow(
                    "Операция успешно выполнена",
                    "Удаление объекта недвижимости типа 'Квартира' выполнено успешно!",
                    Alert.AlertType.INFORMATION);

            //обнуляем текстовые поля после удаления объекта "Дом"
            clearTextFields(listOfFlatTextFields);

            //Поиск объекта недвижимости типа "Дом" адресу
//            findClientByFullName();

        } catch (SQLException e) {
            //открываем диалоговое окно для уведомления об ошибке
            showModalWindow(
                    "Ошибка удаления",
                    "Возникла ошибка при удалении объекта недвижимости типа 'Квартира' с ID = " + idSelectedFlat,
                    Alert.AlertType.ERROR
            );
        }

    }

    /**
     * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
     *  ВСПОМОГАТЕЛЬНЫЕ МЕТОДЫ ДЛЯ РАБОТЫ С ОБЪЕКТОМ НЕВДВИЖИМОСТИ "КВАРТИРА"
     * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
     */

    /**
     * Метод для формирования ObservableList из набора данных
     *
     * @param resultSet набор данных из таблицы `flat`
     * @return ObservableList с объектами класса Flat
     */
    private ObservableList<Flat> createListOfFlats(ResultSet resultSet) {
        //создаем список клиентов
        ObservableList<Flat> list = FXCollections.observableArrayList();
        idFlatsFromDatabaseArrayList = new ArrayList<>();
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
                idFlatsFromDatabaseArrayList.add(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            //открываем диалоговое окно для уведомления об ошибке
            showModalWindow(
                    "Ошибка формирования коллекции объектов недвижимости 'Квартира'",
                    "Возникла ошибка при формировании списка объектов недвижимости 'Квартира' из набора данных, полученных из базы",
                    Alert.AlertType.ERROR);
        }
        return list;
    }

    /**
     * Метод для получения содержимого таблицы домов из базы данных
     *
     * @return ResultSet - набор данных из таблицы
     */
    private ResultSet getFlatTableContent() {
        //SQL запрос на выбор всех данных из таблицы `flat`
        String selectFlats = "SELECT * FROM " + FLAT_TABLE;

        ResultSet resultSet = null;
        try {
            PreparedStatement ps = new DatabaseHandler().createDbConnection().prepareStatement(selectFlats);
            //выполняем запрос и сохраняем полученные значения в resultSet
            resultSet = ps.executeQuery();
        } catch (SQLException e) {
            //открываем диалоговое окно для уведомления об ошибке
            showModalWindow(
                    "Ошибка получения данных из таблицы " + FLAT_TABLE,
                    "Данные не получены из базы. Проверьте подключение к базе!",
                    Alert.AlertType.ERROR);
        }

        return resultSet;
    }

    /**
     * Метод для формирования ObservableList текстовых полей для объекта недвижимости типа "Квартира"
     *
     * @return ObservableList с текстовыми полями для объекта недвижимости типа "Квартира"
     */
    private ObservableList<TextField> createListOfFlatTextFields() {
        ObservableList<TextField> listOfTextField = FXCollections.observableArrayList();
        listOfTextField.add(flatTextFieldCity);
        listOfTextField.add(flatTextFieldStreet);
        listOfTextField.add(flatTextFieldHomeNumber);
        listOfTextField.add(flatTextFieldFlatNumber);
        listOfTextField.add(flatTextFieldLatitude);
        listOfTextField.add(flatTextFieldLongitude);
        listOfTextField.add(flatTextFieldFloor);
        listOfTextField.add(flatTextFieldNumberOfRooms);
        listOfTextField.add(flatTextFieldSquare);
        return listOfTextField;
    }

    /**
     * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
     *  КОНЕЦ ВСПОМОГАТЕЛЬНЫХ МЕТОДОВ ДЛЯ РАБОТЫ С ОБЪЕКТОМ НЕВДВИЖИМОСТИ "КВАРТИРА"
     * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
     */


    /**
     * Метод для добавления нового объекта недвижимости типа "Земля" в базу данных
     *
     * @param actionEvent нажатие на кнопку "Создать"
     */
    public void createLand(ActionEvent actionEvent) {

        Land land = new Land();

        if (!landTextFieldCity.getText().isEmpty() ||
                !landTextFieldStreet.getText().isEmpty() ||
                !landTextFieldHomeNumber.getText().isEmpty() ||
                !landTextFieldFlatNumber.getText().isEmpty() ||
                !landTextFieldLatitude.getText().isEmpty() ||
                !landTextFieldLongitude.getText().isEmpty() ||
                !landTextFieldSquare.getText().isEmpty()
        ) {

            land.setCity(landTextFieldCity.getText());
            land.setStreet(landTextFieldStreet.getText());
            land.setHomeNumber(landTextFieldHomeNumber.getText());
            land.setFlatNumber(landTextFieldFlatNumber.getText());

            if (!landTextFieldLatitude.getText().isEmpty()) {
                if (isCorrectLatitude(landTextFieldLatitude)) {
                    land.setLatitude(Double.valueOf(landTextFieldLatitude.getText()));
                } else {
                    showModalWindow(
                            "Ошибка добавления нового объекта 'Земля' ",
                            "Широта может принимать значения от -90 до 90",
                            Alert.AlertType.ERROR);
                    return;
                }
            }


            if (!landTextFieldLongitude.getText().isEmpty()) {
                if (isCorrectLongitude(landTextFieldLongitude)) {
                    land.setLongitude(Double.valueOf(landTextFieldLongitude.getText()));
                } else {
                    showModalWindow(
                            "Ошибка добавления нового объекта 'Земля' ",
                            "Долгота может принимать значения от -180 до 180",
                            Alert.AlertType.ERROR);
                    return;
                }
            }

            System.out.println(land);

            //SQL запрос для добавления нового клиента в базу данных
            String insertLand = "INSERT INTO " +
                    LAND_TABLE + "(" +
                    LAND_CITY_COLUMN + ", " +
                    LAND_STREET_COLUMN + ", " +
                    LAND_HOME_NUMBER_COLUMN + ", " +
                    LAND_FLAT_NUMBER_COLUMN + ", " +
                    LAND_LATITUDE_COLUMN + ", " +
                    LAND_LONGITUDE_COLUMN + ", " +
                    LAND_SQUARE_COLUMN + ")" +
                    " VALUES (?,?,?,?,?,?,?);";

            try {
                PreparedStatement addLandStatement = new DatabaseHandler().createDbConnection().prepareStatement(insertLand);

                //установка значений для вставки в запрос
                addLandStatement.setString(1, land.getCity());
                addLandStatement.setString(2, land.getStreet());
                addLandStatement.setString(3, land.getHomeNumber());
                addLandStatement.setString(4, land.getFlatNumber());
                addLandStatement.setDouble(5, land.getLatitude());
                addLandStatement.setDouble(6, land.getLongitude());
                addLandStatement.setDouble(7, land.getSquare());

                //выполнение запроса
                addLandStatement.executeUpdate();

                //открываем диалоговое окно для уведомления об успешном добавлении
                showModalWindow(
                        "Операция успешно выполнена",
                        "Объект недвижимости 'Земля' успешно добавлен!",
                        Alert.AlertType.INFORMATION);

                //обновление таблицы после создания нового объекта
                tableLands.setItems(createListOfLands(getLandTableContent()));

                //обнуляем текстовые поля после добавления клиента в базу
                clearTextFields(createListOfFlatTextFields());

                //Поиск объекта недвижимости типа "Дом" адресу
//                findClientByFullName();

            } catch (SQLException e) {
                //открываем диалоговое окно для уведомления об ошибке
                showModalWindow(
                        "Ошибка добавления нового объекта недвижимости 'Земля'",
                        "Объект недвижимости 'Земля'  не был добавлен в базу. Повторите еще раз",
                        Alert.AlertType.ERROR);
            }

        } else {
            showModalWindow(
                    "Ошибка добавления нового объекта недвижимости 'Земля' ",
                    "Ниодно поле объекта не заполнено. Для добавления нового объекта необходимо заполнить хотя бы одно поле",
                    Alert.AlertType.ERROR);
        }

    }

    /**
     * Метод для обновления объекта недвижимости типа "Земля" в базе данных
     *
     * @param actionEvent нажатие на кнопку "Обновить"
     */
    public void updateLand(ActionEvent actionEvent) {

        //SQL запрос для обновления объекта недвижимости типа 'Дом'
        String updateFlatRequest =
                "UPDATE " + LAND_TABLE +
                        " SET " + LAND_CITY_COLUMN + "=?,"
                        + LAND_STREET_COLUMN + "=?,"
                        + LAND_HOME_NUMBER_COLUMN + "=?,"
                        + LAND_FLAT_NUMBER_COLUMN + "=?,"
                        + LAND_LATITUDE_COLUMN + "=?, "
                        + LAND_LONGITUDE_COLUMN + "=?, "
                        + LAND_SQUARE_COLUMN + "=? " +
                        " WHERE " + LAND_ID_COLUMN + "=?";

        try {
            PreparedStatement updateFlatStatement = new DatabaseHandler().createDbConnection().prepareStatement(updateFlatRequest);

            //установка значений для вставки в запрос
            updateFlatStatement.setString(1, landTextFieldCity.getText());
            updateFlatStatement.setString(2, landTextFieldStreet.getText());
            updateFlatStatement.setString(3, landTextFieldHomeNumber.getText());
            updateFlatStatement.setString(4, landTextFieldFlatNumber.getText());
            updateFlatStatement.setDouble(7, Double.parseDouble(landTextFieldSquare.getText()));
            updateFlatStatement.setInt(8, idSelectedLand);

            if (isCorrectLatitude(landTextFieldLatitude)) {
                updateFlatStatement.setDouble(5, Double.parseDouble(landTextFieldLatitude.getText()));
            } else {
                //открываем диалоговое окно для уведомления об ошибке
                showModalWindow(
                        "Ошибка обновления объекта 'Земля' ",
                        "Широта может принимать значения от -90 до 90",
                        Alert.AlertType.ERROR);
                return;
            }
            if (isCorrectLongitude(landTextFieldLongitude)) {
                updateFlatStatement.setDouble(6, Double.parseDouble(landTextFieldLongitude.getText()));
            } else {
                //открываем диалоговое окно для уведомления об ошибке
                showModalWindow(
                        "Ошибка обновления объекта 'Земля' ",
                        "Долгота может принимать значения от -180 до 180",
                        Alert.AlertType.ERROR);
                return;
            }

            //выполнение SQL запроса
            updateFlatStatement.executeUpdate();

            //обновление таблицы после обновления
            tableLands.setItems(createListOfLands(getLandTableContent()));

            //открываем диалоговое окно для уведомления об успешном обновлении
            showModalWindow(
                    "Операция успешно выполнена",
                    "Обновление объекта недвижимости типа 'Земля' выполнено успешно!",
                    Alert.AlertType.INFORMATION);

            //Обнуляем текстовые поля после обновления клиента
            clearTextFields(listOfLandTextFields);

            //Поиск объекта недвижимости типа "Дом" адресу
//            findClientByFullName();

        } catch (SQLException e) {
            //открываем диалоговое окно для уведомления об ошибке
            showModalWindow(
                    "Ошибка обновления объекта недвижимости типа 'Земля'",
                    "Обновление объекта недвижимости типа 'Земля' не выполнено! Повторите еще раз",
                    Alert.AlertType.ERROR);
        }

    }

    /**
     * Метод для удаления объекта недвижимости типа "Земля" из базы данных
     *
     * @param actionEvent нажатие на кнопку "Удалить"
     */
    public void deleteLand(ActionEvent actionEvent) {

        //SQL запрос на удаление объекта недвижимости типа "Квартира"
        String deleteLandRequest = "DELETE FROM " + LAND_TABLE + " WHERE " + LAND_ID_COLUMN + "=?";

        try {
            PreparedStatement deleteLandStatement = new DatabaseHandler().createDbConnection().prepareStatement(deleteLandRequest);

            //установка значений для вставки в запрос
            deleteLandStatement.setInt(1, idSelectedLand);
            //выполнение запроса на удаление
            deleteLandStatement.executeUpdate();

            //обновление таблицы после удаления
            tableLands.setItems(createListOfLands(getLandTableContent()));

            //открываем диалоговое окно для уведомления об успешном удалении
            showModalWindow(
                    "Операция успешно выполнена",
                    "Удаление объекта недвижимости типа 'Земля' выполнено успешно!",
                    Alert.AlertType.INFORMATION);

            //обнуляем текстовые поля после удаления объекта "Дом"
            clearTextFields(listOfLandTextFields);

            //Поиск объекта недвижимости типа "Дом" адресу
//            findClientByFullName();

        } catch (SQLException e) {
            //открываем диалоговое окно для уведомления об ошибке
            showModalWindow(
                    "Ошибка удаления",
                    "Возникла ошибка при удалении объекта недвижимости типа 'Земля' с ID = " + idSelectedLand,
                    Alert.AlertType.ERROR
            );
        }

    }


    /**
     * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
     *  ВСПОМОГАТЕЛЬНЫЕ МЕТОДЫ ДЛЯ РАБОТЫ С ОБЪЕКТОМ НЕВДВИЖИМОСТИ "ЗЕМЛЯ"
     * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
     */


    /**
     * Метод для формирования ObservableList из набора данных
     *
     * @param resultSet набор данных из таблицы `flat`
     * @return ObservableList с объектами класса Flat
     */
    private ObservableList<Land> createListOfLands(ResultSet resultSet) {
        //создаем список клиентов
        ObservableList<Land> list = FXCollections.observableArrayList();
        idLandsFromDatabaseArrayList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                //Создание объекта "Квартира"
                Land land = new Land(
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getDouble(6),
                        resultSet.getDouble(7),
                        resultSet.getDouble(8)
                );
                //добавляем клиента в список
                list.add(land);
                idLandsFromDatabaseArrayList.add(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            //открываем диалоговое окно для уведомления об ошибке
            showModalWindow(
                    "Ошибка формирования коллекции объектов недвижимости 'Земля'",
                    "Возникла ошибка при формировании списка объектов недвижимости 'Земля' из набора данных, полученных из базы",
                    Alert.AlertType.ERROR);
        }
        return list;
    }

    /**
     * Метод для получения содержимого таблицы домов из базы данных
     *
     * @return ResultSet - набор данных из таблицы
     */
    private ResultSet getLandTableContent() {
        //SQL запрос на выбор всех данных из таблицы `flat`
        String selectFlats = "SELECT * FROM " + LAND_TABLE;

        ResultSet resultSet = null;
        try {
            PreparedStatement ps = new DatabaseHandler().createDbConnection().prepareStatement(selectFlats);
            //выполняем запрос и сохраняем полученные значения в resultSet
            resultSet = ps.executeQuery();
        } catch (SQLException e) {
            //открываем диалоговое окно для уведомления об ошибке
            showModalWindow(
                    "Ошибка получения данных из таблицы " + LAND_TABLE,
                    "Данные не получены из базы. Проверьте подключение к базе!",
                    Alert.AlertType.ERROR);
        }

        return resultSet;
    }


    /**
     * Метод для формирования ObservableList текстовых полей для объекта недвижимости типа "Земля"
     *
     * @return ObservableList с текстовыми полями для объекта недвижимости типа "Земля"
     */
    private ObservableList<TextField> createListOfLandTextFields() {
        ObservableList<TextField> listOfTextField = FXCollections.observableArrayList();
        listOfTextField.add(landTextFieldCity);
        listOfTextField.add(landTextFieldStreet);
        listOfTextField.add(landTextFieldHomeNumber);
        listOfTextField.add(landTextFieldFlatNumber);
        listOfTextField.add(landTextFieldLatitude);
        listOfTextField.add(landTextFieldLongitude);
        listOfTextField.add(landTextFieldSquare);
        return listOfTextField;
    }


    /**
     * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
     *  КОНЕЦ ВСПОМОГАТЕЛЬНЫХ МЕТОДОВ ДЛЯ РАБОТЫ С ОБЪЕКТОМ НЕВДВИЖИМОСТИ "ЗЕМЛЯ"
     * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
     */


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
        alert.initOwner(this.stackPane.getScene().getWindow());
        //установка типа модального окна
        alert.initModality(Modality.WINDOW_MODAL);

        if (alert.getAlertType().equals(Alert.AlertType.ERROR)) {
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
