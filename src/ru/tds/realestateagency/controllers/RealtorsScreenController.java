package ru.tds.realestateagency.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * Класс-контроллер для обработки событий на экране "Работа с риэлторами"
 *
 * @author Трушенков Дмитрий
 */
public class RealtorsScreenController {

    @FXML
    private Button createBtn;

    @FXML
    private Button updateBtn;

    @FXML
    private Button deleteBtn;

    @FXML
    private Button goBackBtn;

    @FXML
    private TableView tableRealtors;

    @FXML
    private TableColumn<?, ?> tableColumnLastName;

    @FXML
    private TableColumn<?, ?> tableColumnFirstName;

    @FXML
    private TableColumn<?, ?> tableColumnMiddleName;

    @FXML
    private TableColumn<?, ?> tableColumnСommissionPart;

    @FXML
    private TextField tfСommissionPart;

    @FXML
    private TextField tfLastName;

    @FXML
    private TextField tfMiddleName;

    @FXML
    private TextField tfSearch;

    @FXML
    private TextField tfFirstName;

    @FXML
    void initialize() {

    }

    public void createBtnClicked(ActionEvent actionEvent) {

    }

    public void updateBtnClicked(ActionEvent actionEvent) {

    }

    public void deleteBtnClicked(ActionEvent actionEvent) {

    }

    public void goBackBtnClicked(ActionEvent actionEvent) {

    }
}
