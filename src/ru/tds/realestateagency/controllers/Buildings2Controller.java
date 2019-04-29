package ru.tds.realestateagency.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import ru.tds.realestateagency.Helper;

import java.net.URL;
import java.util.ResourceBundle;


public class Buildings2Controller {

    public Button btnAllBuildings;
    public Button btnHouse;
    public Button btnEarth;
    public Button btnFlat;
    public StackPane stackPane;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField tfFlatNumber;

    @FXML
    private Button updateBtn;

    @FXML
    private TableColumn<?, ?> tableColumnFlatNumber1;

    @FXML
    private TableColumn<?, ?> tableColumnStreet1;

    @FXML
    private Button deleteBtn1;

    @FXML
    private TableView<?> tableAllBuildings;

    @FXML
    private TextField tfFirstName;

    @FXML
    private TableColumn<?, ?> tableColumnSquare;

    @FXML
    private TableColumn<?, ?> tableColumnLatitude1;

    @FXML
    private TextField tfCity;

    @FXML
    private TableColumn<?, ?> tableColumnHomeNumber1;

    @FXML
    private TextField tfFloor;

    @FXML
    private TableColumn<?, ?> tableColumnStreet;

    @FXML
    private TextField tfSearch;

    @FXML
    private TableColumn<?, ?> tableColumnLongitude;

    @FXML
    private TableColumn<?, ?> tableColumnCity;

    @FXML
    private TableColumn<?, ?> tableColumnCity1;

    @FXML
    private TextField tfLongitude;

    @FXML
    private Button goBackBtn;

    @FXML
    private TableColumn<?, ?> tableColumnNumberOfRooms;

    @FXML
    private TextField tfLastName1;

    @FXML
    private TableView<?> tableClients1;

    @FXML
    private Button createBtn1;

    @FXML
    private AnchorPane housePane;

    @FXML
    private Button createBtn;

    @FXML
    private TableColumn<?, ?> tableColumnFlatNumber;

    @FXML
    private TextField tfFirstName1;

    @FXML
    private Button updateBtn1;

    @FXML
    private TextField tfStreet;

    @FXML
    private TableColumn<?, ?> tableColumnFloor;

    @FXML
    private Button deleteBtn;

    @FXML
    private TextField tfLatitude;

    @FXML
    private TextField tfPhoneNumber;

    @FXML
    private TableColumn<?, ?> tableColumnHomeNumber;

    @FXML
    private TableColumn<?, ?> tableColumnLatitude;

    @FXML
    private TextField tfLastName;

    @FXML
    private TextField tfMiddleName;

    @FXML
    private TextField tfNumberOfRooms;

    @FXML
    private TextField tfSquare;

    @FXML
    private AnchorPane listAllBuildingsPane;

    @FXML
    private TableColumn<?, ?> tableColumnLongitude1;

    @FXML
    private TextField tfHomeNumber;

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
    void updateBtnClicked(ActionEvent event) {

    }

    @FXML
    void deleteBtnClicked(ActionEvent event) {

    }


    @FXML
    void initialize() {
        btnAllBuildings.setStyle("-fx-background-color: #0d47a1");
        listAllBuildingsPane.toFront();
        housePane.toBack();
        stackPane.setBackground(new Background(new BackgroundFill(Color.rgb(255,255,255), CornerRadii.EMPTY, Insets.EMPTY)));
    }

    public void switchContentScreen(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(btnAllBuildings)) {
            btnHouse.setStyle("");
            btnEarth.setStyle("");
            btnFlat.setStyle("");
            btnAllBuildings.setStyle("-fx-background-color: #0d47a1");
            listAllBuildingsPane.toFront();
        } else if (actionEvent.getSource().equals(btnHouse)){
            btnAllBuildings.setStyle("");
            btnEarth.setStyle("");
            btnFlat.setStyle("");
            btnHouse.setStyle("-fx-background-color: #0d47a1");
            housePane.toFront();
        } else if (actionEvent.getSource().equals(btnFlat)){
            btnAllBuildings.setStyle("");
            btnEarth.setStyle("");
            btnHouse.setStyle("");
            btnFlat.setStyle("-fx-background-color: #0d47a1");
//            housePane.toFront();
        }else if (actionEvent.getSource().equals(btnEarth)){
            btnAllBuildings.setStyle("");
            btnHouse.setStyle("");
            btnFlat.setStyle("");
            btnEarth.setStyle("-fx-background-color: #0d47a1");
//            housePane.toFront();
        }
    }
}
