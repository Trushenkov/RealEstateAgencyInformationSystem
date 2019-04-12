package ru.tds.realestateagency.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import ru.tds.realestateagency.Helper;

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

    /**
     * Метод для обработки события при нажатии на кнопку "Создать"
     *
     * @param actionEvent нажатие на кнопку
     */
    public void createBtnClicked(ActionEvent actionEvent) {

    }

    /**
     * Метод для обработки события при нажатии на кнопку "Обновить"
     *
     * @param actionEvent нажатие на кнопку
     */
    public void updateBtnClicked(ActionEvent actionEvent) {

    }

    /**
     * Метод для обработки события при нажатии на кнопку "Удалить"
     *
     * @param actionEvent нажатие на кнопку
     */
    public void deleteBtnClicked(ActionEvent actionEvent) {

    }

    /**
     * Метод для обработки события при нажатии на кнопку "Назад"
     *
     * @param actionEvent нажатие на кнопку
     */
    public void goBackBtnClicked(ActionEvent actionEvent) {
        ((Node) actionEvent.getSource()).getScene().getWindow().hide();
        new Helper().changeScreen("/ru/tds/realestateagency/views/mainpageScreen.fxml");
    }
}
