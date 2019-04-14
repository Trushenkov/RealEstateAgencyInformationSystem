package ru.tds.realestateagency.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import ru.tds.realestateagency.DatabaseHandler;
import ru.tds.realestateagency.Helper;
import ru.tds.realestateagency.entities.Realtor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Predicate;

/**
 * Класс-контроллер для обработки событий на экране "Работа с риэлторами"
 *
 * @author Трушенков Дмитрий
 */
public class RealtorsScreenController {

    private static final String REALTOR_TABLE = "realtors";
    private static final String REALTOR_ID = "id";
    private static final String REALTOR_LAST_NAME = "lastName";
    private static final String REALTOR_FIRST_NAME = "firstName";
    private static final String REALTOR_MIDDLE_NAME = "middleName";
    private static final String REALTOR_COMMISSION_PART = "commissionPart";

    @FXML
    private Button createBtn;
    @FXML
    private Button updateBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private Button goBackBtn;
    @FXML
    private TableView<Realtor> tableRealtors;
    @FXML
    private TableColumn<Realtor, String> tableColumnLastName;
    @FXML
    private TableColumn<Realtor, String> tableColumnFirstName;
    @FXML
    private TableColumn<Realtor, String> tableColumnMiddleName;
    @FXML
    private TableColumn<Realtor, Integer> tableColumnСommissionPart;
    @FXML
    private TextField tfCommissionPart;
    @FXML
    private TextField tfLastName;
    @FXML
    private TextField tfMiddleName;
    @FXML
    private TextField tfSearch;
    @FXML
    private TextField tfFirstName;
    private int idRealtor;

