package ru.tds.realestateagency.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import ru.tds.realestateagency.Helper;


public class RealEstateController {

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
    private TableView<?> tableHomes;
    @FXML
    private TableColumn<?, ?> homeTableColumnLatitude;
    @FXML
    private TableColumn<?, ?> homeTableColumnLongitude;
    @FXML
    private TableColumn<?, ?> homeTableColumnSquare;
    @FXML
    private TableColumn<?, ?> homeTableColumnFlatNumber;
    @FXML
    private TableColumn<?, ?> homeTableColumnHomeNumber;
    @FXML
    private TableColumn<?, ?> homeTableColumnNumberOfRooms;
    @FXML
    private TableColumn<?, ?> homeTableColumnStreet;
    @FXML
    private TableColumn<?, ?> homeTableColumnFloor;
    @FXML
    private TableColumn<?, ?> homeTableColumnCity;
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


    @FXML
    void goBackBtnClicked(ActionEvent event) {
        ((Node) event.getSource()).getScene().getWindow().hide();
        //переход на главный экран
        Helper.changeScreen("/ru/tds/realestateagency/views/main.fxml");
    }

//    @FXML
//    void createBtnClicked(ActionEvent event) {
//
//    }
//
//    @FXML
//    void updateBtnClicked(ActionEvent event) {
//
//    }
//
//    @FXML
//    void deleteBtnClicked(ActionEvent event) {
//
//    }

    @FXML
    void createBtnClicked(ActionEvent event) {

    }

    @FXML
    public void updateBtnClicked(ActionEvent event) {

    }

    @FXML
    void deleteBtnClicked(ActionEvent event) {

    }


    @FXML
    public void initialize() {
        showHomesButton.setStyle("-fx-background-color: #0d47a1");
        headerTypeBuildingLabel.setText("Главная > Объекты недвижимости > Дом");
        homesAnchorPane.toBack();
        landsAnchorPane.toFront();
        flatsAnchorPane.toBack();
        stackPane.setBackground(new Background(new BackgroundFill(Color.rgb(255, 255, 255), CornerRadii.EMPTY, Insets.EMPTY)));
    }

    /**
     * Метод для смены вкладок внутри экрана при нажатии на соответствующую кнопку
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
     * @param actionEvent нажатие на кнопку "Создать"
     */
    public void createHome(ActionEvent actionEvent) {

    }

    /**
     * Метод для обновления объекта недвижимости типа "Дом" в базе данных
     *
     * @param actionEvent нажатие на кнопку "Обновить"
     */
    public void updateHome(ActionEvent actionEvent) {

    }

    /**
     * Метод для удаления объекта недвижимости типа "Дом" из базы данных
     *
     * @param actionEvent нажатие на кнопку "Удалить"
     */
    public void deleteHome(ActionEvent actionEvent) {

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


}
