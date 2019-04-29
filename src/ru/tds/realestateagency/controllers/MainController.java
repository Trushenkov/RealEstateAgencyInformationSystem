package ru.tds.realestateagency.controllers;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import ru.tds.realestateagency.Helper;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController {

    @FXML
    private FontAwesomeIconView expandBtn;

    @FXML
    private FontAwesomeIconView compressBtn;

    @FXML
    private FontAwesomeIconView closeBtn;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private GridPane gridPaneButtons;

    @FXML
    private Button btnDeals;

    @FXML
    private FontAwesomeIcon btnClose11;

    @FXML
    private Button btnNeeds;

    @FXML
    private FontAwesomeIcon btnClose1;

    @FXML
    private Button btnHouses;

    @FXML
    private Button btnOffers;

    @FXML
    private Button btnClients;

    @FXML
    private Button btnRealtors;

    @FXML
    void handleButtonClicks(ActionEvent event) {
        ((Node) event.getSource()).getScene().getWindow().hide();
        if (event.getSource().equals(btnClients)) {
            Helper.changeScreen("/ru/tds/realestateagency/views/client.fxml");
        } else if (event.getSource().equals(btnRealtors)) {
            Helper.changeScreen("/ru/tds/realestateagency/views/realtor.fxml");
        }  else if (event.getSource().equals(btnHouses)) {
            Helper.changeScreen("/ru/tds/realestateagency/views/buildings2.fxml");
        }
    }

    @FXML
    void initialize() {

    }

    public void expand(MouseEvent mouseEvent) {
        ((Node) mouseEvent.getSource()).getScene().getWindow().setY(0);
        ((Node) mouseEvent.getSource()).getScene().getWindow().setX(0);

        ((Node) mouseEvent.getSource()).getScene().getWindow().setWidth(Screen.getPrimary().getVisualBounds().getMaxX());
        ((Node) mouseEvent.getSource()).getScene().getWindow().setHeight(Screen.getPrimary().getVisualBounds().getMaxY());
    }

//    public void compress(MouseEvent mouseEvent) {
//
//    }
//
//    public void close(MouseEvent mouseEvent) {
//
//    }
}