    @FXML
    void initialize() throws SQLException {
        tableColumnLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        tableColumnFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        tableColumnMiddleName.setCellValueFactory(new PropertyValueFactory<>("middleName"));
        tableColumnСommissionPart.setCellValueFactory(new PropertyValueFactory<>("commissionPart"));

        try {
            tableRealtors.setItems(createListRealtors(getRealtorsTableContent()));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //При клике на элемент данные выделенного объекта заносятся в текстовые поля
        tableRealtors.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                DatabaseHandler handler = new DatabaseHandler();
                PreparedStatement statement = null;
                try {
                    statement = handler.createDbConnection().prepareStatement("SELECT " + REALTOR_ID + " FROM " + REALTOR_TABLE + " WHERE "
                            + REALTOR_LAST_NAME + " = ? AND "
                            + REALTOR_FIRST_NAME + " = ? AND "
                            + REALTOR_MIDDLE_NAME + "=?");

                    statement.setString(1, tableRealtors.getSelectionModel().getSelectedItem().getLastName());
                    statement.setString(2, tableRealtors.getSelectionModel().getSelectedItem().getFirstName());
                    statement.setString(3, tableRealtors.getSelectionModel().getSelectedItem().getMiddleName());
                    ResultSet resultSet = statement.executeQuery();
                    while (resultSet.next()) {
                        idRealtor = resultSet.getInt("id");
                        System.out.println("Id realtor=" + idRealtor);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                tfLastName.setText(tableRealtors.getSelectionModel().getSelectedItem().getLastName());
                tfFirstName.setText(tableRealtors.getSelectionModel().getSelectedItem().getFirstName());
                tfMiddleName.setText(tableRealtors.getSelectionModel().getSelectedItem().getMiddleName());
                tfCommissionPart.setText(String.valueOf(tableRealtors.getSelectionModel().getSelectedItem().getCommissionPart()));
            }
        });

        FilteredList<Realtor> filteredList = new FilteredList<>(createListRealtors(getRealtorsTableContent()));
        tfSearch.textProperty().addListener((observable, oldValue, newValue) -> {

            filteredList.setPredicate((Predicate<? super Realtor>) realtor -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseNewValue = newValue.toLowerCase();

                try {
                    ObservableList<Realtor> list = createListRealtors(getRealtorsTableContent());
                    for (int i = 0; i < list.size(); i++) {
                        if (Helper.levenstain(lowerCaseNewValue, realtor.getLastName().toLowerCase()) <= 3){
                            return true;
                        }
                        if (Helper.levenstain(lowerCaseNewValue, realtor.getFirstName().toLowerCase()) <= 3){
                            return true;
                        }
                        if (Helper.levenstain(lowerCaseNewValue, realtor.getMiddleName().toLowerCase()) <= 3){
                            return true;
                        }
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }




                return false;
            });
        });

        SortedList<Realtor> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tableRealtors.comparatorProperty());
        tableRealtors.setItems(sortedList);
    }


    /**
     * Метод для обработки события при нажатии на кнопку "Создать"
     *
     * @param actionEvent нажатие на кнопку
     */
    public void createBtnClicked(ActionEvent actionEvent) {
        Realtor realtor = new Realtor();

        if (!tfLastName.getText().isEmpty() && !tfFirstName.getText().isEmpty() && !tfMiddleName.getText().isEmpty()) {
            realtor.setLastName(tfLastName.getText());
            realtor.setFirstName(tfFirstName.getText());
            realtor.setMiddleName(tfMiddleName.getText());
            realtor.setCommissionPart(Integer.parseInt(tfCommissionPart.getText()));

            System.out.println(realtor);

            String insertRealtor = "INSERT INTO " +
                    REALTOR_TABLE + "(" +
                    REALTOR_LAST_NAME + ", " +
                    REALTOR_FIRST_NAME + ", " +
                    REALTOR_MIDDLE_NAME + ", " +
                    REALTOR_COMMISSION_PART + ")" +
                    " VALUES (?,?,?,?);";

            DatabaseHandler handler = new DatabaseHandler();

            try {
                PreparedStatement addRealtorStatement = handler.createDbConnection().prepareStatement(insertRealtor);
                addRealtorStatement.setString(1, realtor.getLastName());
                addRealtorStatement.setString(2, realtor.getFirstName());
                addRealtorStatement.setString(3, realtor.getMiddleName());
                addRealtorStatement.setInt(4, realtor.getCommissionPart());
                addRealtorStatement.executeUpdate();

                tableRealtors.setItems(createListRealtors(getRealtorsTableContent()));

                System.out.println(createListRealtors(getRealtorsTableContent()).size());
            } catch (SQLException e) {
                System.err.println("Ошибка создания addRealtorStatement");
            }

            clearTextFields();

        } else {
            System.err.println("Поля: фамилия, имя, отчество обязательны к заполнению.");
        }
    }

    /**
     * Метод для обработки события при нажатии на кнопку "Обновить"
     *
     * @param actionEvent нажатие на кнопку
     */
    public void updateBtnClicked(ActionEvent actionEvent) {
        String update = "UPDATE " + REALTOR_TABLE + " SET "
                + REALTOR_LAST_NAME + "=?,"
                + REALTOR_FIRST_NAME + "=?,"
                + REALTOR_MIDDLE_NAME + "=?,"
                + REALTOR_COMMISSION_PART + "=?" + " WHERE " + REALTOR_ID + "=?";

        DatabaseHandler handler = new DatabaseHandler();
        try {
            PreparedStatement preparedStatement = handler.createDbConnection().prepareStatement(update);
            preparedStatement.setString(1, tfLastName.getText());
            preparedStatement.setString(2, tfFirstName.getText());
            preparedStatement.setString(3, tfMiddleName.getText());
            preparedStatement.setInt(4, Integer.parseInt(tfCommissionPart.getText()));
            preparedStatement.setInt(5, idRealtor);
            preparedStatement.executeUpdate();

            tableRealtors.setItems(createListRealtors(getRealtorsTableContent()));
        } catch (SQLException e) {
            e.printStackTrace();
        }

//        System.out.println("Updated realtor with lastName = " +
//                tableRealtors.getSelectionModel().getSelectedItem().getLastName() +
//                ", firstName =" +
//                tableRealtors.getSelectionModel().getSelectedItem().getFirstName() +
//                ", middleName = " +
//                tableRealtors.getSelectionModel().getSelectedItem().getMiddleName());

        clearTextFields();
    }

    /**
     * Метод для обнуления текстовых полей
     */
    private void clearTextFields() {
        tfLastName.setText("");
        tfFirstName.setText("");
        tfMiddleName.setText("");
        tfCommissionPart.setText("");
    }

    /**
     * Метод для обработки события при нажатии на кнопку "Удалить"
     *
     * @param actionEvent нажатие на кнопку
     */
    public void deleteBtnClicked(ActionEvent actionEvent) {
        String deleteClient = "DELETE FROM " + REALTOR_TABLE + " WHERE " + REALTOR_ID + "=?";

        DatabaseHandler handler = new DatabaseHandler();

        try {
            PreparedStatement preparedStatement = handler.createDbConnection().prepareStatement(deleteClient);
            preparedStatement.setInt(1, idRealtor);
            preparedStatement.executeUpdate();

            tableRealtors.setItems(createListRealtors(getRealtorsTableContent()));
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Ошибка удаления риэтора с ID = " + idRealtor + "из базы данных.");
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
    private ResultSet getRealtorsTableContent() {

        String selectRealtors = "SELECT * FROM " + REALTOR_TABLE;

        ResultSet resultSet = null;
        try {
            DatabaseHandler dbhandler = new DatabaseHandler();

            PreparedStatement ps = dbhandler.createDbConnection().prepareStatement(selectRealtors);
            resultSet = ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Ошибка получения данных из таблицы риэлторов");
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
    private ObservableList<Realtor> createListRealtors(ResultSet resultSet) throws SQLException {
        ObservableList<Realtor> list = FXCollections.observableArrayList();
        while (resultSet.next()) {
            Realtor realtor = new Realtor(
                    resultSet.getString("lastName"),
                    resultSet.getString("firstName"),
                    resultSet.getString("middleName"),
                    resultSet.getInt("commissionPart"
                    ));
            list.add(realtor);
        }
        return list;
    }
}
