package ru.tds.realestateagency.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import java.util.function.Predicate;

/**
 * Класс-контроллер для обработки событий на экране "Объекты недвижимости"
 *
 * @author Трушенков Дмитрий
 */
public class RealEstateController {

    private static final String PATH_TO_MAIN_SCREEN = "/ru/tds/realestateagency/views/main.fxml";
    private static final String BUTTON_COLOR = "-fx-background-color: #0d47a1";

    private static final String TYPE_BUILDING_HOME_LABEL = "Главная > Объекты недвижимости > Дом";
    private static final String TYPE_BUILDING_LAND_LABEL = "Главная > Объекты недвижимости > Земля";
    private static final String TYPE_BUILDING_FLAT_LABEL = "Главная > Объекты недвижимости > Квартира";

    private static final String ERROR_ADD_HOME = "Ошибка добавления нового объекта недвижимости 'Дом' ";
    private static final String ERROR_UPDATE_HOME = "Ошибка обновления объекта недвижимости 'Дом' ";
    private static final String ERROR_DELETE_HOME = "Ошибка удаления объекта недвижимости 'Дом'";
    private static final String ERROR_ADD_FLAT = "Ошибка добавления нового объекта недвижимости 'Квартира' ";
    private static final String ERROR_UPDATE_FLAT = "Ошибка обновления объекта недвижимости 'Квартира' ";
    private static final String ERROR_DELETE_FLAT = "Ошибка удаления объекта недвижимости 'Квартира' ";
    private static final String ERROR_ADD_LAND = "Ошибка добавления нового объекта недвижимости 'Земля' ";
    private static final String ERROR_UPDATE_LAND = "Ошибка обновления объекта недвижимости 'Земля' ";
    private static final String ERROR_DELETE_LAND = "Ошибка удаления объекта недвижимости 'Земля' ";
    private static final String ERROR_LATITUDE = "Широта может принимать значения от -90 до 90";
    private static final String ERROR_LONGITUDE = "Долгота может принимать значения от -180 до 180";
    private static final String ERROR_EMPTY_FIELDS = "Необходимо заполнить хотя бы одно поле объекта";

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
    private static final String OPERATION_COMPLETED = "Операция успешно выполнена";


    @FXML
    private AnchorPane mainPane;
    @FXML
    private Label headerTypeBuildingLabel;
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
    private ArrayList<Integer> idHomesFromDatabaseArrayList;//список id объектов из таблицы 'home'
    private int idSelectedHome;//id выбранного в таблице объекта Home

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
    private ArrayList<Integer> idLandsFromDatabaseArrayList;//список id объектов из таблицы 'land'
    private int idSelectedLand;//id выбранного в таблице объекта Land


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
    private ArrayList<Integer> idFlatsFromDatabaseArrayList;// список id объектов из таблицы 'land'
    private int idSelectedFlat;//id выбранного в таблице объекта Flat

    private ObservableList<TextField> listOfHomeTextFields;//список текстовых полей на вкладке "Дом"
    private ObservableList<TextField> listOfFlatTextFields;//список текстовых полей на вкладке "Квартира"
    private ObservableList<TextField> listOfLandTextFields;//список текстовых полей на вкладке "Земля"

