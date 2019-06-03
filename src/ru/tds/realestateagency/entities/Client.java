package ru.tds.realestateagency.entities;

/**
 * Класс для создания сущности "Клиент"
 *
 * @author Трушенков Дмитрий
 */
public class Client {

    private String lastName;
    private String firstName;
    private String middleName;
    private String phoneNumber;
    private String email;

    public Client(String lastName, String firstName, String middleName, String phoneNumber, String email) {
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
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

        final StringBuilder clientStringBuilder = new StringBuilder();

        if (lastName != null) {
            clientStringBuilder.append(lastName).append(" ");
        }
        if (firstName != null) {
            clientStringBuilder.append(firstName).append(" ");
        }
        if (middleName != null) {
            clientStringBuilder.append(middleName);
        }

        return clientStringBuilder.toString();
    }
}
