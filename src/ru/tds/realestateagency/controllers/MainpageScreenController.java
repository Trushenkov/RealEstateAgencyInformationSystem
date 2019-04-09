package ru.tds.realestateagency.controllers;

import javafx.fxml.FXML;
import ru.tds.realestateagency.DatabaseHandler;

import java.sql.Connection;
import java.sql.SQLException;

public class MainpageScreenController {

    @FXML
    void initialize() {
        DatabaseHandler dbHandler = new DatabaseHandler();
        Connection connection = dbHandler.createDbConnection();
        try {
            if (connection.isClosed()) {
                System.out.println("Connection is closed.");
            } else {
                System.err.println("Connection is opened.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
