package ru.tds.realestateagency.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import ru.tds.realestateagency.Helper;

public class MainController {


    @FXML
    private Button btnDeals;

    @FXML
    private Button btnNeeds;

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
            Helper.changeScreen("/ru/tds/realestateagency/views/realEstate.fxml");
        }
    }

    @FXML
    void initialize() {

    }
}
