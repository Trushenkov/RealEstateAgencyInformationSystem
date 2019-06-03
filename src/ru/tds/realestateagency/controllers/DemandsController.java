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
import ru.tds.realestateagency.entities.Client;
import ru.tds.realestateagency.entities.Demand;
import ru.tds.realestateagency.entities.Realtor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Класс-контроллер для обработки событий на экране "Потребности"
 *
 * @author Трушенков Дмитрий
 */
public class DemandsController {


    private static final String HOME_DEMAND = "Главная > Потребности > В доме";
    private static final String FLAT_DEMAND = "Главная > Потребности > В квартире";
    private static final String LAND_DEMAND = "Главная > Потребности > В земле";
    private static final String BUTTON_COLOR = "-fx-background-color: #0d47a1;";
    private static final String PATH_TO_HOME_SCREEN = "/ru/tds/realestateagency/views/main.fxml";
    //Константы для работы с таблицей Потребностей
    private static final String DEMANDS_TABLE = "demands";
    private static final String ID_COLUMN = "id";
    private static final String CLIENT_COLUMN = "client";
    private static final String REALTOR_COLUMN = "realtor";
    private static final String TYPE_REAL_ESTATE = "typeRealEstate";
    private static final String ADDRESS_COLUMN = "address";
    private static final String MIN_PRICE_COLUMN = "minPrice";
    private static final String MAX_PRICE_COLUMN = "maxPrice";
    private static final String MIN_SQUARE_COLUMN = "minSquare";
    private static final String MAX_SQUARE_COLUMN = "maxSquare";
    private static final String MIN_COUNT_OF_ROOMS_COLUMN = "minCountOfRooms";
    private static final String MAX_COUNT_OF_ROOMS_COLUMN = "maxCountOfRooms";
    private static final String MIN_NUMBER_OF_FLOORS_COLUMN = "minNumberOfFloors";
    private static final String MAX_NUMBER_OF_FLOORS_COLUMN = "maxNumberOfFloors";
    private static final String MIN_FLOOR = "minFloor";
    private static final String MAX_FLOOR = "maxFloor";
    private static final String HOME = "Дом";
    private static final String FLAT = "Квартира";
    private static final String LAND = "Земля";

    /*
    Элементы интерфейса общие
    */
    @FXML
    private AnchorPane mainPane;

    @FXML
    private Label headerTypeBuildingLabel;

    @FXML
    private Button homeDemandsButton;

    @FXML
    private Button flatDemandsButton;

    @FXML
    private Button landDemandsButton;

    @FXML
    private StackPane stackPane;

    /*
    Элементы интерфейса для потребности в доме
     */
    @FXML
    private AnchorPane homesAnchorPane;

    @FXML
    private TableView<Demand> tableHomes;

    @FXML
    private TableColumn<Demand, String> homeClientColumn;

    @FXML
    private TableColumn<Demand, String> homeRealtorColumn;

    @FXML
    private TableColumn<Demand, String> homeAddressColumn;

    @FXML
    private TableColumn<Demand, Integer> homeMinPriceColumn;

    @FXML
    private TableColumn<Demand, Integer> homeMaxPriceColumn;

    @FXML
    private TableColumn<Demand, Integer> homeMinSquareColumn;

    @FXML
    private TableColumn<Demand, Integer> homeMaxSquareColumn;

    @FXML
    private TableColumn<Demand, Integer> homeMinNumberOfRoomsColumn;

    @FXML
    private TableColumn<Demand, Integer> homeMaxNumberOfRoomsColumn;

    @FXML
    private TableColumn<Demand, Integer> homeMinNumberOfFloorsColumn;

    @FXML
    private TableColumn<Demand, Integer> homeMaxNumberOfFloorsColumn;

    @FXML
    private ComboBox<Client> homeClientComboBox;

    @FXML
    private ComboBox<Realtor> homeRealtorComboBox;

    @FXML
    private TextField homeTextFieldAddress;

    @FXML
    private TextField homeTextFieldMinPrice;

    @FXML
    private TextField homeTextFieldMaxPrice;

    @FXML
    private TextField homeTextFieldMinSquare;

    @FXML
    private TextField homeTextFieldMaxSquare;

    @FXML
    private TextField homeTextFieldMinNumberOfRooms;

    @FXML
    private TextField homeTextFieldMaxNumberOfRooms;

    @FXML
    private TextField homeTextFieldMinNumberOfFloors;

    @FXML
    private TextField homeTextFieldMaxNumberOfFloors;

    @FXML
    private Button createHomeDemandButton;

    @FXML
    private Button updateHomeDemandButton;

    @FXML
    private Button deleteHomeDemandButton;

    /*
    Элементы интерфейс для потребности в квартире
     */
    @FXML
    private AnchorPane flatsAnchorPane;

    @FXML
    private TableView<Demand> tableFlats;

    @FXML
    private TableColumn<Demand, String> flatClientColumn;

    @FXML
    private TableColumn<Demand, String> flatRealtorColumn;

    @FXML
    private TableColumn<Demand, String> flatAddressColumn;

    @FXML
    private TableColumn<Demand, Integer> flatMinPriceColumn;

    @FXML
    private TableColumn<Demand, Integer> flatMaxPriceColumn;

    @FXML
    private TableColumn<Demand, Integer> flatMinSquareColumn;

    @FXML
    private TableColumn<Demand, Integer> flatMaxSquareColumn;

    @FXML
    private TableColumn<Demand, Integer> flatMinNumberOfRoomsColumn;

    @FXML
    private TableColumn<Demand, Integer> flatMaxNumberOfRoomsColumn;

    @FXML
    private TableColumn<Demand, Integer> flatMinFloorColumn;

    @FXML
    private TableColumn<Demand, Integer> flatMaxFloorColumn;

    @FXML
    private ComboBox<Client> flatClientComboBox;

    @FXML
    private ComboBox<Realtor> flatRealtorComboBox;

    @FXML
    private TextField flatTextFieldAddress;

    @FXML
    private TextField flatTextFieldMinPrice;

    @FXML
    private TextField flatTextFieldMaxPrice;

    @FXML
    private TextField flatTextFieldMinSquare;

    @FXML
    private TextField flatTextFieldMaxSquare;

    @FXML
    private TextField flatTextFieldMinNumberOfRooms;

    @FXML
    private TextField flatTextFieldMaxNumberOfRooms;

    @FXML
    private TextField flatTextFieldMinFloor;

    @FXML
    private TextField flatTextFieldMaxFloor;

    @FXML
    private Button createFlatDemandButton;

    @FXML
    private Button updateFlatDemandButton;

    @FXML
    private Button deleteFlatDemandButton;


    /*
    Элементы интерфейса для потребности в земле
     */
    @FXML
    private AnchorPane landsAnchorPane;

    @FXML
    private TableView<Demand> tableLands;

    @FXML
    private TableColumn<Demand, String> landClientColumn;

    @FXML
    private TableColumn<Demand, String> landRealtorColumn;

    @FXML
    private TableColumn<Demand, String> landAddressColumn;

    @FXML
    private TableColumn<Demand, Integer> landMinPriceColumn;

    @FXML
    private TableColumn<Demand, Integer> landMaxPriceColumn;

    @FXML
    private TableColumn<Demand, Integer> landMinSquareColumn;

    @FXML
    private TableColumn<Demand, Integer> landMaxSquareColumn;

    @FXML
    private ComboBox<Client> landClientComboBox;

    @FXML
    private ComboBox<Realtor> landRealtorComboBox;

    @FXML
    private TextField landTextFieldAddress;

    @FXML
    private TextField landTextFieldMinPrice;

    @FXML
    private TextField landTextFieldMaxPrice;

    @FXML
    private TextField landTextFieldMinSquare;

    @FXML
    private TextField landTextFieldMaxSquare;

    @FXML
    private Button createLandDemandButton;

    @FXML
    private Button updateLandDemandButton;

    @FXML
    private Button deleteLandDemandButton;

    private ObservableList<TextField> listOfHomeDemandTextFields;//список текстовых полей на вкладке "Дом"
    private ObservableList<TextField> listOfFlatDemandTextFields;//список текстовых полей на вкладке "Квартира"
    private ObservableList<TextField> listOfLandDemandTextFields;//список текстовых полей на вкладке "Земля"

    private ArrayList<Integer> idHomesArray;//список id потребностей в доме из таблицы 'demands'
    private ArrayList<Integer> idFlatsArray;//список id потребностей в квартире из таблицы 'demands'
    private ArrayList<Integer> idLandsArray;//список id потребностей в земле из таблицы 'demands'

    private int idSelectedHomeDemand;//id выбранного предложения в таблице
    private int idSelectedFlatDemand;//id выбранного предложения в таблице
    private int idSelectedLandDemand;//id выбранного предложения в таблице

    //Списки для храненеия информации из базы данных
    private ObservableList<Client> listOfClients;//список клиентов
    private ObservableList<Realtor> listOfRealtors;//список риэлторов

