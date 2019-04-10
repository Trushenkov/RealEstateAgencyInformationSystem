package ru.tds.realestateagency.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import ru.tds.realestateagency.Helper;

/**
 * Класс-контроллер для обработки событий на главном экране
 *
 * @author Трушенков Дмитрий
 */
public class MainpageScreenController {

    @FXML
    private Button clientsBtn;

    @FXML
    private Button realtorsBtn;

    @FXML
    void initialize() {
        clientsBtn.setOnAction(event -> {
            clientsBtn.getScene().getWindow().hide();
            new Helper().changeScreen("/ru/tds/realestateagency/views/clientsScreen.fxml");
        });

        realtorsBtn.setOnAction(event -> {
            realtorsBtn.getScene().getWindow().hide();
            new Helper().changeScreen("ru/tds/realestateagency/views/realtorsScreen.fxml");
        });
    }


}
