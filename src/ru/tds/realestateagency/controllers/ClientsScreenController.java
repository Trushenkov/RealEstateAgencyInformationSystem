package ru.tds.realestateagency.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import ru.tds.realestateagency.entities.Realtor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Predicate;

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
    private static final String CLIENT_ID = "id";

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
    private int idClient;

    @FXML
    void initialize() throws SQLException {
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

        //При клике на элемент данные выделенного объекта заносятся в текстовые поля
        tableClients.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                DatabaseHandler handler = new DatabaseHandler();
                PreparedStatement statement = null;
                try {
                    statement = handler.createDbConnection().prepareStatement("SELECT " + CLIENT_ID + " FROM " + CLIENT_TABLE + " WHERE " + CLIENT_PHONE_NUMBER + " = ? AND " + CLIENT_EMAIL + " = ?");
                    statement.setString(1, tableClients.getSelectionModel().getSelectedItem().getPhoneNumber());
                    statement.setString(2, tableClients.getSelectionModel().getSelectedItem().getEmail());
                    ResultSet resultSet = statement.executeQuery();
                    while (resultSet.next()) {
                        idClient = resultSet.getInt("id");
                        System.out.println("Id client=" + idClient);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                tfLastName.setText(tableClients.getSelectionModel().getSelectedItem().getLastName());
                tfFirstName.setText(tableClients.getSelectionModel().getSelectedItem().getFirstName());
                tfMiddleName.setText(tableClients.getSelectionModel().getSelectedItem().getMiddleName());
                tfPhoneNumber.setText(String.valueOf(tableClients.getSelectionModel().getSelectedItem().getPhoneNumber()));
                tfEmail.setText(String.valueOf(tableClients.getSelectionModel().getSelectedItem().getEmail()));
            }
        });

        FilteredList<Client> filteredList = new FilteredList<>(createListClients(getClientsTableContent()));
        tfSearch.textProperty().addListener((observable, oldValue, newValue) -> {

            filteredList.setPredicate((Predicate<? super Client>) client -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseNewValue = newValue.toLowerCase();

                try {
                    ObservableList<Client> list = createListClients(getClientsTableContent());
                    for (int i = 0; i < list.size(); i++) {
                        if (Helper.levenstain(lowerCaseNewValue, client.getLastName().toLowerCase()) <= 3){
                            return true;
                        }
                        if (Helper.levenstain(lowerCaseNewValue, client.getFirstName().toLowerCase()) <= 3){
                            return true;
                        }
                        if (Helper.levenstain(lowerCaseNewValue, client.getMiddleName().toLowerCase()) <= 3){
                            return true;
                        }
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }




                return false;
            });
        });

        SortedList<Client> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tableClients.comparatorProperty());
        tableClients.setItems(sortedList);

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
                client.setPhoneNumber(tfPhoneNumber.getText());
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
                addClientStatement.setString(4, client.getPhoneNumber());
                addClientStatement.setString(5, client.getEmail());
                addClientStatement.executeUpdate();

                tableClients.setItems(createListClients(getClientsTableContent()));

                System.out.println(createListClients(getClientsTableContent()).size());
            } catch (SQLException e) {
                System.err.println("Ошибка создания addClientStatement");
            }

            clearTextFields();
        } else {
            System.err.println("Одно из полей:  номер телефона или электронная почта должно быть указано");
        }

    }

    /**
     * Метод для обнуления текстовых полей
     */
    private void clearTextFields() {
        tfLastName.setText("");
        tfFirstName.setText("");
        tfMiddleName.setText("");
        tfPhoneNumber.setText("");
        tfEmail.setText("");
    }

    /**
     * Метод для обработки события при нажатии на кнопку "Обновить"
     *
     * @param actionEvent нажатие на кнопку
     */
    public void updateBtnClicked(ActionEvent actionEvent) {
        String update = "UPDATE " + CLIENT_TABLE + " SET " + CLIENT_LAST_NAME + "=?,"
                + CLIENT_FIRST_NAME + "=?,"
                + CLIENT_MIDDLE_NAME + "=?,"
                + CLIENT_PHONE_NUMBER + "=?,"
                + CLIENT_EMAIL + "=? " +
                "WHERE " + CLIENT_ID + "=?";

        DatabaseHandler handler = new DatabaseHandler();
        try {
            PreparedStatement preparedStatement = handler.createDbConnection().prepareStatement(update);
            preparedStatement.setString(1, tfLastName.getText());
            preparedStatement.setString(2, tfFirstName.getText());
            preparedStatement.setString(3, tfMiddleName.getText());
            preparedStatement.setString(4, tfPhoneNumber.getText());
            preparedStatement.setString(5, tfEmail.getText());
            preparedStatement.setInt(6, idClient);
            preparedStatement.executeUpdate();

            tableClients.setItems(createListClients(getClientsTableContent()));
        } catch (SQLException e) {
            e.printStackTrace();
        }

//        System.out.println("Updated client with phoneNumber=" +
//                tableClients.getSelectionModel().getSelectedItem().getPhoneNumber() +
//                " and email=" +
//                tableClients.getSelectionModel().getSelectedItem().getEmail());

        clearTextFields();
    }

    /**
     * Метод для обработки события при нажатии на кнопку "Удалить"
     *
     * @param actionEvent нажатие на кнопку
     */
    public void deleteBtnClicked(ActionEvent actionEvent) {
        String deleteClient = "DELETE FROM " + CLIENT_TABLE + " WHERE " + CLIENT_ID + "=?";

        DatabaseHandler handler = new DatabaseHandler();

        try {
            PreparedStatement preparedStatement = handler.createDbConnection().prepareStatement(deleteClient);
            preparedStatement.setInt(1, idClient);
            preparedStatement.executeUpdate();

            tableClients.setItems(createListClients(getClientsTableContent()));
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Ошибка удаления клиента с ID = " + idClient + "из базы данных.");
        }
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
                    resultSet.getString("phoneNumber"),
                    resultSet.getString("email")
            );
            list.add(client);
        }
        return list;
    }
}
