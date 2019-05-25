package ru.tds.realestateagency.entities;

/**
 * Класс для создания сущности "Риэлтор"
 *
 * @author Трушенков Дмитрий
 */
public class Realtor {

    private String lastName;
    private String firstName;
    private String middleName;
    private int commissionPart;

    public Realtor(String lastName, String firstName, String middleName, int commissionPart) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.commissionPart = commissionPart;
    }

    public Realtor() {
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

    public int getCommissionPart() {
        return commissionPart;
    }

    public void setCommissionPart(int commissionPart) {
        this.commissionPart = commissionPart;
    }

    @Override
    public String toString() {

        StringBuilder realtorStringBuilder = new StringBuilder();

        if (lastName != null) {
            realtorStringBuilder.append(lastName).append(" ");
        }
        if (firstName != null) {
            realtorStringBuilder.append(firstName).append(" ");
        }
        if (middleName != null) {
            realtorStringBuilder.append(middleName);
        }

        return realtorStringBuilder.toString();
    }
}