    @FXML
    public void initialize() {

        //Обнуление текстовых полей и отмена выбора строки в таблице при нажатии на область окна приложения 
        mainPane.setOnMousePressed(event -> {
            idSelectedHome = 0;
            idSelectedFlat = 0;
            idSelectedLand = 0;
            clearTextFields(listOfHomeTextFields);
            clearTextFields(listOfFlatTextFields);
            clearTextFields(listOfLandTextFields);
            tableHomes.getSelectionModel().clearSelection();
            tableFlats.getSelectionModel().clearSelection();
            tableLands.getSelectionModel().clearSelection();
            updateHomeButton.setDisable(true);
            updateFlatButton.setDisable(true);
            updateLandButton.setDisable(true);
        });

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

        showHomesButton.setStyle("-fx-background-color: #0d47a1");
        headerTypeBuildingLabel.setText(TYPE_BUILDING_HOME_LABEL);
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

        //отключение возможности сортировки полей таблицы объектов "Дом"
        homeTableColumnCity.setSortable(false);
        homeTableColumnStreet.setSortable(false);
        homeTableColumnHomeNumber.setSortable(false);
        homeTableColumnFlatNumber.setSortable(false);
        homeTableColumnLatitude.setSortable(false);
        homeTableColumnLongitude.setSortable(false);
        homeTableColumnNumberOfFloors.setSortable(false);
        homeTableColumnNumberOfRooms.setSortable(false);
        homeTableColumnSquare.setSortable(false);

        //заполняем таблицу данным из БД
        tableHomes.setItems(createListOfHomes(getHomeTableContent()));

        //Заносим данные выделенного объекта "Дом" в текстовые поля при клике на объект в таблице
        tableHomes.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedHome) -> {
            if (selectedHome != null) {

                idSelectedHome = idHomesFromDatabaseArrayList.get(tableHomes.getSelectionModel().getSelectedIndex());

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

                createHomeButton.setDisable(true);
                updateHomeButton.setDisable(true);
                deleteHomeButton.setDisable(false);

                //Слушатели для текстовых полей для проверки на изменение какого либо значения и предоставления возможности обновления дома
                homeTextFieldCity.textProperty().addListener((observable1, oldValue1, newValueCity) -> {
                    try {
                        if (!tableHomes.getSelectionModel().getSelectedItem().getCity().equals(newValueCity)) {
                            updateHomeButton.setDisable(false);
                        } else if (tableHomes.getSelectionModel().getSelectedItem().getCity().equals(newValueCity)) {
                            updateHomeButton.setDisable(true);
                        }
                    } catch (Exception ignored) {
                    }
                });

                homeTextFieldStreet.textProperty().addListener((observable1, oldValue1, newValueStreet) -> {
                    try {
                        if (!tableHomes.getSelectionModel().getSelectedItem().getStreet().equals(newValueStreet)) {
                            updateHomeButton.setDisable(false);
                        } else if (tableHomes.getSelectionModel().getSelectedItem().getStreet().equals(newValueStreet)) {
                            updateHomeButton.setDisable(true);
                        }
                    } catch (Exception ignored) {
                    }
                });

                homeTextFieldHomeNumber.textProperty().addListener((observable1, oldValue1, newValueHomeNumber) -> {
                    try {
                        if (!tableHomes.getSelectionModel().getSelectedItem().getHomeNumber().equals(newValueHomeNumber)) {
                            updateHomeButton.setDisable(false);
                        } else if (tableHomes.getSelectionModel().getSelectedItem().getHomeNumber().equals(newValueHomeNumber)) {
                            updateHomeButton.setDisable(true);
                        }
                    } catch (Exception ignored) {
                    }
                });

                homeTextFieldFlatNumber.textProperty().addListener((observable1, oldValue1, newValueFlatNumber) -> {
                    try {
                        if (!tableHomes.getSelectionModel().getSelectedItem().getFlatNumber().equals(newValueFlatNumber)) {
                            updateHomeButton.setDisable(false);
                        } else if (tableHomes.getSelectionModel().getSelectedItem().getFlatNumber().equals(newValueFlatNumber)) {
                            updateHomeButton.setDisable(true);
                        }
                    } catch (Exception ignored) {
                    }
                });

                homeTextFieldLatitude.textProperty().addListener((observable1, oldValue1, newValueLatitude) -> {

                    if (!newValueLatitude.isEmpty()) {
                        try {
                            if (tableHomes.getSelectionModel().getSelectedItem().getLatitude() != Double.parseDouble(newValueLatitude)) {
                                updateHomeButton.setDisable(false);
                            } else if (tableHomes.getSelectionModel().getSelectedItem().getLatitude() == Double.parseDouble(newValueLatitude)) {
                                updateHomeButton.setDisable(true);
                            }
                        } catch (Exception ignored) {
                        }
                    }
                });

                homeTextFieldLongitude.textProperty().addListener((observable1, oldValue1, newValueLongitude) -> {

                    if (!newValueLongitude.isEmpty()) {
                        try {
                            if (tableHomes.getSelectionModel().getSelectedItem().getLongitude() != Double.parseDouble(newValueLongitude)) {
                                updateHomeButton.setDisable(false);
                            } else if (tableHomes.getSelectionModel().getSelectedItem().getLongitude() == Double.parseDouble(newValueLongitude)) {
                                updateHomeButton.setDisable(true);
                            }
                        } catch (Exception ignored) {
                        }
                    }
                });

                homeTextFieldNumberOfFloors.textProperty().addListener((observable1, oldValue1, newValueNumberOfFloors) -> {
                    if (!newValueNumberOfFloors.isEmpty()) {
                        try {
                            if (tableHomes.getSelectionModel().getSelectedItem().getNumberOfFloors() != Integer.parseInt(newValueNumberOfFloors)) {
                                updateHomeButton.setDisable(false);
                            } else if (tableHomes.getSelectionModel().getSelectedItem().getNumberOfFloors() == Integer.parseInt(newValueNumberOfFloors)) {
                                updateHomeButton.setDisable(true);
                            }
                        } catch (Exception ignored) {
                        }
                    }
                });

                homeTextFieldNumberOfRooms.textProperty().addListener((observable1, oldValue1, newValueNumberOfRooms) -> {
                    if (!newValueNumberOfRooms.isEmpty()) {
                        try {
                            if (tableHomes.getSelectionModel().getSelectedItem().getNumberOfRooms() != Integer.parseInt(newValueNumberOfRooms)) {
                                updateHomeButton.setDisable(false);
                            } else if (tableHomes.getSelectionModel().getSelectedItem().getNumberOfRooms() == Integer.parseInt(newValueNumberOfRooms)) {
                                updateHomeButton.setDisable(true);
                            }
                        } catch (Exception ignored) {
                        }
                    }
                });

                homeTextFieldSquare.textProperty().addListener((observable1, oldValue1, newValueSquare) -> {
                    if (!newValueSquare.isEmpty()) {
                        try {
                            System.out.println(tableHomes.getSelectionModel().getSelectedItem().getSquare());
                            if (tableHomes.getSelectionModel().getSelectedItem().getSquare() != Double.parseDouble(newValueSquare)) {
                                updateHomeButton.setDisable(false);
                            } else if (tableHomes.getSelectionModel().getSelectedItem().getSquare() == Double.parseDouble(newValueSquare)) {
                                updateHomeButton.setDisable(true);
                            }
                        } catch (Exception ignored) {
                        }
                    }
                });


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

        //отключение возможности сортировки полей таблицы объектов "Квартира"
        flatTableColumnCity.setSortable(false);
        flatTableColumnStreet.setSortable(false);
        flatTableColumnHomeNumber.setSortable(false);
        flatTableColumnFlatNumber.setSortable(false);
        flatTableColumnLatitude.setSortable(false);
        flatTableColumnLongitude.setSortable(false);
        flatTableColumnFloor.setSortable(false);
        flatTableColumnNumberOfRooms.setSortable(false);
        flatTableColumnSquare.setSortable(false);

        //заполняем таблицу данным из БД
        tableFlats.setItems(createListOfFlats(getFlatTableContent()));

        //Заносим данные выделенного объекта "Квартира" в текстовые поля при клике на объект в таблице
        tableFlats.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedFlat) -> {
            if (selectedFlat != null) {

                idSelectedFlat = idFlatsFromDatabaseArrayList.get(tableFlats.getSelectionModel().getSelectedIndex());

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

                createFlatButton.setDisable(true);
                updateFlatButton.setDisable(true);
                deleteFlatButton.setDisable(false);

                //Слушатели для текстовых полей для проверки на изменение какого либо значения и предоставления возможности обновления квартиры
                flatTextFieldCity.textProperty().addListener((observable1, oldValue1, newValueCity) -> {
                    try {
                        if (!tableFlats.getSelectionModel().getSelectedItem().getCity().equals(newValueCity)) {
                            updateFlatButton.setDisable(false);
                        } else if (selectedFlat.getCity().equals(newValueCity)) {
                            updateFlatButton.setDisable(true);
                        }
                    } catch (Exception ignored) {
                    }
                });

                flatTextFieldStreet.textProperty().addListener((observable1, oldValue1, newValueStreet) -> {
                    try {
                        if (!tableFlats.getSelectionModel().getSelectedItem().getStreet().equals(newValueStreet)) {
                            updateFlatButton.setDisable(false);
                        } else if (tableFlats.getSelectionModel().getSelectedItem().getStreet().equals(newValueStreet)) {
                            updateFlatButton.setDisable(true);
                        }
                    } catch (Exception ignored) {
                    }
                });

                flatTextFieldHomeNumber.textProperty().addListener((observable1, oldValue1, newValueHomeNumber) -> {
                    try {
                        if (!tableFlats.getSelectionModel().getSelectedItem().getHomeNumber().equals(newValueHomeNumber)) {
                            updateFlatButton.setDisable(false);
                        } else if (tableFlats.getSelectionModel().getSelectedItem().getHomeNumber().equals(newValueHomeNumber)) {
                            updateFlatButton.setDisable(true);
                        }
                    } catch (Exception ignored) {
                    }
                });

                flatTextFieldFlatNumber.textProperty().addListener((observable1, oldValue1, newValueFlatNumber) -> {
                    if (!tableFlats.getSelectionModel().getSelectedItem().getFlatNumber().equals(newValueFlatNumber)) {
                        updateFlatButton.setDisable(false);
                    } else if (tableFlats.getSelectionModel().getSelectedItem().getFlatNumber().equals(newValueFlatNumber)) {
                        updateFlatButton.setDisable(true);
                    }
                });

                flatTextFieldLatitude.textProperty().addListener((observable1, oldValue1, newValueLatitude) -> {
                    if (!newValueLatitude.isEmpty()) {
                        try {
                            if (tableFlats.getSelectionModel().getSelectedItem().getLatitude() != Double.parseDouble(newValueLatitude)) {
                                updateFlatButton.setDisable(false);
                            } else if (tableFlats.getSelectionModel().getSelectedItem().getLatitude() == Double.parseDouble(newValueLatitude)) {
                                updateFlatButton.setDisable(true);
                            }
                        } catch (Exception ignored) {
                        }
                    }
                });

                flatTextFieldLongitude.textProperty().addListener((observable1, oldValue1, newValueLongitude) -> {
                    if (!newValueLongitude.isEmpty()) {
                        try {
                            if (tableFlats.getSelectionModel().getSelectedItem().getLongitude() != Double.parseDouble(newValueLongitude)) {
                                updateFlatButton.setDisable(false);
                            } else if (tableFlats.getSelectionModel().getSelectedItem().getLongitude() == Double.parseDouble(newValueLongitude)) {
                                updateFlatButton.setDisable(true);
                            }
                        } catch (Exception ignored) {
                        }
                    }
                });

                flatTextFieldFloor.textProperty().addListener((observable1, oldValue1, newValueFloor) -> {
                    if (!newValueFloor.isEmpty()) {
                        try {
                            if (tableFlats.getSelectionModel().getSelectedItem().getFloor() != Integer.parseInt(newValueFloor)) {
                                updateFlatButton.setDisable(false);
                            } else if (tableFlats.getSelectionModel().getSelectedItem().getFloor() == Integer.parseInt(newValueFloor)) {
                                updateFlatButton.setDisable(true);
                            }
                        } catch (Exception ignored) {
                        }
                    }
                });

                flatTextFieldNumberOfRooms.textProperty().addListener((observable1, oldValue1, newValueNumberOfRooms) -> {
                    if (!newValueNumberOfRooms.isEmpty()) {
                        try {
                            if (tableFlats.getSelectionModel().getSelectedItem().getNumberOfRooms() != Integer.parseInt(newValueNumberOfRooms)) {
                                updateFlatButton.setDisable(false);
                            } else if (tableFlats.getSelectionModel().getSelectedItem().getNumberOfRooms() == Integer.parseInt(newValueNumberOfRooms)) {
                                updateFlatButton.setDisable(true);
                            }
                        } catch (Exception ignored) {
                        }
                    }
                });

                flatTextFieldSquare.textProperty().addListener((observable1, oldValue1, newValueSquare) -> {
                    if (!newValueSquare.isEmpty()) {
                        try {
                            if (tableFlats.getSelectionModel().getSelectedItem().getSquare() != Double.parseDouble(newValueSquare)) {
                                updateFlatButton.setDisable(false);
                            } else if (tableFlats.getSelectionModel().getSelectedItem().getSquare() == Double.parseDouble(newValueSquare)) {
                                updateFlatButton.setDisable(true);
                            }
                        } catch (Exception ignored) {
                        }
                    }
                });

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

        //отключение возможности сортировки полей таблицы объектов "Квартира"
        landTableColumnCity.setSortable(false);
        landTableColumnStreet.setSortable(false);
        landTableColumnHomeNumber.setSortable(false);
        landTableColumnFlatNumber.setSortable(false);
        landTableColumnLatitude.setSortable(false);
        landTableColumnLongitude.setSortable(false);
        landTableColumnSquare.setSortable(false);

        //заполняем таблицу данным из БД
        tableLands.setItems(createListOfLands(getLandTableContent()));

        //Заносим данные выделенного объекта "Квартира" в текстовые поля при клике на объект в таблице
        tableLands.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedLand) -> {
            if (selectedLand != null) {

                idSelectedLand = idLandsFromDatabaseArrayList.get(tableLands.getSelectionModel().getSelectedIndex());

                //устанавливаем значения выделенного объекта в текстовые поля
                landTextFieldCity.setText(tableLands.getSelectionModel().getSelectedItem().getCity());
                landTextFieldStreet.setText(tableLands.getSelectionModel().getSelectedItem().getStreet());
                landTextFieldHomeNumber.setText(tableLands.getSelectionModel().getSelectedItem().getHomeNumber());
                landTextFieldFlatNumber.setText(tableLands.getSelectionModel().getSelectedItem().getFlatNumber());
                landTextFieldLatitude.setText(String.valueOf(tableLands.getSelectionModel().getSelectedItem().getLatitude()));
                landTextFieldLongitude.setText(String.valueOf(tableLands.getSelectionModel().getSelectedItem().getLongitude()));
                landTextFieldSquare.setText(String.valueOf(tableLands.getSelectionModel().getSelectedItem().getSquare()));

                createLandButton.setDisable(true);
                updateLandButton.setDisable(true);
                deleteLandButton.setDisable(false);

                //Слушатели для текстовых полей для проверки на изменение какого либо значения и предоставления возможности обновления земли
                landTextFieldCity.textProperty().addListener((observable1, oldValue1, newValueCity) -> {
                    try {
                        if (!tableLands.getSelectionModel().getSelectedItem().getCity().equals(newValueCity)) {
                            updateLandButton.setDisable(false);
                        } else if (tableLands.getSelectionModel().getSelectedItem().getCity().equals(newValueCity)) {
                            updateLandButton.setDisable(true);
                        }
                    } catch (Exception ignored) {
                    }
                });

                landTextFieldStreet.textProperty().addListener((observable1, oldValue1, newValueStreet) -> {
                    try {
                        if (!tableLands.getSelectionModel().getSelectedItem().getStreet().equals(newValueStreet)) {
                            updateLandButton.setDisable(false);
                        } else if (tableLands.getSelectionModel().getSelectedItem().getStreet().equals(newValueStreet)) {
                            updateLandButton.setDisable(true);
                        }
                    } catch (Exception ignored) {
                    }
                });

                landTextFieldHomeNumber.textProperty().addListener((observable1, oldValue1, newValueHomeNumber) -> {
                    try {
                        if (!tableLands.getSelectionModel().getSelectedItem().getHomeNumber().equals(newValueHomeNumber)) {
                            updateLandButton.setDisable(false);
                        } else if (tableLands.getSelectionModel().getSelectedItem().getHomeNumber().equals(newValueHomeNumber)) {
                            updateLandButton.setDisable(true);
                        }
                    } catch (Exception ignored) {
                    }
                });

                landTextFieldFlatNumber.textProperty().addListener((observable1, oldValue1, newValueFlatNumber) -> {
                    try {
                        if (!tableLands.getSelectionModel().getSelectedItem().getFlatNumber().equals(newValueFlatNumber)) {
                            updateLandButton.setDisable(false);
                        } else if (tableLands.getSelectionModel().getSelectedItem().getFlatNumber().equals(newValueFlatNumber)) {
                            updateLandButton.setDisable(true);
                        }
                    } catch (Exception ignored) {
                    }
                });

                landTextFieldLatitude.textProperty().addListener((observable1, oldValue1, newValueLatitude) -> {
                    if (!newValueLatitude.isEmpty()) {
                        try {
                            if (tableLands.getSelectionModel().getSelectedItem().getLatitude() != Double.parseDouble(newValueLatitude)) {
                                updateLandButton.setDisable(false);
                            } else if (tableLands.getSelectionModel().getSelectedItem().getLatitude() == Double.parseDouble(newValueLatitude)) {
                                updateLandButton.setDisable(true);
                            }
                        } catch (Exception ignored) {
                        }
                    }
                });

                landTextFieldLongitude.textProperty().addListener((observable1, oldValue1, newValueLongitude) -> {
                    if (!newValueLongitude.isEmpty()) {
                        try {
                            if (tableLands.getSelectionModel().getSelectedItem().getLongitude() != Double.parseDouble(newValueLongitude)) {
                                updateLandButton.setDisable(false);
                            } else if (tableLands.getSelectionModel().getSelectedItem().getLongitude() == Double.parseDouble(newValueLongitude)) {
                                updateLandButton.setDisable(true);
                            }
                        } catch (Exception ignored) {
                        }
                    }
                });

                landTextFieldSquare.textProperty().addListener((observable1, oldValue1, newValueSquare) -> {
                    if (!newValueSquare.isEmpty()) {
                        try {
                            if (tableLands.getSelectionModel().getSelectedItem().getSquare() != Double.parseDouble(newValueSquare)) {
                                updateLandButton.setDisable(false);
                            } else if (tableLands.getSelectionModel().getSelectedItem().getSquare() == Double.parseDouble(newValueSquare)) {
                                updateLandButton.setDisable(true);
                            }
                        } catch (Exception ignored) {
                        }
                    }
                });

            } else {
                createLandButton.setDisable(false);
                updateLandButton.setDisable(true);
                deleteLandButton.setDisable(true);
            }
        });

        findHomeByAddress();
        findFlatByAddress();
        findLandByAddress();
    }

