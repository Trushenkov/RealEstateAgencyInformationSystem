package ru.tds.realestateagency;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Класс, содержащий методы для работы с базой данных
 *
 * @author Трушенков Дмитрий
 */
public class DatabaseHandler {

    /**
     * Класс для хранения констант, необходимых для работы с базой данных
     *
     * @author Трушенков Дмитрий
     */
    class Const {
        static final String DB_HOST = "192.168.3.171";
        static final int DB_PORT = 3306;
        static final String DB_NAME = "realestateagency";
        static final String DB_USER = "test";
        static final String DB_PASSWORD = "w4r=ktp>qrqG";
    }

    private Connection connection;

    /**
     * Метод для создания соединения с базой данных
     *
     * @return соединение с базой данных
     */
    public Connection createDbConnection() {
        String connectionUrl = "jdbc:mysql://" + Const.DB_HOST + ":" + Const.DB_PORT + "/" + Const.DB_NAME + "?serverTimezone=UTC";
        try {
            connection = DriverManager.getConnection(connectionUrl, Const.DB_USER, Const.DB_PASSWORD);
        } catch (SQLException e) {
            System.err.println("Ошибка при создании соединения с базой данных.");
        }
        return connection;
    }



}
