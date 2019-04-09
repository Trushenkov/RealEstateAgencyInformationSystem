package ru.tds.realestateagency.entities;

/**
 * Класс объекта "Клиент"
 *
 * @author Трушенков Дмитрий
 */
public class Client {

    private String lastName;
    private String firstName;
    private String middleName;
    private int phoneNumber;
    private String email;

    public Client(String lastName, String firstName, String middleName, int phoneNumber, String email) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public Client() {
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Клиент [" +
                " Фамилия ='" + lastName + '\'' +
                ", Имя ='" + firstName + '\'' +
                ", Отчество ='" + middleName + '\'' +
                ", Номер телефона =" + phoneNumber +
                ", Электронная почта ='" + email + '\'' +
                ']';
    }
}
