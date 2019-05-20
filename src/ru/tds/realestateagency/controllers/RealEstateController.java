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
import ru.tds.realestateagency.entities.Home;

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
    private static final String FLAT_ID_COLUMN = "id";
    private static final String FLAT_CITY_COLUMN_COLUMN = "city";
    private static final String FLAT_STREET_COLUMN_COLUMN = "street";
    private static final String FLAT_HOME_NUMBER_COLUMN_COLUMN = "homeNumber";
    private static final String FLAT_FLAT_NUMBER_COLUMN_COLUMN = "flatNumber";
    private static final String FLAT_LATITUDE_COLUMN_COLUMN = "latitude";
    private static final String FLAT_LONGITUDE_COLUMN_COLUMN = "longitude";
    private static final String FLAT_FLOOR_COLUMN = "floor";
    private static final String FLAT_NUMBER_OF_ROOMS_COLUMN = "numberOfRooms";
    private static final String FLAT_SQUARE_COLUMN = "square";

    //константы для работы с таблицей `land`
    private static final String LAND_TABLE = "land";
    private static final String LAND_ID_COLUMN = "id";
    private static final String LAND_CITY_COLUMN_COLUMN = "city";
    private static final String LAND_STREET_COLUMN_COLUMN = "street";
    private static final String LAND_HOME_NUMBER_COLUMN_COLUMN = "homeNumber";
    private static final String LAND_FLAT_NUMBER_COLUMN_COLUMN = "flatNumber";
    private static final String LAND_LATITUDE_COLUMN_COLUMN = "latitude";
    private static final String LAND_LONGITUDE_COLUMN_COLUMN = "longitude";
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

    /*
    Элементы интерфейса для экрана "Земля"
     */
    @FXML
    private AnchorPane landsAnchorPane;
    //Таблица и поля
    @FXML
    private TableView<?> tableLands;
    @FXML
    private TableColumn<?, ?> landTableColumnCity;
    @FXML
    private TableColumn<?, ?> landTableColumnStreet;
    @FXML
    private TableColumn<?, ?> landTableColumnHomeNumber;
    @FXML
    private TableColumn<?, ?> landTableColumnFlatNumber;
    @FXML
    private TableColumn<?, ?> landTableColumnLatitude;
    @FXML
    private TableColumn<?, ?> landTableColumnLongitude;
    @FXML
    private TableColumn<?, ?> landTableColumnSquare;
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


    //Элементы интерфейса для экрана "Квартира"
    @FXML
    private AnchorPane flatsAnchorPane;
    //Таблица и поля
    @FXML
    private TableView<?> tableFlats;
    @FXML
    private TableColumn<?, ?> flatTableColumnCity;
    @FXML
    private TableColumn<?, ?> flatTableColumnStreet;
    @FXML
    private TableColumn<?, ?> flatTableColumnHomeNumber;
    @FXML
    private TableColumn<?, ?> flatTableColumnFlatNumber;
    @FXML
    private TableColumn<?, ?> flatTableColumnLatitude;
    @FXML
    private TableColumn<?, ?> flatTableColumnLongitude;
    @FXML
    private TableColumn<?, ?> flatTableColumnFloor;
    @FXML
    private TableColumn<?, ?> flatTableColumnNumberOfRooms;
    @FXML
    private TableColumn<?, ?> flatTableColumnSquare;

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

    ArrayList<Integer> idHomesFromDatabaseArrayList; // arrayList со всеми id для домов из базы данных

    private int idSelectedHome; //id из базы данных для выбранного в таблице дома


    @FXML
    public void initialize() {

        mainPane.setOnMousePressed(event -> {
            clearHomeTextFields();
            tableHomes.getSelectionModel().clearSelection();
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

//        listOfFlats = createListOfFlats(getFlatsTableContent());
//        listOfLands = createListOfLands(getLandsTableContent());

//        listOfHomes.addListener(new ListChangeListener<Home>() {
//            @Override
//            public void onChanged(Change<? extends Home> c) {
//                updateHomeTable();
//            }
//        });

        //заполняем таблицу данным из БД
        tableHomes.setItems(createListOfHomes(getHomeTableContent()));

        //Заносим данные выделенного объекта в текстовые поля при клике на объект в таблице
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
            } else {
                createHomeButton.setDisable(false);
                updateHomeButton.setDisable(true);
                deleteHomeButton.setDisable(true);
            }

            idSelectedHome = idHomesFromDatabaseArrayList.get(tableHomes.getSelectionModel().getSelectedIndex());
            System.out.println("Выбран объект, у которого id в базе = " + idSelectedHome);

            System.out.println(idHomesFromDatabaseArrayList);
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
     *
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
                if (isCorrectLatitude()) {
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
                if (isCorrectLongitude()) {
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
                clearHomeTextFields();

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
     * Метод для обнуления текстовых полей на вкладке "Дом"
     */
    private void clearHomeTextFields() {
        homeTextFieldCity.setText("");
        homeTextFieldStreet.setText("");
        homeTextFieldHomeNumber.setText("");
        homeTextFieldFlatNumber.setText("");
        homeTextFieldLatitude.setText("");
        homeTextFieldLongitude.setText("");
        homeTextFieldNumberOfFloors.setText("");
        homeTextFieldNumberOfRooms.setText("");
        homeTextFieldSquare.setText("");
    }

    /**
     * Метод для проверки значения введенной широты
     *
     * @return true - если значение от -90 до 90, иначе - false
     */
    private boolean isCorrectLatitude() {
        return Double.parseDouble(homeTextFieldLatitude.getText()) >= -90 && Double.parseDouble(homeTextFieldLatitude.getText()) <= 90;
    }

    /**
     * Метод для проверки значения введенной широты
     *
     * @return true - если значение от -90 до 90, иначе - false
     */
    private boolean isCorrectLongitude() {
        return Double.parseDouble(homeTextFieldLongitude.getText()) >= -180 && Double.parseDouble(homeTextFieldLongitude.getText()) <= 180;
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
     * @param resultSet набор данных из таблицы клиентов
     * @return ObservableList с объектами типа Client
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
     * Метод для заполнения таблицы данными из базы на вкладке объекта недвижимости "Дом"
     */
    private void updateHomeTable() {
        tableHomes.setItems(createListOfHomes(getHomeTableContent()));
    }


    /**
     * Метод для обновления объекта недвижимости типа "Дом" в базе данных
     *
     * @param actionEvent нажатие на кнопку "Обновить"
     */
    public void updateHome(ActionEvent actionEvent) {
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

            if (isCorrectLatitude()) {
                updateHomeStatement.setDouble(5, Double.parseDouble(homeTextFieldLatitude.getText()));
            } else {
                //открываем диалоговое окно для уведомления об ошибке
                showModalWindow(
                        "Ошибка добавления нового объекта 'Дом' ",
                        "Широта может принимать значения от -90 до 90",
                        Alert.AlertType.ERROR);
                return;
            }
            if (isCorrectLongitude()) {
                updateHomeStatement.setDouble(6, Double.parseDouble(homeTextFieldLongitude.getText()));
            } else {
                //открываем диалоговое окно для уведомления об ошибке
                showModalWindow(
                        "Ошибка добавления нового объекта 'Дом' ",
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
            clearHomeTextFields();

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

        //SQL запрос на удаление клиента
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
                    "Удаление объекта недвижимсти типа 'Дом' выполнено успешно!",
                    Alert.AlertType.INFORMATION);

            //обнуляем текстовые поля после удаления клиента
            clearHomeTextFields();

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
     * Метод для добавления нового объекта недвижимости типа "Земля" в базу данных
     *
     * @param actionEvent нажатие на кнопку "Создать"
     */
    public void createLand(ActionEvent actionEvent) {

    }

    /**
     * Метод для обновления объекта недвижимости типа "Земля" в базе данных
     *
     * @param actionEvent нажатие на кнопку "Обновить"
     */
    public void updateLand(ActionEvent actionEvent) {

    }

    /**
     * Метод для удаления объекта недвижимости типа "Земля" из базы данных
     *
     * @param actionEvent нажатие на кнопку "Удалить"
     */
    public void deleteLand(ActionEvent actionEvent) {

    }


    /**
     * Метод для добавления нового объекта недвижимости типа "Квартира" в базу данных
     *
     * @param actionEvent нажатие на кнопку "Создать"
     */
    public void createFlat(ActionEvent actionEvent) {

    }

    /**
     * Метод для обновления объекта недвижимости типа "Квартира" в базе данных
     *
     * @param actionEvent нажатие на кнопку "Обновить"
     */
    public void updateFlat(ActionEvent actionEvent) {

    }

    /**
     * Метод для удаления объекта недвижимости типа "Квартира" из базы данных
     *
     * @param actionEvent нажатие на кнопку "Удалить"
     */
    public void deleteFlat(ActionEvent actionEvent) {

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