    @FXML
    void initialize() {

        //Обнуление текстовых полей и отмена выбора строки в таблице при нажатии на область окна приложения
        mainPane.setOnMousePressed(event -> {
            idSelectedHomeDemand = 0;
            idSelectedFlatDemand = 0;
            idSelectedLandDemand = 0;
            clearTextFields(listOfHomeDemandTextFields);
            clearTextFields(listOfFlatDemandTextFields);
            clearTextFields(listOfLandDemandTextFields);
            tableHomes.getSelectionModel().clearSelection();
            tableFlats.getSelectionModel().clearSelection();
            tableLands.getSelectionModel().clearSelection();

            homeClientComboBox.setValue(null);
            homeRealtorComboBox.setValue(null);

            flatClientComboBox.setValue(null);
            flatRealtorComboBox.setValue(null);

            landClientComboBox.setValue(null);
            landRealtorComboBox.setValue(null);
        });

        //Кнопки
        updateHomeDemandButton.setDisable(true);
        deleteHomeDemandButton.setDisable(true);

        updateFlatDemandButton.setDisable(true);
        deleteFlatDemandButton.setDisable(true);

        updateLandDemandButton.setDisable(true);
        deleteLandDemandButton.setDisable(true);

        //объявляем коллекции объектов
        listOfClients = createListOfClients(getDataFromDB("clients"));
        listOfRealtors = createListOfRealtors(getDataFromDB("realtors"));

        //размещаем соответствующие списки объектов в элементы ComboBox
        homeClientComboBox.setItems(listOfClients);
        homeRealtorComboBox.setItems(listOfRealtors);

        flatClientComboBox.setItems(listOfClients);
        flatRealtorComboBox.setItems(listOfRealtors);

        landClientComboBox.setItems(listOfClients);
        landRealtorComboBox.setItems(listOfRealtors);

        //формирование коллекций с текстовыми полями для каждого типа объекта недвижимости
        listOfHomeDemandTextFields = createListOfHomeTextFields();
        listOfFlatDemandTextFields = createListOfFlatTextFields();
        listOfLandDemandTextFields = createListOfLandTextFields();

        //установка по умолчанию выбранной вкладки потребности в доме
        homeDemandsButton.setStyle("-fx-background-color: #0d47a1");
        headerTypeBuildingLabel.setText(HOME_DEMAND);
        homesAnchorPane.toFront();
        stackPane.setBackground(new Background(new BackgroundFill(Color.rgb(255, 255, 255), CornerRadii.EMPTY, Insets.EMPTY)));


        //определение колонок таблицы с соответствующими полями объекта "Потребность в доме"
        homeClientColumn.setCellValueFactory(new PropertyValueFactory<>("client"));
        homeRealtorColumn.setCellValueFactory(new PropertyValueFactory<>("realtor"));
        homeAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        homeMinPriceColumn.setCellValueFactory(new PropertyValueFactory<>("minPrice"));
        homeMaxPriceColumn.setCellValueFactory(new PropertyValueFactory<>("maxPrice"));
        homeMinSquareColumn.setCellValueFactory(new PropertyValueFactory<>("minSquare"));
        homeMaxSquareColumn.setCellValueFactory(new PropertyValueFactory<>("maxSquare"));
        homeMinNumberOfRoomsColumn.setCellValueFactory(new PropertyValueFactory<>("minNumberOfRooms"));
        homeMaxNumberOfRoomsColumn.setCellValueFactory(new PropertyValueFactory<>("maxNumberOfRooms"));
        homeMinNumberOfFloorsColumn.setCellValueFactory(new PropertyValueFactory<>("minNumberOfFloors"));
        homeMaxNumberOfFloorsColumn.setCellValueFactory(new PropertyValueFactory<>("maxNumberOfFloors"));

        //отключение возможности сортировки полей таблицы объектов "Потребность в доме"
        homeClientColumn.setSortable(false);
        homeRealtorColumn.setSortable(false);
        homeAddressColumn.setSortable(false);
        homeMinPriceColumn.setSortable(false);
        homeMaxPriceColumn.setSortable(false);
        homeMinSquareColumn.setSortable(false);
        homeMaxSquareColumn.setSortable(false);
        homeMinNumberOfRoomsColumn.setSortable(false);
        homeMaxNumberOfRoomsColumn.setSortable(false);
        homeMinNumberOfFloorsColumn.setSortable(false);
        homeMaxNumberOfFloorsColumn.setSortable(false);

        //определение колонок таблицы с соответствующими полями объекта "Потребность в квартире"
        flatClientColumn.setCellValueFactory(new PropertyValueFactory<>("client"));
        flatRealtorColumn.setCellValueFactory(new PropertyValueFactory<>("realtor"));
        flatAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        flatMinPriceColumn.setCellValueFactory(new PropertyValueFactory<>("minPrice"));
        flatMaxPriceColumn.setCellValueFactory(new PropertyValueFactory<>("maxPrice"));
        flatMinSquareColumn.setCellValueFactory(new PropertyValueFactory<>("minSquare"));
        flatMaxSquareColumn.setCellValueFactory(new PropertyValueFactory<>("maxSquare"));
        flatMinNumberOfRoomsColumn.setCellValueFactory(new PropertyValueFactory<>("minNumberOfRooms"));
        flatMaxNumberOfRoomsColumn.setCellValueFactory(new PropertyValueFactory<>("maxNumberOfRooms"));
        flatMinFloorColumn.setCellValueFactory(new PropertyValueFactory<>("minFloor"));
        flatMaxFloorColumn.setCellValueFactory(new PropertyValueFactory<>("maxFloor"));

        //отключение возможности сортировки полей таблицы объектов "Потребность в квартире"
        flatClientColumn.setSortable(false);
        flatRealtorColumn.setSortable(false);
        flatAddressColumn.setSortable(false);
        flatMinPriceColumn.setSortable(false);
        flatMaxPriceColumn.setSortable(false);
        flatMinSquareColumn.setSortable(false);
        flatMaxSquareColumn.setSortable(false);
        flatMinNumberOfRoomsColumn.setSortable(false);
        flatMaxNumberOfRoomsColumn.setSortable(false);
        flatMinFloorColumn.setSortable(false);
        flatMaxFloorColumn.setSortable(false);

        //определение колонок таблицы с соответствующими полями объекта "Потребность в земле"
        landClientColumn.setCellValueFactory(new PropertyValueFactory<>("client"));
        landRealtorColumn.setCellValueFactory(new PropertyValueFactory<>("realtor"));
        landAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        landMinPriceColumn.setCellValueFactory(new PropertyValueFactory<>("minPrice"));
        landMaxPriceColumn.setCellValueFactory(new PropertyValueFactory<>("maxPrice"));
        landMinSquareColumn.setCellValueFactory(new PropertyValueFactory<>("minSquare"));
        landMaxSquareColumn.setCellValueFactory(new PropertyValueFactory<>("maxSquare"));

        //отключение возможности сортировки полей таблицы объектов "Потребность в земле"
        landClientColumn.setSortable(false);
        landRealtorColumn.setSortable(false);
        landAddressColumn.setSortable(false);
        landMinPriceColumn.setSortable(false);
        landMaxPriceColumn.setSortable(false);
        landMinSquareColumn.setSortable(false);
        landMaxSquareColumn.setSortable(false);

        ObservableList<Demand> listOfHomeDemands = createListOfHomeDemands(getDemandTableContent(HOME));
        ObservableList<Demand> listOfFlatDemands = createListOfFlatDemands(getDemandTableContent(FLAT));
        ObservableList<Demand> listOfLandDemands = createListOfLandDemands(getDemandTableContent(LAND));

        //заполняем таблицы данными из БД
        tableHomes.setItems(listOfHomeDemands);
        tableFlats.setItems(listOfFlatDemands);
        tableLands.setItems(listOfLandDemands);

        //Заносим данные выделенного объекта "Потребность в доме" в текстовые поля при клике на объект в таблице
        tableHomes.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedHome) -> {
            if (selectedHome != null) {

                idSelectedHomeDemand = idHomesArray.get(tableHomes.getSelectionModel().getSelectedIndex());
                System.out.println(idSelectedHomeDemand);

                //устанавливаем значения выделенного объекта в текстовые поля
                homeTextFieldAddress.setText(tableHomes.getSelectionModel().getSelectedItem().getAddress());
                homeTextFieldMinPrice.setText(String.valueOf(tableHomes.getSelectionModel().getSelectedItem().getMinPrice()));
                homeTextFieldMaxPrice.setText(String.valueOf(tableHomes.getSelectionModel().getSelectedItem().getMaxPrice()));
                homeTextFieldMinSquare.setText(String.valueOf(tableHomes.getSelectionModel().getSelectedItem().getMinSquare()));
                homeTextFieldMaxSquare.setText(String.valueOf(tableHomes.getSelectionModel().getSelectedItem().getMaxSquare()));
                homeTextFieldMinNumberOfRooms.setText(String.valueOf(tableHomes.getSelectionModel().getSelectedItem().getMinNumberOfRooms()));
                homeTextFieldMaxNumberOfRooms.setText(String.valueOf(tableHomes.getSelectionModel().getSelectedItem().getMaxNumberOfRooms()));
                homeTextFieldMinNumberOfFloors.setText(String.valueOf(tableHomes.getSelectionModel().getSelectedItem().getMinNumberOfFloors()));
                homeTextFieldMaxNumberOfFloors.setText(String.valueOf(tableHomes.getSelectionModel().getSelectedItem().getMaxNumberOfFloors()));

                createHomeDemandButton.setDisable(true);
                updateHomeDemandButton.setDisable(true);
                deleteHomeDemandButton.setDisable(false);

                //Слушатели для текстовых полей для проверки на изменение какого либо значения и предоставления возможности обновления потребности
                homeClientComboBox.valueProperty().addListener((observable1, oldValue1, client) -> {
                    try {
                        if (!tableHomes.getSelectionModel().getSelectedItem().getClient().equals(client.getLastName())) {
                            updateHomeDemandButton.setDisable(false);
                        } else if (tableHomes.getSelectionModel().getSelectedItem().getClient().equals(client.getLastName())) {
                            updateHomeDemandButton.setDisable(true);
                        }
                    } catch (Exception ignored) {
                    }
                });

                homeRealtorComboBox.valueProperty().addListener((observable1, oldValue1, realtor) -> {
                    try {
                        if (!tableHomes.getSelectionModel().getSelectedItem().getRealtor().equals(realtor.getLastName())) {
                            updateHomeDemandButton.setDisable(false);
                        } else if (tableHomes.getSelectionModel().getSelectedItem().getRealtor().equals(realtor.getLastName())) {
                            updateHomeDemandButton.setDisable(true);
                        }
                    } catch (Exception ignored) {
                    }
                });

                homeTextFieldAddress.textProperty().addListener((observable1, oldValue1, address) -> {
                    try {
                        if (!tableHomes.getSelectionModel().getSelectedItem().getAddress().equals(address)) {
                            updateHomeDemandButton.setDisable(false);
                        } else if (tableHomes.getSelectionModel().getSelectedItem().getAddress().equals(address)) {
                            updateHomeDemandButton.setDisable(true);
                        }
                    } catch (Exception ignored) {
                    }
                });

                homeTextFieldMinPrice.textProperty().addListener((observable1, oldValue1, minPrice) -> {
                    try {
                        if (tableHomes.getSelectionModel().getSelectedItem().getMinPrice() != Integer.parseInt(minPrice)) {
                            updateHomeDemandButton.setDisable(false);
                        } else if (tableHomes.getSelectionModel().getSelectedItem().getMinPrice() == Integer.parseInt(minPrice)) {
                            updateHomeDemandButton.setDisable(true);
                        }
                    } catch (Exception ignored) {
                    }
                });

                homeTextFieldMaxPrice.textProperty().addListener((observable1, oldValue1, maxPrice) -> {
                    try {
                        if (tableHomes.getSelectionModel().getSelectedItem().getMaxPrice() != Integer.parseInt(maxPrice)) {
                            updateHomeDemandButton.setDisable(false);
                        } else if (tableHomes.getSelectionModel().getSelectedItem().getMaxPrice() == Integer.parseInt(maxPrice)) {
                            updateHomeDemandButton.setDisable(true);
                        }
                    } catch (Exception ignored) {
                    }
                });

                homeTextFieldMinSquare.textProperty().addListener((observable1, oldValue1, minSquare) -> {
                    try {
                        if (tableHomes.getSelectionModel().getSelectedItem().getMinSquare() != Integer.parseInt(minSquare)) {
                            updateHomeDemandButton.setDisable(false);
                        } else if (tableHomes.getSelectionModel().getSelectedItem().getMinSquare() == Integer.parseInt(minSquare)) {
                            updateHomeDemandButton.setDisable(true);
                        }
                    } catch (Exception ignored) {
                    }
                });

                homeTextFieldMaxSquare.textProperty().addListener((observable1, oldValue1, maxSquare) -> {
                    try {
                        if (tableHomes.getSelectionModel().getSelectedItem().getMaxSquare() != Integer.parseInt(maxSquare)) {
                            updateHomeDemandButton.setDisable(false);
                        } else if (tableHomes.getSelectionModel().getSelectedItem().getMaxSquare() == Integer.parseInt(maxSquare)) {
                            updateHomeDemandButton.setDisable(true);
                        }
                    } catch (Exception ignored) {
                    }
                });

                homeTextFieldMinNumberOfRooms.textProperty().addListener((observable1, oldValue1, minNumberOfRooms) -> {
                    try {
                        if (tableHomes.getSelectionModel().getSelectedItem().getMinNumberOfRooms() != Integer.parseInt(minNumberOfRooms)) {
                            updateHomeDemandButton.setDisable(false);
                        } else if (tableHomes.getSelectionModel().getSelectedItem().getMinNumberOfRooms() == Integer.parseInt(minNumberOfRooms)) {
                            updateHomeDemandButton.setDisable(true);
                        }
                    } catch (Exception ignored) {
                    }
                });

                homeTextFieldMaxNumberOfRooms.textProperty().addListener((observable1, oldValue1, maxNumberOfRooms) -> {
                    try {
                        if (tableHomes.getSelectionModel().getSelectedItem().getMaxNumberOfRooms() != Integer.parseInt(maxNumberOfRooms)) {
                            updateHomeDemandButton.setDisable(false);
                        } else if (tableHomes.getSelectionModel().getSelectedItem().getMaxNumberOfRooms() == Integer.parseInt(maxNumberOfRooms)) {
                            updateHomeDemandButton.setDisable(true);
                        }
                    } catch (Exception ignored) {
                    }
                });

                homeTextFieldMinNumberOfFloors.textProperty().addListener((observable1, oldValue1, minNumberOfFloors) -> {
                    try {
                        if (tableHomes.getSelectionModel().getSelectedItem().getMinNumberOfFloors() != Integer.parseInt(minNumberOfFloors)) {
                            updateHomeDemandButton.setDisable(false);
                        } else if (tableHomes.getSelectionModel().getSelectedItem().getMinNumberOfFloors() == Integer.parseInt(minNumberOfFloors)) {
                            updateHomeDemandButton.setDisable(true);
                        }
                    } catch (Exception ignored) {
                    }
                });

                homeTextFieldMaxNumberOfFloors.textProperty().addListener((observable1, oldValue1, maxNumberOfFloors) -> {
                    try {
                        if (tableHomes.getSelectionModel().getSelectedItem().getMaxNumberOfFloors() != Integer.parseInt(maxNumberOfFloors)) {
                            updateHomeDemandButton.setDisable(false);
                        } else if (tableHomes.getSelectionModel().getSelectedItem().getMaxNumberOfFloors() == Integer.parseInt(maxNumberOfFloors)) {
                            updateHomeDemandButton.setDisable(true);
                        }
                    } catch (Exception ignored) {
                    }
                });


                //установка значения клиента выбранного объекта в соответствующий выпадающий список
                if (tableHomes.getSelectionModel().getSelectedItem().getClient() != null) {
                    //получаем айди клиента из списка всех клиентов
                    for (int i = 0; i < listOfClients.size(); i++) {
                        if (tableHomes.getSelectionModel().getSelectedItem().getClient().equals(listOfClients.get(i).getLastName())) {
                            //устанавливаем в выпадающий список клиента
                            homeClientComboBox.getSelectionModel().select(i);
                        }
                    }
                }

                //установка значения риэлтора выбранного объекта в соответствующий выпадающий список
                if (tableHomes.getSelectionModel().getSelectedItem().getRealtor() != null) {
                    //получаем айди клиента из списка всех риэлторов
                    for (int i = 0; i < listOfRealtors.size(); i++) {
                        if (tableHomes.getSelectionModel().getSelectedItem().getRealtor().equals(listOfRealtors.get(i).getLastName())) {
                            //устанавливаем в выпадающий список риэлторов
                            homeRealtorComboBox.getSelectionModel().select(i);
                        }
                    }
                }

                createHomeDemandButton.setDisable(true);
                updateHomeDemandButton.setDisable(true);
                deleteHomeDemandButton.setDisable(false);

            } else {
                createHomeDemandButton.setDisable(false);
                updateHomeDemandButton.setDisable(true);
                deleteHomeDemandButton.setDisable(true);
            }
        });

        //Заносим данные выделенного объекта "Потребность в квартире" в текстовые поля при клике на объект в таблице
        tableFlats.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedFlat) -> {
            if (selectedFlat != null) {

                idSelectedFlatDemand = idFlatsArray.get(tableFlats.getSelectionModel().getSelectedIndex());
                System.out.println(idSelectedFlatDemand);

                //устанавливаем значения выделенного объекта в текстовые поля
                flatTextFieldAddress.setText(tableFlats.getSelectionModel().getSelectedItem().getAddress());
                flatTextFieldMinPrice.setText(String.valueOf(tableFlats.getSelectionModel().getSelectedItem().getMinPrice()));
                flatTextFieldMaxPrice.setText(String.valueOf(tableFlats.getSelectionModel().getSelectedItem().getMaxPrice()));
                flatTextFieldMinSquare.setText(String.valueOf(tableFlats.getSelectionModel().getSelectedItem().getMinSquare()));
                flatTextFieldMaxSquare.setText(String.valueOf(tableFlats.getSelectionModel().getSelectedItem().getMaxSquare()));
                flatTextFieldMinNumberOfRooms.setText(String.valueOf(tableFlats.getSelectionModel().getSelectedItem().getMinNumberOfRooms()));
                flatTextFieldMaxNumberOfRooms.setText(String.valueOf(tableFlats.getSelectionModel().getSelectedItem().getMaxNumberOfRooms()));
                flatTextFieldMinFloor.setText(String.valueOf(tableFlats.getSelectionModel().getSelectedItem().getMinFloor()));
                flatTextFieldMaxFloor.setText(String.valueOf(tableFlats.getSelectionModel().getSelectedItem().getMaxFloor()));

                createFlatDemandButton.setDisable(true);
                updateFlatDemandButton.setDisable(true);
                deleteFlatDemandButton.setDisable(false);

                //Слушатели для текстовых полей для проверки на изменение какого либо значения и предоставления возможности обновления потребности
                flatClientComboBox.valueProperty().addListener((observable1, oldValue1, client) -> {
                    try {
                        if (!tableFlats.getSelectionModel().getSelectedItem().getClient().equals(client.getLastName())) {
                            updateFlatDemandButton.setDisable(false);
                        } else if (tableFlats.getSelectionModel().getSelectedItem().getClient().equals(client.getLastName())) {
                            updateFlatDemandButton.setDisable(true);
                        }
                    } catch (Exception ignored) {
                    }
                });

                flatRealtorComboBox.valueProperty().addListener((observable1, oldValue1, realtor) -> {
                    try {
                        if (!tableFlats.getSelectionModel().getSelectedItem().getRealtor().equals(realtor.getLastName())) {
                            updateFlatDemandButton.setDisable(false);
                        } else if (tableFlats.getSelectionModel().getSelectedItem().getRealtor().equals(realtor.getLastName())) {
                            updateFlatDemandButton.setDisable(true);
                        }
                    } catch (Exception ignored) {
                    }
                });

                flatTextFieldAddress.textProperty().addListener((observable1, oldValue1, address) -> {
                    try {
                        if (!tableFlats.getSelectionModel().getSelectedItem().getAddress().equals(address)) {
                            updateFlatDemandButton.setDisable(false);
                        } else if (tableFlats.getSelectionModel().getSelectedItem().getAddress().equals(address)) {
                            updateFlatDemandButton.setDisable(true);
                        }
                    } catch (Exception ignored) {
                    }
                });

                flatTextFieldMinPrice.textProperty().addListener((observable1, oldValue1, minPrice) -> {
                    try {
                        if (tableFlats.getSelectionModel().getSelectedItem().getMinPrice() != Integer.parseInt(minPrice)) {
                            updateFlatDemandButton.setDisable(false);
                        } else if (tableFlats.getSelectionModel().getSelectedItem().getMinPrice() == Integer.parseInt(minPrice)) {
                            updateFlatDemandButton.setDisable(true);
                        }
                    } catch (Exception ignored) {
                    }
                });

                flatTextFieldMaxPrice.textProperty().addListener((observable1, oldValue1, maxPrice) -> {
                    try {
                        if (tableFlats.getSelectionModel().getSelectedItem().getMaxPrice() != Integer.parseInt(maxPrice)) {
                            updateFlatDemandButton.setDisable(false);
                        } else if (tableFlats.getSelectionModel().getSelectedItem().getMaxPrice() == Integer.parseInt(maxPrice)) {
                            updateFlatDemandButton.setDisable(true);
                        }
                    } catch (Exception ignored) {
                    }
                });

                flatTextFieldMinSquare.textProperty().addListener((observable1, oldValue1, minSquare) -> {
                    try {
                        if (tableFlats.getSelectionModel().getSelectedItem().getMinSquare() != Integer.parseInt(minSquare)) {
                            updateFlatDemandButton.setDisable(false);
                        } else if (tableFlats.getSelectionModel().getSelectedItem().getMinSquare() == Integer.parseInt(minSquare)) {
                            updateFlatDemandButton.setDisable(true);
                        }
                    } catch (Exception ignored) {
                    }
                });

                flatTextFieldMaxSquare.textProperty().addListener((observable1, oldValue1, maxSquare) -> {
                    try {
                        if (tableFlats.getSelectionModel().getSelectedItem().getMaxSquare() != Integer.parseInt(maxSquare)) {
                            updateFlatDemandButton.setDisable(false);
                        } else if (tableFlats.getSelectionModel().getSelectedItem().getMaxSquare() == Integer.parseInt(maxSquare)) {
                            updateFlatDemandButton.setDisable(true);
                        }
                    } catch (Exception ignored) {
                    }
                });

                flatTextFieldMinNumberOfRooms.textProperty().addListener((observable1, oldValue1, minNumberOfRooms) -> {
                    try {
                        if (tableFlats.getSelectionModel().getSelectedItem().getMinNumberOfRooms() != Integer.parseInt(minNumberOfRooms)) {
                            updateFlatDemandButton.setDisable(false);
                        } else if (tableFlats.getSelectionModel().getSelectedItem().getMinNumberOfRooms() == Integer.parseInt(minNumberOfRooms)) {
                            updateFlatDemandButton.setDisable(true);
                        }
                    } catch (Exception ignored) {
                    }
                });

                flatTextFieldMaxNumberOfRooms.textProperty().addListener((observable1, oldValue1, maxNumberOfRooms) -> {
                    try {
                        if (tableFlats.getSelectionModel().getSelectedItem().getMaxNumberOfRooms() != Integer.parseInt(maxNumberOfRooms)) {
                            updateFlatDemandButton.setDisable(false);
                        } else if (tableFlats.getSelectionModel().getSelectedItem().getMaxNumberOfRooms() == Integer.parseInt(maxNumberOfRooms)) {
                            updateFlatDemandButton.setDisable(true);
                        }
                    } catch (Exception ignored) {
                    }
                });

                flatTextFieldMinFloor.textProperty().addListener((observable1, oldValue1, minNumberOfFloors) -> {
                    try {
                        if (tableFlats.getSelectionModel().getSelectedItem().getMinFloor() != Integer.parseInt(minNumberOfFloors)) {
                            updateFlatDemandButton.setDisable(false);
                        } else if (tableFlats.getSelectionModel().getSelectedItem().getMinFloor() == Integer.parseInt(minNumberOfFloors)) {
                            updateFlatDemandButton.setDisable(true);
                        }
                    } catch (Exception ignored) {
                    }
                });

                flatTextFieldMaxFloor.textProperty().addListener((observable1, oldValue1, maxNumberOfFloors) -> {
                    try {
                        if (tableFlats.getSelectionModel().getSelectedItem().getMaxFloor() != Integer.parseInt(maxNumberOfFloors)) {
                            updateFlatDemandButton.setDisable(false);
                        } else if (tableFlats.getSelectionModel().getSelectedItem().getMaxFloor() == Integer.parseInt(maxNumberOfFloors)) {
                            updateFlatDemandButton.setDisable(true);
                        }
                    } catch (Exception ignored) {
                    }
                });


                //установка значения клиента выбранного объекта в соответствующий выпадающий список
                if (tableFlats.getSelectionModel().getSelectedItem().getClient() != null) {
                    //получаем айди клиента из списка всех клиентов
                    for (int i = 0; i < listOfClients.size(); i++) {
                        if (tableFlats.getSelectionModel().getSelectedItem().getClient().equals(listOfClients.get(i).getLastName())) {
                            //устанавливаем в выпадающий список клиента
                            flatClientComboBox.getSelectionModel().select(i);
                        }
                    }
                }

                //установка значения риэлтора выбранного объекта в соответствующий выпадающий список
                if (tableFlats.getSelectionModel().getSelectedItem().getRealtor() != null) {
                    //получаем айди клиента из списка всех риэлторов
                    for (int i = 0; i < listOfRealtors.size(); i++) {
                        if (tableFlats.getSelectionModel().getSelectedItem().getRealtor().equals(listOfRealtors.get(i).getLastName())) {
                            //устанавливаем в выпадающий список риэлторов
                            flatRealtorComboBox.getSelectionModel().select(i);
                        }
                    }
                }

                createFlatDemandButton.setDisable(true);
                updateFlatDemandButton.setDisable(true);
                deleteFlatDemandButton.setDisable(false);

            } else {
                createFlatDemandButton.setDisable(false);
                updateFlatDemandButton.setDisable(true);
                deleteFlatDemandButton.setDisable(true);
            }
        });

        //Заносим данные выделенного объекта "Потребность в земле" в текстовые поля при клике на объект в таблице
        tableLands.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedLand) -> {
            if (selectedLand != null) {

                idSelectedLandDemand = idLandsArray.get(tableLands.getSelectionModel().getSelectedIndex());
                System.out.println(idSelectedLandDemand);

                //устанавливаем значения выделенного объекта в текстовые поля
                landTextFieldAddress.setText(tableLands.getSelectionModel().getSelectedItem().getAddress());
                landTextFieldMinPrice.setText(String.valueOf(tableLands.getSelectionModel().getSelectedItem().getMinPrice()));
                landTextFieldMaxPrice.setText(String.valueOf(tableLands.getSelectionModel().getSelectedItem().getMaxPrice()));
                landTextFieldMinSquare.setText(String.valueOf(tableLands.getSelectionModel().getSelectedItem().getMinSquare()));
                landTextFieldMaxSquare.setText(String.valueOf(tableLands.getSelectionModel().getSelectedItem().getMaxSquare()));

                createLandDemandButton.setDisable(true);
                updateLandDemandButton.setDisable(true);
                deleteLandDemandButton.setDisable(false);

                //Слушатели для текстовых полей для проверки на изменение какого либо значения и предоставления возможности обновления потребности
                landClientComboBox.valueProperty().addListener((observable1, oldValue1, client) -> {
                    try {
                        if (!tableLands.getSelectionModel().getSelectedItem().getClient().equals(client.getLastName())) {
                            updateLandDemandButton.setDisable(false);
                        } else if (tableLands.getSelectionModel().getSelectedItem().getClient().equals(client.getLastName())) {
                            updateLandDemandButton.setDisable(true);
                        }
                    } catch (Exception ignored) {
                    }
                });

                landRealtorComboBox.valueProperty().addListener((observable1, oldValue1, realtor) -> {
                    try {
                        if (!tableLands.getSelectionModel().getSelectedItem().getRealtor().equals(realtor.getLastName())) {
                            updateLandDemandButton.setDisable(false);
                        } else if (tableLands.getSelectionModel().getSelectedItem().getRealtor().equals(realtor.getLastName())) {
                            updateLandDemandButton.setDisable(true);
                        }
                    } catch (Exception ignored) {
                    }
                });

                landTextFieldAddress.textProperty().addListener((observable1, oldValue1, address) -> {
                    try {
                        if (!tableLands.getSelectionModel().getSelectedItem().getAddress().equals(address)) {
                            updateLandDemandButton.setDisable(false);
                        } else if (tableLands.getSelectionModel().getSelectedItem().getAddress().equals(address)) {
                            updateLandDemandButton.setDisable(true);
                        }
                    } catch (Exception ignored) {
                    }
                });

                landTextFieldMinPrice.textProperty().addListener((observable1, oldValue1, minPrice) -> {
                    try {
                        if (tableLands.getSelectionModel().getSelectedItem().getMinPrice() != Integer.parseInt(minPrice)) {
                            updateLandDemandButton.setDisable(false);
                        } else if (tableLands.getSelectionModel().getSelectedItem().getMinPrice() == Integer.parseInt(minPrice)) {
                            updateLandDemandButton.setDisable(true);
                        }
                    } catch (Exception ignored) {
                    }
                });

                landTextFieldMaxPrice.textProperty().addListener((observable1, oldValue1, maxPrice) -> {
                    try {
                        if (tableLands.getSelectionModel().getSelectedItem().getMaxPrice() != Integer.parseInt(maxPrice)) {
                            updateLandDemandButton.setDisable(false);
                        } else if (tableLands.getSelectionModel().getSelectedItem().getMaxPrice() == Integer.parseInt(maxPrice)) {
                            updateLandDemandButton.setDisable(true);
                        }
                    } catch (Exception ignored) {
                    }
                });

                landTextFieldMinSquare.textProperty().addListener((observable1, oldValue1, minSquare) -> {
                    try {
                        if (tableLands.getSelectionModel().getSelectedItem().getMinSquare() != Integer.parseInt(minSquare)) {
                            updateLandDemandButton.setDisable(false);
                        } else if (tableLands.getSelectionModel().getSelectedItem().getMinSquare() == Integer.parseInt(minSquare)) {
                            updateLandDemandButton.setDisable(true);
                        }
                    } catch (Exception ignored) {
                    }
                });

                landTextFieldMaxSquare.textProperty().addListener((observable1, oldValue1, maxSquare) -> {
                    try {
                        if (tableLands.getSelectionModel().getSelectedItem().getMaxSquare() != Integer.parseInt(maxSquare)) {
                            updateLandDemandButton.setDisable(false);
                        } else if (tableLands.getSelectionModel().getSelectedItem().getMaxSquare() == Integer.parseInt(maxSquare)) {
                            updateLandDemandButton.setDisable(true);
                        }
                    } catch (Exception ignored) {
                    }
                });

                //установка значения клиента выбранного объекта в соответствующий выпадающий список
                if (tableLands.getSelectionModel().getSelectedItem().getClient() != null) {
                    //получаем айди клиента из списка всех клиентов
                    for (int i = 0; i < listOfClients.size(); i++) {
                        if (tableLands.getSelectionModel().getSelectedItem().getClient().equals(listOfClients.get(i).getLastName())) {
                            //устанавливаем в выпадающий список клиента
                            landClientComboBox.getSelectionModel().select(i);
                        }
                    }
                }

                //установка значения риэлтора выбранного объекта в соответствующий выпадающий список
                if (tableLands.getSelectionModel().getSelectedItem().getRealtor() != null) {
                    //получаем айди клиента из списка всех риэлторов
                    for (int i = 0; i < listOfRealtors.size(); i++) {
                        if (tableLands.getSelectionModel().getSelectedItem().getRealtor().equals(listOfRealtors.get(i).getLastName())) {
                            //устанавливаем в выпадающий список риэлторов
                            landRealtorComboBox.getSelectionModel().select(i);
                        }
                    }
                }

                createLandDemandButton.setDisable(true);
                updateLandDemandButton.setDisable(true);
                deleteLandDemandButton.setDisable(false);

            } else {
                createLandDemandButton.setDisable(false);
                updateLandDemandButton.setDisable(true);
                deleteLandDemandButton.setDisable(true);
            }
        });

    }

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
                Realtor realtor = new Realtor();
                realtor.setLastName(resultSet.getString("lastName"));
                realtor.setFirstName(resultSet.getString("firstName"));
                realtor.setMiddleName(resultSet.getString("middleName"));

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
     * Метод для получения содержимого таблицы потребностей из базы данных
     *
     * @param typeDemand тип объекта недвижимости. Принимает одно из 3 значений: дом, квартира, земля.
     * @return ResultSet - набор данных из таблицы
     */
    private ResultSet getDemandTableContent(String typeDemand) {
        //SQL запрос на выбор всех данных из таблицы `demands`
        String selectHomeDemand = String.format("SELECT * FROM %s WHERE %s = ?", DEMANDS_TABLE, TYPE_REAL_ESTATE);

        ResultSet resultSet = null;
        try {
            PreparedStatement ps = new DatabaseHandler().createDbConnection().prepareStatement(selectHomeDemand);
            ps.setString(1, typeDemand);
            //выполняем запрос и сохраняем полученные значения в resultSet
            resultSet = ps.executeQuery();
        } catch (SQLException e) {
            //открываем диалоговое окно для уведомления об ошибке
            showAlert(
                    "Ошибка получения данных из таблицы " + DEMANDS_TABLE,
                    "Данные не получены из базы. Проверьте подключение к базе!",
                    Alert.AlertType.ERROR);
        }

        return resultSet;
    }

    /**
     * Метод для формирования ObservableList с потребностиями в доме из набора данных
     *
     * @param resultSet набор данных из таблицы `demands`
     * @return ObservableList с объектами класса Demand
     */
    private ObservableList<Demand> createListOfHomeDemands(ResultSet resultSet) {
        ObservableList<Demand> list = FXCollections.observableArrayList();
        idHomesArray = new ArrayList<>();
        try {
            while (resultSet.next()) {
                //Создание объекта "Потребность"
                Demand demand = new Demand();
                demand.setClient(resultSet.getString(CLIENT_COLUMN));
                demand.setRealtor(resultSet.getString(REALTOR_COLUMN));
                demand.setAddress(resultSet.getString(ADDRESS_COLUMN));
                demand.setMinPrice(resultSet.getInt(MIN_PRICE_COLUMN));
                demand.setMaxPrice(resultSet.getInt(MAX_PRICE_COLUMN));
                demand.setMinSquare(resultSet.getInt(MIN_SQUARE_COLUMN));
                demand.setMaxSquare(resultSet.getInt(MAX_SQUARE_COLUMN));
                demand.setMinNumberOfRooms(resultSet.getInt(MIN_COUNT_OF_ROOMS_COLUMN));
                demand.setMaxNumberOfRooms(resultSet.getInt(MAX_COUNT_OF_ROOMS_COLUMN));
                demand.setMinNumberOfFloors(resultSet.getInt(MIN_NUMBER_OF_FLOORS_COLUMN));
                demand.setMaxNumberOfFloors(resultSet.getInt(MAX_NUMBER_OF_FLOORS_COLUMN));

                //добавляем объект в список
                System.out.println(demand);

                list.add(demand);
                idHomesArray.add(resultSet.getInt(ID_COLUMN));
            }
        } catch (SQLException e) {
            //открываем диалоговое окно для уведомления об ошибке
            showAlert(
                    "Ошибка формирования коллекции потребностей",
                    "Возникла ошибка при формировании списка потребностей в доме из набора данных, полученных из базы",
                    Alert.AlertType.ERROR);
        }

        return list;
    }

    /**
     * Метод для формирования ObservableList с потребностиями в квартире из набора данных
     *
     * @param resultSet набор данных из таблицы `demands`
     * @return ObservableList с объектами класса Demand
     */
    private ObservableList<Demand> createListOfFlatDemands(ResultSet resultSet) {
        ObservableList<Demand> list = FXCollections.observableArrayList();
        idFlatsArray = new ArrayList<>();
        try {
            while (resultSet.next()) {
                //Создание объекта "Потребность"
                Demand demand = new Demand();
                demand.setClient(resultSet.getString(CLIENT_COLUMN));
                demand.setRealtor(resultSet.getString(REALTOR_COLUMN));
                demand.setAddress(resultSet.getString(ADDRESS_COLUMN));
                demand.setMinPrice(resultSet.getInt(MIN_PRICE_COLUMN));
                demand.setMaxPrice(resultSet.getInt(MAX_PRICE_COLUMN));
                demand.setMinSquare(resultSet.getInt(MIN_SQUARE_COLUMN));
                demand.setMaxSquare(resultSet.getInt(MAX_SQUARE_COLUMN));
                demand.setMinNumberOfRooms(resultSet.getInt(MIN_COUNT_OF_ROOMS_COLUMN));
                demand.setMaxNumberOfRooms(resultSet.getInt(MAX_COUNT_OF_ROOMS_COLUMN));
                demand.setMinFloor(resultSet.getInt(MIN_FLOOR));
                demand.setMaxFloor(resultSet.getInt(MAX_FLOOR));

                //добавляем объект в список
                System.out.println(demand);

                list.add(demand);
                idFlatsArray.add(resultSet.getInt(ID_COLUMN));
            }
        } catch (SQLException e) {
            //открываем диалоговое окно для уведомления об ошибке
            showAlert(
                    "Ошибка формирования коллекции потребностей",
                    "Возникла ошибка при формировании списка потребностей в квартире из набора данных, полученных из базы",
                    Alert.AlertType.ERROR);
        }

        return list;
    }

    /**
     * Метод для формирования ObservableList с потребностиями в земле из набора данных
     *
     * @param resultSet набор данных из таблицы `demands`
     * @return ObservableList с объектами класса Demand
     */
    private ObservableList<Demand> createListOfLandDemands(ResultSet resultSet) {
        ObservableList<Demand> list = FXCollections.observableArrayList();
        idLandsArray = new ArrayList<>();
        try {
            while (resultSet.next()) {
                //Создание объекта "Потребность"
                Demand demand = new Demand();
                demand.setClient(resultSet.getString(CLIENT_COLUMN));
                demand.setRealtor(resultSet.getString(REALTOR_COLUMN));
                demand.setAddress(resultSet.getString(ADDRESS_COLUMN));
                demand.setMinPrice(resultSet.getInt(MIN_PRICE_COLUMN));
                demand.setMaxPrice(resultSet.getInt(MAX_PRICE_COLUMN));
                demand.setMinSquare(resultSet.getInt(MIN_SQUARE_COLUMN));
                demand.setMaxSquare(resultSet.getInt(MAX_SQUARE_COLUMN));

                //добавляем объект в список
                System.out.println(demand);

                list.add(demand);
                idLandsArray.add(resultSet.getInt(ID_COLUMN));
            }
        } catch (SQLException e) {
            //открываем диалоговое окно для уведомления об ошибке
            showAlert(
                    "Ошибка формирования коллекции потребностей",
                    "Возникла ошибка при формировании списка потребностей в земле из набора данных, полученных из базы",
                    Alert.AlertType.ERROR);
        }

        return list;
    }

    @FXML
    void goHomeScreen(ActionEvent event) {
        ((Node) event.getSource()).getScene().getWindow().hide();
        //переход на главный экран
        Helper.openNewScreen(PATH_TO_HOME_SCREEN);
    }

    /**
     * Метод для смены вкладок внутри экрана при нажатии на соответствующий тип объекта недвижимости
     *
     * @param event нажатие на кнопку
     */
    @FXML
    void switchContent(ActionEvent event) {
        if (event.getSource().equals(homeDemandsButton)) {
            headerTypeBuildingLabel.setText(HOME_DEMAND);
            landDemandsButton.setStyle("");
            flatDemandsButton.setStyle("");
            homeDemandsButton.setStyle(BUTTON_COLOR);
            homesAnchorPane.toFront();
        } else if (event.getSource().equals(flatDemandsButton)) {
            headerTypeBuildingLabel.setText(FLAT_DEMAND);
            homeDemandsButton.setStyle("");
            landDemandsButton.setStyle("");
            flatDemandsButton.setStyle(BUTTON_COLOR);
            flatsAnchorPane.toFront();
        } else if (event.getSource().equals(landDemandsButton)) {
            headerTypeBuildingLabel.setText(LAND_DEMAND);
            homeDemandsButton.setStyle("");
            flatDemandsButton.setStyle("");
            landDemandsButton.setStyle(BUTTON_COLOR);
            landsAnchorPane.toFront();
        }
    }

    /**
     * Метод для создания потребности в доме
     */
    @FXML
    void createHomeDemand() {
        if (homeClientComboBox.getValue() != null &&
                homeRealtorComboBox.getValue() != null &&
                !homeTextFieldAddress.getText().isEmpty() &&
                !homeTextFieldMinPrice.getText().isEmpty() &&
                !homeTextFieldMaxPrice.getText().isEmpty()) {

            //создание объекта потребности в доме
            Demand homeDemand = new Demand();
            homeDemand.setClient(homeClientComboBox.getValue().getLastName());
            homeDemand.setRealtor(homeRealtorComboBox.getValue().getLastName());
            homeDemand.setAddress(homeTextFieldAddress.getText());
            homeDemand.setMinPrice(Integer.parseInt(homeTextFieldMinPrice.getText()));
            homeDemand.setMaxPrice(Integer.parseInt(homeTextFieldMaxPrice.getText()));
            homeDemand.setTypeRealEstate(HOME);

            //min square
            if (!homeTextFieldMinSquare.getText().isEmpty()) {
                homeDemand.setMinSquare(Integer.parseInt(homeTextFieldMinSquare.getText()));
            }
            //max square
            if (!homeTextFieldMaxSquare.getText().isEmpty()) {
                homeDemand.setMaxSquare(Integer.parseInt(homeTextFieldMaxSquare.getText()));
            }
            //min rooms
            if (!homeTextFieldMinNumberOfRooms.getText().isEmpty()) {
                homeDemand.setMinNumberOfRooms(Integer.parseInt(homeTextFieldMinNumberOfRooms.getText()));
            }
            //max rooms
            if (!homeTextFieldMaxNumberOfRooms.getText().isEmpty()) {
                homeDemand.setMaxNumberOfRooms(Integer.parseInt(homeTextFieldMaxNumberOfRooms.getText()));
            }
            //min floors
            if (!homeTextFieldMinNumberOfFloors.getText().isEmpty()) {
                homeDemand.setMinNumberOfFloors(Integer.parseInt(homeTextFieldMinNumberOfFloors.getText()));
            }
            //max floors
            if (!homeTextFieldMaxNumberOfFloors.getText().isEmpty()) {
                homeDemand.setMaxNumberOfFloors(Integer.parseInt(homeTextFieldMaxNumberOfFloors.getText()));
            }

            //SQL запрос для добавления потребности в доме
            String insertHomeDemand = String.format("INSERT INTO %s(%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s) VALUES (?,?,?,?,?,?,?,?,?,?,?,?);",
                    DEMANDS_TABLE,
                    CLIENT_COLUMN,
                    REALTOR_COLUMN,
                    TYPE_REAL_ESTATE,
                    ADDRESS_COLUMN,
                    MIN_PRICE_COLUMN,
                    MAX_PRICE_COLUMN,
                    MIN_SQUARE_COLUMN,
                    MAX_SQUARE_COLUMN,
                    MIN_COUNT_OF_ROOMS_COLUMN,
                    MAX_COUNT_OF_ROOMS_COLUMN,
                    MIN_NUMBER_OF_FLOORS_COLUMN,
                    MAX_NUMBER_OF_FLOORS_COLUMN);

            try {
                PreparedStatement addHomeStatement = new DatabaseHandler().createDbConnection().prepareStatement(insertHomeDemand);

                //установка значений для вставки в запрос
                addHomeStatement.setString(1, homeDemand.getClient());
                addHomeStatement.setString(2, homeDemand.getRealtor());
                addHomeStatement.setString(3, homeDemand.getTypeRealEstate());
                addHomeStatement.setString(4, homeDemand.getAddress());
                addHomeStatement.setInt(5, homeDemand.getMinPrice());
                addHomeStatement.setInt(6, homeDemand.getMaxPrice());
                addHomeStatement.setInt(7, homeDemand.getMinSquare());
                addHomeStatement.setInt(8, homeDemand.getMaxSquare());
                addHomeStatement.setInt(9, homeDemand.getMinNumberOfRooms());
                addHomeStatement.setInt(10, homeDemand.getMaxNumberOfRooms());
                addHomeStatement.setInt(11, homeDemand.getMinNumberOfFloors());
                addHomeStatement.setInt(12, homeDemand.getMaxNumberOfFloors());

                //выполнение запроса
                addHomeStatement.executeUpdate();

                //открываем диалоговое окно для уведомления об успешном добавлении
                showAlert(
                        "Операция успешно выполнена",
                        "Потребность в доме успешно добавлена!",
                        Alert.AlertType.INFORMATION);

                tableHomes.setItems(createListOfHomeDemands(getDemandTableContent(HOME)));

                //обнуляем текстовые поля и выпадающие списки после добавления
                clearTextFields(listOfHomeDemandTextFields);
                homeClientComboBox.setValue(null);
                homeRealtorComboBox.setValue(null);

            } catch (SQLException e) {
                //открываем диалоговое окно для уведомления об ошибке
                showAlert(
                        "Ошибка добавления потребности в доме",
                        "Потребность в доме не была добавлена в базу. Повторите еще раз",
                        Alert.AlertType.ERROR);
            }


        } else {
            //открываем диалоговое окно для уведомления об ошибке
            showAlert(
                    "Ошибка добавления потребности в доме",
                    "Поля: клиент, риэлтор, адрес, мин. цена и макс. цена являются обязательными для заполнения",
                    Alert.AlertType.ERROR);
        }
    }

    /**
     * Метод для обновления потребности в доме
     */
    @FXML
    void updateHomeDemand() {
        if (homeClientComboBox.getValue() != null &&
                homeRealtorComboBox.getValue() != null &&
                !homeTextFieldAddress.getText().isEmpty() &&
                !homeTextFieldMinPrice.getText().isEmpty() &&
                !homeTextFieldMaxPrice.getText().isEmpty()) {

            //SQL запрос для обновления потребности в доме
            String update = String.format("UPDATE %s SET %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=? WHERE %s=?;",
                    DEMANDS_TABLE,
                    CLIENT_COLUMN,
                    REALTOR_COLUMN,
                    ADDRESS_COLUMN,
                    MIN_PRICE_COLUMN,
                    MAX_PRICE_COLUMN,
                    MIN_SQUARE_COLUMN,
                    MAX_SQUARE_COLUMN,
                    MIN_COUNT_OF_ROOMS_COLUMN,
                    MAX_COUNT_OF_ROOMS_COLUMN,
                    MIN_NUMBER_OF_FLOORS_COLUMN,
                    MAX_NUMBER_OF_FLOORS_COLUMN,
                    ID_COLUMN);

            try {
                PreparedStatement updateHomeDemand = new DatabaseHandler().createDbConnection().prepareStatement(update);

                //установка значений для вставки в запрос
                updateHomeDemand.setString(1, homeClientComboBox.getValue().getLastName());
                updateHomeDemand.setString(2, homeRealtorComboBox.getValue().getLastName());
                updateHomeDemand.setString(3, homeTextFieldAddress.getText());
                updateHomeDemand.setInt(4, Integer.parseInt(homeTextFieldMinPrice.getText()));
                updateHomeDemand.setInt(5, Integer.parseInt(homeTextFieldMaxPrice.getText()));
                updateHomeDemand.setInt(6, Integer.parseInt(homeTextFieldMinSquare.getText()));
                updateHomeDemand.setInt(7, Integer.parseInt(homeTextFieldMaxSquare.getText()));
                updateHomeDemand.setInt(8, Integer.parseInt(homeTextFieldMinNumberOfRooms.getText()));
                updateHomeDemand.setInt(9, Integer.parseInt(homeTextFieldMaxNumberOfRooms.getText()));
                updateHomeDemand.setInt(10, Integer.parseInt(homeTextFieldMinNumberOfFloors.getText()));
                updateHomeDemand.setInt(11, Integer.parseInt(homeTextFieldMaxNumberOfFloors.getText()));
                updateHomeDemand.setInt(12, idSelectedHomeDemand);

                //выполнение SQL запроса
                updateHomeDemand.executeUpdate();

                //заполняем таблицу данным из БД
                tableHomes.setItems(createListOfHomeDemands(getDemandTableContent(HOME)));

                //открываем диалоговое окно для уведомления об успешном обновлении
                showAlert(
                        "Операция успешно выполнена",
                        "Обновление потребности в доме выполнено успешно!",
                        Alert.AlertType.INFORMATION);

                //обнуляем текстовые поля и выпадающие списки после обновления
                clearTextFields(listOfHomeDemandTextFields);
                homeClientComboBox.setValue(null);
                homeRealtorComboBox.setValue(null);

            } catch (SQLException e) {
                //открываем диалоговое окно для уведомления об ошибке
                showAlert(
                        "Ошибка обновления потребности в доме",
                        "Обновление потребности в доме не выполнено! Повторите еще раз",
                        Alert.AlertType.ERROR);
            }

        } else {
            //открываем диалоговое окно для уведомления об ошибке
            showAlert(
                    "Ошибка обновления потребности в доме",
                    "Поля: клиент, риэлтор, адрес, мин. цена и макс. цена являются обязательными для заполнения",
                    Alert.AlertType.ERROR);
        }
    }

    /**
     * Метод для удаления потребности в доме
     */
    @FXML
    void deleteHomeDemand() {
        //SQL запрос на удаление предложения
        String deleteHomeDemand = String.format("DELETE FROM %s WHERE %s=?", DEMANDS_TABLE, ID_COLUMN);

        try {
            PreparedStatement deleteOfferStatement = new DatabaseHandler().createDbConnection().prepareStatement(deleteHomeDemand);
            //установка значений для вставки в запрос
            deleteOfferStatement.setInt(1, idSelectedHomeDemand);
            //выполнение запроса на удаление
            deleteOfferStatement.executeUpdate();

            //открываем диалоговое окно для уведомления об успешном удалении
            showAlert(
                    "Операция успешно выполнена",
                    "Удаление потребности в доме выполнено успешно!",
                    Alert.AlertType.INFORMATION);

            //обнуляем текстовые поля и выпадающие списки после удаления
            clearTextFields(listOfHomeDemandTextFields);
            homeClientComboBox.setValue(null);
            homeRealtorComboBox.setValue(null);

            tableHomes.setItems(createListOfHomeDemands(getDemandTableContent(HOME)));

        } catch (SQLException e) {
            //открываем диалоговое окно для уведомления об ошибке
            showAlert(
                    "Ошибка удаления потребности в доме",
                    String.format("Возникла ошибка при удалении потребности в доме с ID = %d", idSelectedHomeDemand),
                    Alert.AlertType.ERROR
            );
        }
    }

    /**
     * Метод для создания потребности в квартире
     */
    @FXML
    void createFlatDemand() {
        if (flatClientComboBox.getValue() != null &&
                flatRealtorComboBox.getValue() != null &&
                !flatTextFieldAddress.getText().isEmpty() &&
                !flatTextFieldMinPrice.getText().isEmpty() &&
                !flatTextFieldMaxPrice.getText().isEmpty()) {

            //создание объекта потребности в доме
            Demand flatDemand = new Demand();
            flatDemand.setClient(flatClientComboBox.getValue().getLastName());
            flatDemand.setRealtor(flatRealtorComboBox.getValue().getLastName());
            flatDemand.setTypeRealEstate(FLAT);
            flatDemand.setAddress(flatTextFieldAddress.getText());
            flatDemand.setMinPrice(Integer.parseInt(flatTextFieldMinPrice.getText()));
            flatDemand.setMaxPrice(Integer.parseInt(flatTextFieldMaxPrice.getText()));

            //min square
            if (!flatTextFieldMinSquare.getText().isEmpty()) {
                flatDemand.setMinSquare(Integer.parseInt(flatTextFieldMinSquare.getText()));
            }
            //max square
            if (!flatTextFieldMaxSquare.getText().isEmpty()) {
                flatDemand.setMaxSquare(Integer.parseInt(flatTextFieldMaxSquare.getText()));
            }
            //min rooms
            if (!flatTextFieldMinNumberOfRooms.getText().isEmpty()) {
                flatDemand.setMinNumberOfRooms(Integer.parseInt(flatTextFieldMinNumberOfRooms.getText()));
            }
            //max rooms
            if (!flatTextFieldMaxNumberOfRooms.getText().isEmpty()) {
                flatDemand.setMaxNumberOfRooms(Integer.parseInt(flatTextFieldMaxNumberOfRooms.getText()));
            }
            //min floors
            if (!flatTextFieldMinFloor.getText().isEmpty()) {
                flatDemand.setMinFloor(Integer.parseInt(flatTextFieldMinFloor.getText()));
            }
            //max floors
            if (!flatTextFieldMaxFloor.getText().isEmpty()) {
                flatDemand.setMaxFloor(Integer.parseInt(flatTextFieldMaxFloor.getText()));
            }

            //SQL запрос для добавления потребности в доме
            String insertFlatDemand = String.format("INSERT INTO %s(%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s) VALUES (?,?,?,?,?,?,?,?,?,?,?,?);",
                    DEMANDS_TABLE,
                    CLIENT_COLUMN,
                    REALTOR_COLUMN,
                    TYPE_REAL_ESTATE,
                    ADDRESS_COLUMN,
                    MIN_PRICE_COLUMN,
                    MAX_PRICE_COLUMN,
                    MIN_SQUARE_COLUMN,
                    MAX_SQUARE_COLUMN,
                    MIN_COUNT_OF_ROOMS_COLUMN,
                    MAX_COUNT_OF_ROOMS_COLUMN,
                    MIN_FLOOR,
                    MAX_FLOOR);

            try {
                PreparedStatement addFlatStatement = new DatabaseHandler().createDbConnection().prepareStatement(insertFlatDemand);

                //установка значений для вставки в запрос
                addFlatStatement.setString(1, flatDemand.getClient());
                addFlatStatement.setString(2, flatDemand.getRealtor());
                addFlatStatement.setString(3, flatDemand.getTypeRealEstate());
                addFlatStatement.setString(4, flatDemand.getAddress());
                addFlatStatement.setInt(5, flatDemand.getMinPrice());
                addFlatStatement.setInt(6, flatDemand.getMaxPrice());
                addFlatStatement.setInt(7, flatDemand.getMinSquare());
                addFlatStatement.setInt(8, flatDemand.getMaxSquare());
                addFlatStatement.setInt(9, flatDemand.getMinNumberOfRooms());
                addFlatStatement.setInt(10, flatDemand.getMaxNumberOfRooms());
                addFlatStatement.setInt(11, flatDemand.getMinFloor());
                addFlatStatement.setInt(12, flatDemand.getMaxFloor());

                //выполнение запроса
                addFlatStatement.executeUpdate();

                //открываем диалоговое окно для уведомления об успешном добавлении
                showAlert(
                        "Операция успешно выполнена",
                        "Потребность в квартире успешно добавлена!",
                        Alert.AlertType.INFORMATION);

                tableFlats.setItems(createListOfFlatDemands(getDemandTableContent(FLAT)));

                //обнуляем текстовые поля и выпадающие списки после добавления
                clearTextFields(listOfFlatDemandTextFields);
                flatClientComboBox.setValue(null);
                flatRealtorComboBox.setValue(null);

            } catch (SQLException e) {
                //открываем диалоговое окно для уведомления об ошибке
                showAlert(
                        "Ошибка добавления потребности в квартире",
                        "Потребность в квартире не была добавлена в базу. Повторите еще раз",
                        Alert.AlertType.ERROR);
            }


        } else {
            //открываем диалоговое окно для уведомления об ошибке
            showAlert(
                    "Ошибка добавления потребности в квартире",
                    "Поля: клиент, риэлтор, адрес, мин. цена и макс. цена являются обязательными для заполнения",
                    Alert.AlertType.ERROR);
        }
    }

    /**
     * Метод для обновления потребности в квартире
     */
    @FXML
    void updateFlatDemand() {
        if (flatClientComboBox.getValue() != null &&
                flatRealtorComboBox.getValue() != null &&
                !flatTextFieldAddress.getText().isEmpty() &&
                !flatTextFieldMinPrice.getText().isEmpty() &&
                !flatTextFieldMaxPrice.getText().isEmpty()) {

            //SQL запрос для обновления потребности в квартире
            String update = String.format("UPDATE %s SET %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=? WHERE %s=?;",
                    DEMANDS_TABLE,
                    CLIENT_COLUMN,
                    REALTOR_COLUMN,
                    ADDRESS_COLUMN,
                    MIN_PRICE_COLUMN,
                    MAX_PRICE_COLUMN,
                    MIN_SQUARE_COLUMN,
                    MAX_SQUARE_COLUMN,
                    MIN_COUNT_OF_ROOMS_COLUMN,
                    MAX_COUNT_OF_ROOMS_COLUMN,
                    MIN_FLOOR,
                    MAX_FLOOR,
                    ID_COLUMN);

            try {
                PreparedStatement updateFlatDemand = new DatabaseHandler().createDbConnection().prepareStatement(update);

                //установка значений для вставки в запрос
                updateFlatDemand.setString(1, flatClientComboBox.getValue().getLastName());
                updateFlatDemand.setString(2, flatRealtorComboBox.getValue().getLastName());
                updateFlatDemand.setString(3, flatTextFieldAddress.getText());
                updateFlatDemand.setInt(4, Integer.parseInt(flatTextFieldMinPrice.getText()));
                updateFlatDemand.setInt(5, Integer.parseInt(flatTextFieldMaxPrice.getText()));
                updateFlatDemand.setInt(6, Integer.parseInt(flatTextFieldMinSquare.getText()));
                updateFlatDemand.setInt(7, Integer.parseInt(flatTextFieldMaxSquare.getText()));
                updateFlatDemand.setInt(8, Integer.parseInt(flatTextFieldMinNumberOfRooms.getText()));
                updateFlatDemand.setInt(9, Integer.parseInt(flatTextFieldMaxNumberOfRooms.getText()));
                updateFlatDemand.setInt(10, Integer.parseInt(flatTextFieldMinFloor.getText()));
                updateFlatDemand.setInt(11, Integer.parseInt(flatTextFieldMaxFloor.getText()));
                updateFlatDemand.setInt(12, idSelectedFlatDemand);

                //выполнение SQL запроса
                updateFlatDemand.executeUpdate();

                //заполняем таблицу данным из БД
                tableFlats.setItems(createListOfFlatDemands(getDemandTableContent(FLAT)));

                //открываем диалоговое окно для уведомления об успешном обновлении
                showAlert(
                        "Операция успешно выполнена",
                        "Обновление потребности в квартире выполнено успешно!",
                        Alert.AlertType.INFORMATION);

                //обнуляем текстовые поля и выпадающие списки после обновления
                clearTextFields(listOfFlatDemandTextFields);
                flatClientComboBox.setValue(null);
                flatRealtorComboBox.setValue(null);

            } catch (SQLException e) {
                //открываем диалоговое окно для уведомления об ошибке
                showAlert(
                        "Ошибка обновления потребности в квартире",
                        "Обновление потребности в квартире не выполнено! Повторите еще раз",
                        Alert.AlertType.ERROR);
            }

        } else {
            //открываем диалоговое окно для уведомления об ошибке
            showAlert(
                    "Ошибка обновления потребности в квартире",
                    "Поля: клиент, риэлтор, адрес, мин. цена и макс. цена являются обязательными для заполнения",
                    Alert.AlertType.ERROR);
        }
    }

    /**
     * Метод для удаления потребности в квартире
     */
    @FXML
    void deleteFlatDemand() {
        //SQL запрос на удаление предложения
        String deleteFlatDemand = String.format("DELETE FROM %s WHERE %s=?", DEMANDS_TABLE, ID_COLUMN);

        try {
            PreparedStatement deleteOfferStatement = new DatabaseHandler().createDbConnection().prepareStatement(deleteFlatDemand);
            //установка значений для вставки в запрос
            deleteOfferStatement.setInt(1, idSelectedFlatDemand);
            //выполнение запроса на удаление
            deleteOfferStatement.executeUpdate();

            //открываем диалоговое окно для уведомления об успешном удалении
            showAlert(
                    "Операция успешно выполнена",
                    "Удаление потребности в квартире выполнено успешно!",
                    Alert.AlertType.INFORMATION);

            //обнуляем текстовые поля и выпадающие списки после удаления
            clearTextFields(listOfFlatDemandTextFields);
            flatClientComboBox.setValue(null);
            flatRealtorComboBox.setValue(null);

            tableFlats.setItems(createListOfFlatDemands(getDemandTableContent(FLAT)));

        } catch (SQLException e) {
            //открываем диалоговое окно для уведомления об ошибке
            showAlert(
                    "Ошибка удаления потребности в квартире",
                    String.format("Возникла ошибка при удалении потребности в квартире с ID = %d", idSelectedFlatDemand),
                    Alert.AlertType.ERROR
            );
        }
    }

    /**
     * Метод для создания потребности в земле
     */
    @FXML
    void createLandDemand() {
        if (landClientComboBox.getValue() != null &&
                landRealtorComboBox.getValue() != null &&
                !landTextFieldAddress.getText().isEmpty() &&
                !landTextFieldMinPrice.getText().isEmpty() &&
                !landTextFieldMaxPrice.getText().isEmpty()) {

            //создание объекта потребности в доме
            Demand landDemand = new Demand();
            landDemand.setClient(landClientComboBox.getValue().getLastName());
            landDemand.setRealtor(landRealtorComboBox.getValue().getLastName());
            landDemand.setTypeRealEstate(LAND);
            landDemand.setAddress(landTextFieldAddress.getText());
            landDemand.setMinPrice(Integer.parseInt(landTextFieldMinPrice.getText()));
            landDemand.setMaxPrice(Integer.parseInt(landTextFieldMaxPrice.getText()));

            //min square
            if (!landTextFieldMinSquare.getText().isEmpty()) {
                landDemand.setMinSquare(Integer.parseInt(landTextFieldMinSquare.getText()));
            }
            //max square
            if (!landTextFieldMaxSquare.getText().isEmpty()) {
                landDemand.setMaxSquare(Integer.parseInt(landTextFieldMaxSquare.getText()));
            }

            //SQL запрос для добавления потребности в доме
            String insertLandDemand = String.format("INSERT INTO %s(%s, %s, %s, %s, %s, %s, %s, %s) VALUES (?,?,?,?,?,?,?,?);",
                    DEMANDS_TABLE,
                    CLIENT_COLUMN,
                    REALTOR_COLUMN,
                    TYPE_REAL_ESTATE,
                    ADDRESS_COLUMN,
                    MIN_PRICE_COLUMN,
                    MAX_PRICE_COLUMN,
                    MIN_SQUARE_COLUMN,
                    MAX_SQUARE_COLUMN);

            try {
                PreparedStatement addLandStatement = new DatabaseHandler().createDbConnection().prepareStatement(insertLandDemand);

                //установка значений для вставки в запрос
                addLandStatement.setString(1, landDemand.getClient());
                addLandStatement.setString(2, landDemand.getRealtor());
                addLandStatement.setString(3, landDemand.getTypeRealEstate());
                addLandStatement.setString(4, landDemand.getAddress());
                addLandStatement.setInt(5, landDemand.getMinPrice());
                addLandStatement.setInt(6, landDemand.getMaxPrice());
                addLandStatement.setInt(7, landDemand.getMinSquare());
                addLandStatement.setInt(8, landDemand.getMaxSquare());

                //выполнение запроса
                addLandStatement.executeUpdate();

                //открываем диалоговое окно для уведомления об успешном добавлении
                showAlert(
                        "Операция успешно выполнена",
                        "Потребность в земле успешно добавлена!",
                        Alert.AlertType.INFORMATION);

                tableLands.setItems(createListOfLandDemands(getDemandTableContent(LAND)));

                //обнуляем текстовые поля и выпадающие списки после добавления
                clearTextFields(listOfLandDemandTextFields);
                landClientComboBox.setValue(null);
                landRealtorComboBox.setValue(null);

            } catch (SQLException e) {
                //открываем диалоговое окно для уведомления об ошибке
                showAlert(
                        "Ошибка добавления потребности в земле",
                        "Потребность в земле не была добавлена в базу. Повторите еще раз",
                        Alert.AlertType.ERROR);
            }


        } else {
            //открываем диалоговое окно для уведомления об ошибке
            showAlert(
                    "Ошибка добавления потребности в земле",
                    "Поля: клиент, риэлтор, адрес, мин. цена и макс. цена являются обязательными для заполнения",
                    Alert.AlertType.ERROR);
        }
    }

    /**
     * Метод для обновления потребности в земле
     */
    @FXML
    void updateLandDemand() {

        if (landClientComboBox.getValue() != null &&
                landRealtorComboBox.getValue() != null &&
                !landTextFieldAddress.getText().isEmpty() &&
                !landTextFieldMinPrice.getText().isEmpty() &&
                !landTextFieldMaxPrice.getText().isEmpty()) {

            //SQL запрос для обновления потребности в земле
            String update = String.format("UPDATE %s SET %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=? WHERE %s=?;",
                    DEMANDS_TABLE,
                    CLIENT_COLUMN,
                    REALTOR_COLUMN,
                    ADDRESS_COLUMN,
                    MIN_PRICE_COLUMN,
                    MAX_PRICE_COLUMN,
                    MIN_SQUARE_COLUMN,
                    MAX_SQUARE_COLUMN,
                    ID_COLUMN);

            try {
                PreparedStatement updateLandDemand = new DatabaseHandler().createDbConnection().prepareStatement(update);

                //установка значений для вставки в запрос
                updateLandDemand.setString(1, landClientComboBox.getValue().getLastName());
                updateLandDemand.setString(2, landRealtorComboBox.getValue().getLastName());
                updateLandDemand.setString(3, landTextFieldAddress.getText());
                updateLandDemand.setInt(4, Integer.parseInt(landTextFieldMinPrice.getText()));
                updateLandDemand.setInt(5, Integer.parseInt(landTextFieldMaxPrice.getText()));
                updateLandDemand.setInt(6, Integer.parseInt(landTextFieldMinSquare.getText()));
                updateLandDemand.setInt(7, Integer.parseInt(landTextFieldMaxSquare.getText()));
                updateLandDemand.setInt(8, idSelectedLandDemand);

                //выполнение SQL запроса
                updateLandDemand.executeUpdate();

                //заполняем таблицу данным из БД
                tableLands.setItems(createListOfLandDemands(getDemandTableContent(LAND)));

                //открываем диалоговое окно для уведомления об успешном обновлении
                showAlert(
                        "Операция успешно выполнена",
                        "Обновление потребности в земле выполнено успешно!",
                        Alert.AlertType.INFORMATION);

                //обнуляем текстовые поля и выпадающие списки после обновления
                clearTextFields(listOfLandDemandTextFields);
                landClientComboBox.setValue(null);
                landRealtorComboBox.setValue(null);

            } catch (SQLException e) {
                //открываем диалоговое окно для уведомления об ошибке
                showAlert(
                        "Ошибка обновления потребности в земле",
                        "Обновление потребности в земле не выполнено! Повторите еще раз",
                        Alert.AlertType.ERROR);
            }

        } else {
            //открываем диалоговое окно для уведомления об ошибке
            showAlert(
                    "Ошибка обновления потребности в земле",
                    "Поля: клиент, риэлтор, адрес, мин. цена и макс. цена являются обязательными для заполнения",
                    Alert.AlertType.ERROR);
        }
    }

    /**
     * Метод для удаления потребности в земле
     */
    @FXML
    void deleteLandDemand() {
        //SQL запрос на удаление предложения
        String deleteLandDemand = String.format("DELETE FROM %s WHERE %s=?", DEMANDS_TABLE, ID_COLUMN);

        try {
            PreparedStatement deleteOfferStatement = new DatabaseHandler().createDbConnection().prepareStatement(deleteLandDemand);
            //установка значений для вставки в запрос
            deleteOfferStatement.setInt(1, idSelectedLandDemand);
            //выполнение запроса на удаление
            deleteOfferStatement.executeUpdate();

            //открываем диалоговое окно для уведомления об успешном удалении
            showAlert(
                    "Операция успешно выполнена",
                    "Удаление потребности в земле выполнено успешно!",
                    Alert.AlertType.INFORMATION);

            //обнуляем текстовые поля и выпадающие списки после удаления
            clearTextFields(listOfLandDemandTextFields);
            landClientComboBox.setValue(null);
            landRealtorComboBox.setValue(null);

            tableLands.setItems(createListOfLandDemands(getDemandTableContent(LAND)));

        } catch (SQLException e) {
            //открываем диалоговое окно для уведомления об ошибке
            showAlert(
                    "Ошибка удаления потребности в земле",
                    String.format("Возникла ошибка при удалении потребности в земле с ID = %d", idSelectedLandDemand),
                    Alert.AlertType.ERROR
            );
        }
    }


    /**
     * Метод для формирования ObservableList текстовых полей на вкладке "Дом"
     *
     * @return ObservableList с текстовыми полями на вкладке "Дом"
     */
    private ObservableList<TextField> createListOfHomeTextFields() {
        ObservableList<TextField> listOfTextField = FXCollections.observableArrayList();
        listOfTextField.add(homeTextFieldAddress);
        listOfTextField.add(homeTextFieldMinPrice);
        listOfTextField.add(homeTextFieldMaxPrice);
        listOfTextField.add(homeTextFieldMinSquare);
        listOfTextField.add(homeTextFieldMaxSquare);
        listOfTextField.add(homeTextFieldMinNumberOfRooms);
        listOfTextField.add(homeTextFieldMaxNumberOfRooms);
        listOfTextField.add(homeTextFieldMinNumberOfFloors);
        listOfTextField.add(homeTextFieldMaxNumberOfFloors);
        return listOfTextField;
    }

    /**
     * Метод для формирования ObservableList текстовых на вкладке "Квартира"
     *
     * @return ObservableList с текстовыми полями на вкладке "Квартира"
     */
    private ObservableList<TextField> createListOfFlatTextFields() {
        ObservableList<TextField> listOfTextField = FXCollections.observableArrayList();
        listOfTextField.add(flatTextFieldAddress);
        listOfTextField.add(flatTextFieldMinPrice);
        listOfTextField.add(flatTextFieldMaxPrice);
        listOfTextField.add(flatTextFieldMinSquare);
        listOfTextField.add(flatTextFieldMaxSquare);
        listOfTextField.add(flatTextFieldMinNumberOfRooms);
        listOfTextField.add(flatTextFieldMaxNumberOfRooms);
        listOfTextField.add(flatTextFieldMinFloor);
        listOfTextField.add(flatTextFieldMaxFloor);
        return listOfTextField;
    }

    /**
     * Метод для формирования ObservableList текстовых на вкладке "Земля"
     *
     * @return ObservableList с текстовыми полями на вкладке "Земля"
     */
    private ObservableList<TextField> createListOfLandTextFields() {
        ObservableList<TextField> listOfTextField = FXCollections.observableArrayList();
        listOfTextField.add(landTextFieldAddress);
        listOfTextField.add(landTextFieldMinPrice);
        listOfTextField.add(landTextFieldMaxPrice);
        listOfTextField.add(landTextFieldMinSquare);
        listOfTextField.add(landTextFieldMaxSquare);
        return listOfTextField;
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