    /**
     * Метод для поиска объекта недвижимости "Квартира" по адресу
     */
    private void findFlatByAddress() {
        ObservableList<Flat> flatsList = createListOfFlats(getFlatTableContent());
        FilteredList<Flat> filteredFlatList = new FilteredList<>(flatsList);

        searchTextField.textProperty().addListener((observable, oldValue, newValue) ->
                filteredFlatList.setPredicate((Predicate<? super Flat>) flat -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    for (int i = 0; i < flatsList.size(); i++) {
                        if (Helper.levenstain(newValue.toLowerCase(), flat.getCity().toLowerCase()) <= 3) {
                            return true;
                        }
                        if (Helper.levenstain(newValue.toLowerCase(), flat.getStreet().toLowerCase()) <= 3) {
                            return true;
                        }
                        if (Helper.levenstain(newValue.toLowerCase(), flat.getHomeNumber().toLowerCase()) <= 1) {
                            return true;
                        }
                        if (Helper.levenstain(newValue.toLowerCase(), flat.getFlatNumber().toLowerCase()) <= 1) {
                            return true;
                        }
                    }

                    return false;
                }));

        SortedList<Flat> sortedFlat = new SortedList<>(filteredFlatList);
        sortedFlat.comparatorProperty().bind(tableFlats.comparatorProperty());
        tableFlats.setItems(sortedFlat);
    }

    /**
     * Метод для поиска объекта недвижимости "Земля" по адресу
     */
    private void findLandByAddress() {
        ObservableList<Land> landsList = createListOfLands(getLandTableContent());
        FilteredList<Land> filteredLandList = new FilteredList<>(landsList);

        searchTextField.textProperty().addListener((observable, oldValue, newValue) ->
                filteredLandList.setPredicate((Predicate<? super Land>) land -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    for (int i = 0; i < landsList.size(); i++) {
                        if (Helper.levenstain(newValue.toLowerCase(), land.getCity().toLowerCase()) <= 3) {
                            return true;
                        }
                        if (Helper.levenstain(newValue.toLowerCase(), land.getStreet().toLowerCase()) <= 3) {
                            return true;
                        }
                        if (Helper.levenstain(newValue.toLowerCase(), land.getHomeNumber().toLowerCase()) <= 1) {
                            return true;
                        }
                        if (Helper.levenstain(newValue.toLowerCase(), land.getFlatNumber().toLowerCase()) <= 1) {
                            return true;
                        }
                    }

                    return false;
                }));

        SortedList<Land> sortedLand = new SortedList<>(filteredLandList);
        sortedLand.comparatorProperty().bind(tableLands.comparatorProperty());
        tableLands.setItems(sortedLand);
    }

    /**
     * Метод для поиска объекта недвижимости "Дом" по адресу
     */
    private void findHomeByAddress() {
        ObservableList<Home> homesList = createListOfHomes(getHomeTableContent());
        FilteredList<Home> filteredHomeList = new FilteredList<>(homesList);

        searchTextField.textProperty().addListener((observable, oldValue, newValue) ->
                filteredHomeList.setPredicate((Predicate<? super Home>) home -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    for (int i = 0; i < homesList.size(); i++) {
                        if (Helper.levenstain(newValue.toLowerCase(), home.getCity().toLowerCase()) <= 3) {
                            return true;
                        }
                        if (Helper.levenstain(newValue.toLowerCase(), home.getStreet().toLowerCase()) <= 3) {
                            return true;
                        }
                        if (Helper.levenstain(newValue.toLowerCase(), home.getHomeNumber().toLowerCase()) <= 1) {
                            return true;
                        }
                        if (Helper.levenstain(newValue.toLowerCase(), home.getFlatNumber().toLowerCase()) <= 1) {
                            return true;
                        }
                    }

                    return false;
                }));

        SortedList<Home> sortedHome = new SortedList<>(filteredHomeList);
        sortedHome.comparatorProperty().bind(tableHomes.comparatorProperty());
        tableHomes.setItems(sortedHome);
    }

    /**
     * Метод для смены вкладок внутри экрана при нажатии на соответствующий тип объекта недвижимости
     *
     * @param actionEvent нажатие на кнопку
     */
    public void switchContent(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(showHomesButton)) {
            headerTypeBuildingLabel.setText(TYPE_BUILDING_HOME_LABEL);
            showLandsButton.setStyle("");
            showFlatsButton.setStyle("");
            showHomesButton.setStyle(BUTTON_COLOR);
            homesAnchorPane.toFront();
        } else if (actionEvent.getSource().equals(showLandsButton)) {
            headerTypeBuildingLabel.setText(TYPE_BUILDING_LAND_LABEL);
            showHomesButton.setStyle("");
            showFlatsButton.setStyle("");
            showLandsButton.setStyle(BUTTON_COLOR);
            landsAnchorPane.toFront();
        } else if (actionEvent.getSource().equals(showFlatsButton)) {
            headerTypeBuildingLabel.setText(TYPE_BUILDING_FLAT_LABEL);
            showHomesButton.setStyle("");
            showLandsButton.setStyle("");
            showFlatsButton.setStyle(BUTTON_COLOR);
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
        Helper.openNewScreen(PATH_TO_MAIN_SCREEN);
    }

    /**
     * Метод для добавления нового объекта недвижимости "Дом"
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
                !homeTextFieldSquare.getText().isEmpty()) {

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
                    showAlert(
                            ERROR_ADD_HOME,
                            ERROR_LATITUDE,
                            Alert.AlertType.ERROR);
                    return;
                }
            }

            if (!homeTextFieldLongitude.getText().isEmpty()) {
                if (isCorrectLongitude(homeTextFieldLongitude)) {
                    home.setLongitude(Double.valueOf(homeTextFieldLongitude.getText()));
                } else {
                    showAlert(
                            ERROR_ADD_HOME,
                            ERROR_LONGITUDE,
                            Alert.AlertType.ERROR);
                    return;
                }
            }

            //SQL запрос для добавления нового объеекта недвижимости "Дом"
            String insertHome = String.format("INSERT INTO %s(%s, %s, %s, %s, %s, %s, %s, %s, %s) VALUES (?,?,?,?,?,?,?,?,?);",
                    HOME_TABLE,
                    HOME_CITY_COLUMN,
                    HOME_STREET_COLUMN,
                    HOME_HOME_NUMBER_COLUMN,
                    HOME_FLAT_NUMBER_COLUMN,
                    HOME_LATITUDE_COLUMN,
                    HOME_LONGITUDE_COLUMN,
                    HOME_NUMBER_OF_FLOORS_COLUMN,
                    HOME_NUMBER_OF_ROOMS_COLUMN,
                    HOME_SQUARE_COLUMN);

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
                showAlert(
                        OPERATION_COMPLETED,
                        "Объект недвижимости 'Дом' успешно добавлен!",
                        Alert.AlertType.INFORMATION);

                tableHomes.setItems(createListOfHomes(getHomeTableContent()));

                //обнуляем текстовые поля после добавления клиента в базу
                clearTextFields(listOfHomeTextFields);

                //Поиск объекта недвижимости типа "Дом" адресу
                findHomeByAddress();

            } catch (SQLException e) {
                //открываем диалоговое окно для уведомления об ошибке
                showAlert(
                        ERROR_ADD_HOME,
                        "Объект недвижимости 'Дом'  не был добавлен в базу. Повторите еще раз",
                        Alert.AlertType.ERROR);
            }

        } else {
            //открываем диалоговое окно для уведомления об ошибке
            showAlert(
                    ERROR_ADD_HOME,
                    ERROR_EMPTY_FIELDS,
                    Alert.AlertType.ERROR);
        }
    }

    /**
     * Метод для обновления объекта недвижимости "Дом"
     */
    public void updateHome() {
        //SQL запрос для обновления объекта недвижимости типа 'Дом'
        String updateHomeRequest =
                String.format("UPDATE %s SET %s=?,%s=?,%s=?,%s=?,%s=?, %s=?, %s=?, %s=?, %s=?  WHERE %s=?",
                        HOME_TABLE,
                        HOME_CITY_COLUMN,
                        HOME_STREET_COLUMN,
                        HOME_HOME_NUMBER_COLUMN,
                        HOME_FLAT_NUMBER_COLUMN,
                        HOME_LATITUDE_COLUMN, HOME_LONGITUDE_COLUMN, HOME_NUMBER_OF_FLOORS_COLUMN, HOME_NUMBER_OF_ROOMS_COLUMN, HOME_SQUARE_COLUMN, HOME_ID);

        if (!homeTextFieldCity.getText().isEmpty() ||
                !homeTextFieldStreet.getText().isEmpty() ||
                !homeTextFieldHomeNumber.getText().isEmpty() ||
                !homeTextFieldFlatNumber.getText().isEmpty() ||
                !homeTextFieldLatitude.getText().isEmpty() ||
                !homeTextFieldLongitude.getText().isEmpty() ||
                !homeTextFieldNumberOfFloors.getText().isEmpty() ||
                !homeTextFieldNumberOfRooms.getText().isEmpty() ||
                !homeTextFieldSquare.getText().isEmpty()) {

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
                    showAlert(
                            ERROR_UPDATE_HOME,
                            ERROR_LATITUDE,
                            Alert.AlertType.ERROR);
                    return;
                }
                if (isCorrectLongitude(homeTextFieldLongitude)) {
                    updateHomeStatement.setDouble(6, Double.parseDouble(homeTextFieldLongitude.getText()));
                } else {
                    //открываем диалоговое окно для уведомления об ошибке
                    showAlert(
                            ERROR_UPDATE_HOME,
                            ERROR_LONGITUDE,
                            Alert.AlertType.ERROR);
                    return;
                }

                //выполнение SQL запроса
                updateHomeStatement.executeUpdate();

                //Обнуляем текстовые поля после обновления клиента
                clearTextFields(listOfHomeTextFields);

                tableHomes.setItems(createListOfHomes(getHomeTableContent()));

                //открываем диалоговое окно для уведомления об успешном обновлении
                showAlert(
                        OPERATION_COMPLETED,
                        "Обновление объекта недвижимости типа 'Дом' выполнено успешно!",
                        Alert.AlertType.INFORMATION);

                //Поиск объекта недвижимости типа "Дом" адресу
                findHomeByAddress();

            } catch (SQLException e) {
                //открываем диалоговое окно для уведомления об ошибке
                showAlert(
                        ERROR_UPDATE_HOME,
                        "Обновление объекта недвижимости типа 'Дом' не выполнено! Повторите еще раз",
                        Alert.AlertType.ERROR);
            }
        } else {
            //открываем диалоговое окно для уведомления об ошибке
            showAlert(
                    ERROR_UPDATE_HOME,
                    ERROR_EMPTY_FIELDS,
                    Alert.AlertType.ERROR);
        }
    }

    /**
     * Метод для удаления объекта недвижимости "Дом"
     */
    public void deleteHome() {

        //SQL запрос на удаление объекта недвижимости типа "Дом"
        String deleteHomeRequest = String.format("DELETE FROM %s WHERE %s=?", HOME_TABLE, HOME_ID);

        try {
            PreparedStatement deleteHomeStatement = new DatabaseHandler().createDbConnection().prepareStatement(deleteHomeRequest);

            //установка значений для вставки в запрос
            deleteHomeStatement.setInt(1, idSelectedHome);
            //выполнение запроса на удаление
            deleteHomeStatement.executeUpdate();

            //открываем диалоговое окно для уведомления об успешном удалении
            showAlert(
                    OPERATION_COMPLETED,
                    "Удаление объекта недвижимости типа 'Дом' выполнено успешно!",
                    Alert.AlertType.INFORMATION);

            //обнуляем текстовые поля после удаления объекта "Дом"
            clearTextFields(listOfHomeTextFields);

            tableHomes.setItems(createListOfHomes(getHomeTableContent()));
//            homesList.remove(tableHomes.getSelectionModel().getSelectedIndex());

            //Поиск объекта недвижимости типа "Дом" адресу
            findHomeByAddress();

        } catch (SQLException e) {
            //открываем диалоговое окно для уведомления об ошибке
            showAlert(
                    ERROR_DELETE_HOME,
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
     * @return true - если значение от -180 до 180, иначе - false
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
        String selectHomes = String.format("SELECT * FROM %s", HOME_TABLE);

        ResultSet resultSet = null;
        try {
            PreparedStatement ps = new DatabaseHandler().createDbConnection().prepareStatement(selectHomes);
            //выполняем запрос и сохраняем полученные значения в resultSet
            resultSet = ps.executeQuery();
        } catch (SQLException e) {
            //открываем диалоговое окно для уведомления об ошибке
            showAlert(
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
                //добавляем объект в список
                list.add(home);
                idHomesFromDatabaseArrayList.add(resultSet.getInt(1));
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
     * Метод для добавления нового объекта недвижимости "Квартира"
     */
    public void createFlat() {

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
                    showAlert(
                            ERROR_ADD_FLAT,
                            ERROR_LATITUDE,
                            Alert.AlertType.ERROR);
                    return;
                }
            }


            if (!flatTextFieldLongitude.getText().isEmpty()) {
                if (isCorrectLongitude(flatTextFieldLongitude)) {
                    flat.setLongitude(Double.valueOf(flatTextFieldLongitude.getText()));
                } else {
                    showAlert(
                            ERROR_ADD_FLAT,
                            ERROR_LONGITUDE,
                            Alert.AlertType.ERROR);
                    return;
                }
            }

            System.out.println(flat);

            //SQL запрос для добавления нового клиента в базу данных
            String insertFlat = String.format("INSERT INTO %s(%s, %s, %s, %s, %s, %s, %s, %s, %s) VALUES (?,?,?,?,?,?,?,?,?);",
                    FLAT_TABLE,
                    FLAT_CITY_COLUMN,
                    FLAT_STREET_COLUMN,
                    FLAT_HOME_NUMBER_COLUMN,
                    FLAT_FLAT_NUMBER_COLUMN,
                    FLAT_LATITUDE_COLUMN,
                    FLAT_LONGITUDE_COLUMN,
                    FLAT_FLOOR_COLUMN,
                    FLAT_NUMBER_OF_ROOMS_COLUMN,
                    FLAT_SQUARE_COLUMN);

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
                showAlert(
                        OPERATION_COMPLETED,
                        "Объект недвижимости 'Квартира' успешно добавлен!",
                        Alert.AlertType.INFORMATION);

                tableFlats.setItems(createListOfFlats(getFlatTableContent()));

                //обнуляем текстовые поля после добавления клиента в базу
                clearTextFields(listOfFlatTextFields);

                //Поиск объекта недвижимости типа "Квартира" адресу
                findFlatByAddress();

            } catch (SQLException e) {
                //открываем диалоговое окно для уведомления об ошибке
                showAlert(
                        ERROR_ADD_FLAT,
                        "Объект недвижимости 'Квартира'  не был добавлен в базу. Повторите еще раз",
                        Alert.AlertType.ERROR);
            }

        } else {
            showAlert(
                    ERROR_ADD_FLAT,
                    ERROR_EMPTY_FIELDS,
                    Alert.AlertType.ERROR);
        }

    }


    /**
     * Метод для обновления объекта недвижимости "Квартира"
     */
    public void updateFlat() {

        //SQL запрос для обновления объекта недвижимости типа 'Дом'
        String updateFlatRequest =
                String.format("UPDATE %s SET %s=?,%s=?,%s=?,%s=?,%s=?, %s=?, %s=?, %s=?, %s=?  WHERE %s=?",
                        FLAT_TABLE,
                        FLAT_CITY_COLUMN,
                        FLAT_STREET_COLUMN,
                        FLAT_HOME_NUMBER_COLUMN,
                        FLAT_FLAT_NUMBER_COLUMN,
                        FLAT_LATITUDE_COLUMN,
                        FLAT_LONGITUDE_COLUMN,
                        FLAT_FLOOR_COLUMN,
                        FLAT_NUMBER_OF_ROOMS_COLUMN,
                        FLAT_SQUARE_COLUMN,
                        FLAT_ID);

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
                    showAlert(
                            ERROR_UPDATE_FLAT,
                            ERROR_LATITUDE,
                            Alert.AlertType.ERROR);
                    return;
                }
                if (isCorrectLongitude(flatTextFieldLongitude)) {
                    updateFlatStatement.setDouble(6, Double.parseDouble(flatTextFieldLongitude.getText()));
                } else {
                    //открываем диалоговое окно для уведомления об ошибке
                    showAlert(
                            ERROR_UPDATE_FLAT,
                            ERROR_LONGITUDE,
                            Alert.AlertType.ERROR);
                    return;
                }

                //выполнение SQL запроса
                updateFlatStatement.executeUpdate();


                //открываем диалоговое окно для уведомления об успешном обновлении
                showAlert(
                        OPERATION_COMPLETED,
                        "Обновление объекта недвижимости типа 'Квартира' выполнено успешно!",
                        Alert.AlertType.INFORMATION);

                //Обнуляем текстовые поля после обновления клиента
                clearTextFields(listOfFlatTextFields);

                tableFlats.setItems(createListOfFlats(getFlatTableContent()));

                //Поиск объекта недвижимости типа "Квартира" адресу
                findFlatByAddress();

            } catch (SQLException e) {
                //открываем диалоговое окно для уведомления об ошибке
                showAlert(
                        ERROR_UPDATE_FLAT,
                        "Обновление объекта недвижимости типа 'Квартира' не выполнено! Повторите еще раз",
                        Alert.AlertType.ERROR);
            }
        } else {
            //открываем диалоговое окно для уведомления об ошибке
            showAlert(
                    ERROR_UPDATE_FLAT,
                    ERROR_EMPTY_FIELDS,
                    Alert.AlertType.ERROR);
        }
    }

    /**
     * Метод для удаления объекта недвижимости "Квартира"
     */
    public void deleteFlat() {

        //SQL запрос на удаление объекта недвижимости типа "Квартира"
        String deleteFlatRequest = String.format("DELETE FROM %s WHERE %s=?", FLAT_TABLE, FLAT_ID);

        try {
            PreparedStatement deleteFlatStatement = new DatabaseHandler().createDbConnection().prepareStatement(deleteFlatRequest);

            //установка значений для вставки в запрос
            deleteFlatStatement.setInt(1, idSelectedFlat);
            //выполнение запроса на удаление
            deleteFlatStatement.executeUpdate();

            //открываем диалоговое окно для уведомления об успешном удалении
            showAlert(
                    OPERATION_COMPLETED,
                    "Удаление объекта недвижимости типа 'Квартира' выполнено успешно!",
                    Alert.AlertType.INFORMATION);

            //обнуляем текстовые поля после удаления объекта "Дом"
            clearTextFields(listOfFlatTextFields);

//            flatsList.remove(tableFlats.getSelectionModel().getSelectedIndex());

            tableFlats.setItems(createListOfFlats(getFlatTableContent()));

            //Поиск объекта недвижимости типа "Квартира" адресу
            findFlatByAddress();

        } catch (SQLException e) {
            //открываем диалоговое окно для уведомления об ошибке
            showAlert(
                    ERROR_DELETE_FLAT,
                    "Возникла ошибка при удалении объекта недвижимости типа 'Квартира' с ID = " + idSelectedFlat,
                    Alert.AlertType.ERROR
            );
        }

    }

    /**
     * Метод для формирования ObservableList из набора данных
     *
     * @param resultSet набор данных из таблицы 'flat'
     * @return ObservableList с объектами класса Flat
     */
    private ObservableList<Flat> createListOfFlats(ResultSet resultSet) {
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
                //добавляем объект в список
                list.add(flat);
                idFlatsFromDatabaseArrayList.add(resultSet.getInt(1));
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
     * Метод для получения содержимого таблицы квартир из базы данных
     *
     * @return ResultSet - набор данных из таблицы
     */
    private ResultSet getFlatTableContent() {
        //SQL запрос на выбор всех данных из таблицы `flat`
        String selectFlats = String.format("SELECT * FROM %s", FLAT_TABLE);

        ResultSet resultSet = null;
        try {
            PreparedStatement ps = new DatabaseHandler().createDbConnection().prepareStatement(selectFlats);
            //выполняем запрос и сохраняем полученные значения в resultSet
            resultSet = ps.executeQuery();
        } catch (SQLException e) {
            //открываем диалоговое окно для уведомления об ошибке
            showAlert(
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
     * Метод для добавления нового объекта недвижимости "Земля"
     */
    public void createLand() {

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
                    showAlert(
                            ERROR_ADD_LAND,
                            ERROR_LATITUDE,
                            Alert.AlertType.ERROR);
                    return;
                }
            }

            if (!landTextFieldLongitude.getText().isEmpty()) {
                if (isCorrectLongitude(landTextFieldLongitude)) {
                    land.setLongitude(Double.valueOf(landTextFieldLongitude.getText()));
                } else {
                    showAlert(
                            ERROR_ADD_LAND,
                            ERROR_LONGITUDE,
                            Alert.AlertType.ERROR);
                    return;
                }
            }

            if (!landTextFieldSquare.getText().isEmpty()) {
                land.setSquare(Double.parseDouble(landTextFieldSquare.getText()));
            }


            //SQL запрос для добавления нового объекта недвижимости "Земля"
            String insertLand = String.format("INSERT INTO %s(%s, %s, %s, %s, %s, %s, %s) VALUES (?,?,?,?,?,?,?);",
                    LAND_TABLE,
                    LAND_CITY_COLUMN,
                    LAND_STREET_COLUMN,
                    LAND_HOME_NUMBER_COLUMN,
                    LAND_FLAT_NUMBER_COLUMN,
                    LAND_LATITUDE_COLUMN,
                    LAND_LONGITUDE_COLUMN,
                    LAND_SQUARE_COLUMN);

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
                showAlert(
                        OPERATION_COMPLETED,
                        "Объект недвижимости 'Земля' успешно добавлен!",
                        Alert.AlertType.INFORMATION);

                tableLands.setItems(createListOfLands(getLandTableContent()));

                //обнуляем текстовые поля после добавления клиента в базу
                clearTextFields(listOfLandTextFields);

                //Поиск объекта недвижимости типа "Земля" адресу
                findLandByAddress();

            } catch (SQLException e) {
                //открываем диалоговое окно для уведомления об ошибке
                showAlert(
                        ERROR_ADD_LAND,
                        "Объект недвижимости 'Земля'  не был добавлен в базу. Повторите еще раз",
                        Alert.AlertType.ERROR);
            }

        } else {
            //открываем диалоговое окно для уведомления об ошибке
            showAlert(
                    ERROR_ADD_LAND,
                    ERROR_EMPTY_FIELDS,
                    Alert.AlertType.ERROR);
        }

    }

    /**
     * Метод для обновления объекта недвижимости "Земля"
     */
    public void updateLand() {

        //SQL запрос для обновления объекта недвижимости "Земля"
        String updateLandRequest =
                String.format("UPDATE %s SET %s=?,%s=?,%s=?,%s=?,%s=?, %s=?, %s=?  WHERE %s=?",
                        LAND_TABLE,
                        LAND_CITY_COLUMN,
                        LAND_STREET_COLUMN,
                        LAND_HOME_NUMBER_COLUMN,
                        LAND_FLAT_NUMBER_COLUMN,
                        LAND_LATITUDE_COLUMN,
                        LAND_LONGITUDE_COLUMN,
                        LAND_SQUARE_COLUMN,
                        LAND_ID_COLUMN);

        if (!landTextFieldCity.getText().isEmpty() ||
                !landTextFieldStreet.getText().isEmpty() ||
                !landTextFieldHomeNumber.getText().isEmpty() ||
                !landTextFieldFlatNumber.getText().isEmpty() ||
                !landTextFieldLatitude.getText().isEmpty() ||
                !landTextFieldLongitude.getText().isEmpty() ||
                !landTextFieldSquare.getText().isEmpty()
        ) {

            try {
                PreparedStatement updateLandStatement = new DatabaseHandler().createDbConnection().prepareStatement(updateLandRequest);

                //установка значений для вставки в запрос
                updateLandStatement.setString(1, landTextFieldCity.getText());
                updateLandStatement.setString(2, landTextFieldStreet.getText());
                updateLandStatement.setString(3, landTextFieldHomeNumber.getText());
                updateLandStatement.setString(4, landTextFieldFlatNumber.getText());
                updateLandStatement.setDouble(7, Double.parseDouble(landTextFieldSquare.getText()));
                updateLandStatement.setInt(8, idSelectedLand);

                if (isCorrectLatitude(landTextFieldLatitude)) {
                    updateLandStatement.setDouble(5, Double.parseDouble(landTextFieldLatitude.getText()));
                } else {
                    //открываем диалоговое окно для уведомления об ошибке
                    showAlert(
                            ERROR_UPDATE_LAND,
                            ERROR_LATITUDE,
                            Alert.AlertType.ERROR);
                    return;
                }
                if (isCorrectLongitude(landTextFieldLongitude)) {
                    updateLandStatement.setDouble(6, Double.parseDouble(landTextFieldLongitude.getText()));
                } else {
                    //открываем диалоговое окно для уведомления об ошибке
                    showAlert(
                            ERROR_UPDATE_LAND,
                            ERROR_LONGITUDE,
                            Alert.AlertType.ERROR);
                    return;
                }

                //выполнение SQL запроса
                updateLandStatement.executeUpdate();

                tableLands.setItems(createListOfLands(getLandTableContent()));

                //открываем диалоговое окно для уведомления об успешном обновлении
                showAlert(
                        OPERATION_COMPLETED,
                        "Обновление объекта недвижимости типа 'Земля' выполнено успешно!",
                        Alert.AlertType.INFORMATION);

                //Обнуляем текстовые поля после обновления клиента
                clearTextFields(listOfLandTextFields);

                //Поиск объекта недвижимости типа "Земля" адресу
                findLandByAddress();

            } catch (SQLException e) {
                //открываем диалоговое окно для уведомления об ошибке
                showAlert(
                        ERROR_UPDATE_LAND,
                        "Обновление объекта недвижимости типа 'Земля' не выполнено! Повторите еще раз",
                        Alert.AlertType.ERROR);
            }
        } else {
            //открываем диалоговое окно для уведомления об ошибке
            showAlert(
                    ERROR_UPDATE_LAND,
                    ERROR_EMPTY_FIELDS,
                    Alert.AlertType.ERROR);
        }
    }

    /**
     * Метод для удаления объекта недвижимости "Земля"
     */
    public void deleteLand() {

        //SQL запрос на удаление объекта недвижимости "Земля"
        String deleteLandRequest = String.format("DELETE FROM %s WHERE %s=?", LAND_TABLE, LAND_ID_COLUMN);

        try {
            PreparedStatement deleteLandStatement = new DatabaseHandler().createDbConnection().prepareStatement(deleteLandRequest);

            //установка значений для вставки в запрос
            deleteLandStatement.setInt(1, idSelectedLand);
            //выполнение запроса на удаление
            deleteLandStatement.executeUpdate();

            tableLands.setItems(createListOfLands(getLandTableContent()));

            //открываем диалоговое окно для уведомления об успешном удалении
            showAlert(
                    OPERATION_COMPLETED,
                    "Удаление объекта недвижимости типа 'Земля' выполнено успешно!",
                    Alert.AlertType.INFORMATION);

            //обнуляем текстовые поля после удаления объекта "Дом"
            clearTextFields(listOfLandTextFields);

            //Поиск объекта недвижимости типа "Земля" адресу
            findLandByAddress();

        } catch (SQLException e) {
            //открываем диалоговое окно для уведомления об ошибке
            showAlert(
                    ERROR_DELETE_LAND,
                    "Возникла ошибка при удалении объекта недвижимости типа 'Земля' с ID = " + idSelectedLand,
                    Alert.AlertType.ERROR
            );
        }

    }

    /**
     * Метод для формирования ObservableList из набора данных
     *
     * @param resultSet набор данных из таблицы 'land'
     * @return ObservableList с объектами класса Land
     */
    private ObservableList<Land> createListOfLands(ResultSet resultSet) {
        ObservableList<Land> list = FXCollections.observableArrayList();
        idLandsFromDatabaseArrayList = new ArrayList<>();
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
                idLandsFromDatabaseArrayList.add(resultSet.getInt(1));
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
     * Метод для получения содержимого таблицы 'land' из базы данных
     *
     * @return ResultSet - набор данных из таблицы
     */
    private ResultSet getLandTableContent() {
        //SQL запрос на выбор всех данных из таблицы `flat`
        String selectFlats = String.format("SELECT * FROM %s", LAND_TABLE);

        ResultSet resultSet = null;
        try {
            PreparedStatement ps = new DatabaseHandler().createDbConnection().prepareStatement(selectFlats);
            //выполняем запрос и сохраняем полученные значения в resultSet
            resultSet = ps.executeQuery();
        } catch (SQLException e) {
            //открываем диалоговое окно для уведомления об ошибке
            showAlert(
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
        alert.initOwner(this.stackPane.getScene().getWindow());
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
