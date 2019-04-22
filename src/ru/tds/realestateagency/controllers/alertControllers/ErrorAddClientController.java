package ru.tds.realestateagency.controllers.alertControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Класс-контроллер для обработки событий на уведомлении "Ошибка создания нового клиента"
 *
 * @author Трушенков Дмитрий
 */
public class ErrorAddClientController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label detailsLabel;

    @FXML
    private HBox actionParent;

    @FXML
    private Button okButton;

    @FXML
    private HBox okParent;

    @FXML
    private Label messageLabel;

    @FXML
    void initialize() {
        okButton.setOnAction(event -> okButton.getScene().getWindow().hide());
    }
}
