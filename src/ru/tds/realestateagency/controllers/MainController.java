package ru.tds.realestateagency.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import ru.tds.realestateagency.Helper;

/**
 * Класс-контроллер для обработки событий на главном экране
 *
 * @author Трушенков Дмитрий
 */
public class MainController {

    @FXML
    private Button dealsButton;

    @FXML
    private Button demandsButton;

    @FXML
    private Button realEstatesButton;

    @FXML
    private Button offersButton;

    @FXML
    private Button clientsButton;

    @FXML
    private Button realtorsButton;

    @FXML
    void handleButtonClicks(ActionEvent event) {
        ((Node) event.getSource()).getScene().getWindow().hide();

        if (event.getSource().equals(clientsButton)) {
            Helper.openNewScreen("/ru/tds/realestateagency/views/client.fxml");
        } if (event.getSource().equals(realtorsButton)) {
            Helper.openNewScreen("/ru/tds/realestateagency/views/realtor.fxml");
        } else if (event.getSource().equals(realEstatesButton)) {
            Helper.openNewScreen("/ru/tds/realestateagency/views/realEstate.fxml");
        } else if (event.getSource().equals(offersButton)) {
            Helper.openNewScreen("/ru/tds/realestateagency/views/offers.fxml");
        } else if (event.getSource().equals(demandsButton)) {
            Helper.openNewScreen("/ru/tds/realestateagency/views/demands.fxml");
        } else if (event.getSource().equals(dealsButton)) {
            Helper.openNewScreen("/ru/tds/realestateagency/views/deals.fxml");
        }
    }

    @FXML
    void initialize() {

    }
}
