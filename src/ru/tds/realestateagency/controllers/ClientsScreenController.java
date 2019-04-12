package ru.tds.realestateagency.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import ru.tds.realestateagency.DatabaseHandler;
import ru.tds.realestateagency.Helper;
import ru.tds.realestateagency.entities.Client;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Класс-контроллер для обработки событий на экране "Работа с клиентами"
 *
 * @author Трушенков Дмитрий
 */
public class ClientsScreenController {

    private static final String CLIENT_TABLE = "clients";
    private static final String CLIENT_LAST_NAME = "lastName";
    private static final String CLIENT_FIRST_NAME = "firstName";
    private static final String CLIENT_MIDDLE_NAME = "middleName";
    private static final String CLIENT_PHONE_NUMBER = "phoneNumber";
    private static final String CLIENT_EMAIL = "email";

    @FXML
    private TableView<Client> tableClients;
    @FXML
    private TableColumn<Client, String> tableColumnLastName;
    @FXML
    private TableColumn<Client, String> tableColumnFirstName;
    @FXML
    private TableColumn<Client, String> tableColumnMiddleName;
    @FXML
    private TableColumn<Client, Integer> tableColumnPhoneNumber;
    @FXML
    private TableColumn<Client, String> tableColumnEmail;
    @FXML
    private TextField tfSearch;
    @FXML
    private TextField tfLastName;
    @FXML
    private TextField tfFirstName;
    @FXML
    private TextField tfMiddleName;
    @FXML
    private TextField tfPhoneNumber;
    @FXML
    private TextField tfEmail;

    @FXML
    void initialize() {
        tableColumnLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        tableColumnFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        tableColumnMiddleName.setCellValueFactory(new PropertyValueFactory<>("middleName"));
        tableColumnPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        tableColumnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        try {
            tableClients.setItems(createListClients(getClientsTableContent()));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Метод для обработки события при нажатии на кнопку "Создать"
     *
     * @param actionEvent нажатие на кнопку
     */
    public void createBtnClicked(ActionEvent actionEvent) {
        Client client = new Client();

        if (!tfPhoneNumber.getText().isEmpty() || !tfEmail.getText().isEmpty()) {
            client.setLastName(tfLastName.getText());
            client.setFirstName(tfFirstName.getText());
            client.setMiddleName(tfMiddleName.getText());
            client.setEmail(tfEmail.getText());

            if (!tfPhoneNumber.getText().isEmpty()) {
                client.setPhoneNumber(Integer.parseInt(tfPhoneNumber.getText()));
            }

            System.out.println(client);

            String insertClient = "INSERT INTO " +
                    CLIENT_TABLE + "(" +
                    CLIENT_LAST_NAME + ", " +
                    CLIENT_FIRST_NAME + ", " +
                    CLIENT_MIDDLE_NAME + ", " +
                    CLIENT_PHONE_NUMBER + ", " +
                    CLIENT_EMAIL + ")" + " VALUES (?,?,?,?,?);";


            try {
                PreparedStatement addClientStatement = new DatabaseHandler().createDbConnection().prepareStatement(insertClient);
                addClientStatement.setString(1, client.getLastName());
                addClientStatement.setString(2, client.getFirstName());
                addClientStatement.setString(3, client.getMiddleName());
                addClientStatement.setInt(4, client.getPhoneNumber());
                addClientStatement.setString(5, client.getEmail());
                addClientStatement.executeUpdate();

                tableClients.setItems(createListClients(getClientsTableContent()));

                System.out.println(createListClients(getClientsTableContent()).size());
            } catch (SQLException e) {
                System.err.println("Ошибка создания addClientStatement");
            }
        } else {
            System.err.println("Одно из полей:  номер телефона или электронная почта должно быть указано");
        }

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

    /**
     * Метод для получения содержимого таблицы клиентов из базы данных
     *
     * @return ResultSet - набор данных из таблицы
     */
    private ResultSet getClientsTableContent() {

        String selectClients = "SELECT * FROM " + CLIENT_TABLE;

        ResultSet resultSet = null;
        try {
            DatabaseHandler dbhandler = new DatabaseHandler();

            PreparedStatement ps = dbhandler.createDbConnection().prepareStatement(selectClients);
            resultSet = ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Ошибка получения данных из таблицы клиентов");
        }

        return resultSet;
    }

    /**
     * Метод для формирования ObservableList из набора данных
     *
     * @param resultSet набора данных из таблицы клиентов
     * @return ObservableList с объектами типа Client
     * @throws SQLException
     */
    private ObservableList<Client> createListClients(ResultSet resultSet) throws SQLException {
        ObservableList<Client> list = FXCollections.observableArrayList();
        while (resultSet.next()) {
            Client client = new Client(
                    resultSet.getString("lastName"),
                    resultSet.getString("firstName"),
                    resultSet.getString("middleName"),
                    resultSet.getInt("phoneNumber"),
                    resultSet.getString("email")
            );
            list.add(client);
        }
        return list;
    }
}
