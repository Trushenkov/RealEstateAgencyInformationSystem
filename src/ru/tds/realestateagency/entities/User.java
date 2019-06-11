package ru.tds.realestateagency.entities;

/**
 * Класс для создания объекта "Пользователь", используемого при авторизации
 *
 * @author Трушенков Дмитрий
 */
public class User {

    private final String login;
    private final String password;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        final StringBuilder userStringBuilder = new StringBuilder("Пользователь [");
        if (!login.isEmpty())
            userStringBuilder.append(" Логин: ").append(login).append(", ");

        if (!password.isEmpty())
            userStringBuilder.append("Пароль:").append(password);
        userStringBuilder.append(" ]");
        return userStringBuilder.toString();
    }
}
