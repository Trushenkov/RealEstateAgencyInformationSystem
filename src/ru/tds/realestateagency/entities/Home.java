package ru.tds.realestateagency.entities;

/**
 * Класс для создания сущности "Дом"
 *
 * @author Трушенков Дмитрий
 */
public class Home {

    private String city;
    private String street;
    private String homeNumber;
    private String flatNumber;
    private double latitude;
    private double longitude;
    private int numberOfFloors;
    private int numberOfRooms;
    private double square;

    public Home() {
    }

    public Home(final String city,
                final String street,
                final String homeNumber,
                final String flatNumber,
                final double latitude,
                final double longitude,
                final int numberOfFloors,
                final int numberOfRooms,
                final double square) {
        this.city = city;
        this.street = street;
        this.homeNumber = homeNumber;
        this.flatNumber = flatNumber;
        this.latitude = latitude;
        this.longitude = longitude;
        this.numberOfFloors = numberOfFloors;
        this.numberOfRooms = numberOfRooms;
        this.square = square;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHomeNumber() {
        return homeNumber;
    }

    public void setHomeNumber(String homeNumber) {
        this.homeNumber = homeNumber;
    }

    public String getFlatNumber() {
        return flatNumber;
    }

    public void setFlatNumber(String flatNumber) {
        this.flatNumber = flatNumber;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getNumberOfFloors() {
        return numberOfFloors;
    }

    public void setNumberOfFloors(int numberOfFloors) {
        this.numberOfFloors = numberOfFloors;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public double getSquare() {
        return square;
    }

    public void setSquare(Double square) {
        this.square = square;
    }

    @Override
    public String toString() {
        StringBuilder homeStringBuilder = new StringBuilder();
        if (city != null) {
            homeStringBuilder.append("Город=").append(city).append(", ");
        }
        if (street != null) {
            homeStringBuilder.append("Улица=").append(street).append(", ");
        }
        if (homeNumber != null) {
            homeStringBuilder.append("Номер дома=").append(homeNumber).append(", ");
        }
        if (flatNumber != null) {
            homeStringBuilder.append("Номер квартиры=").append(flatNumber).append(", ");
        }
        if (latitude != 0) {
            homeStringBuilder.append("Широта=").append(latitude).append(", ");
        }
        if (longitude != 0) {
            homeStringBuilder.append("Долгота=").append(longitude).append(", ");
        }
        if (numberOfFloors != 0) {
            homeStringBuilder.append("Этажность дома=").append(numberOfFloors).append(", ");
        }
        if (numberOfRooms != 0) {
            homeStringBuilder.append("Кол-во комнат=").append(numberOfRooms).append(", ");
        }
        if (square != 0) {
            homeStringBuilder.append("Площадь=").append(square);
        }

        return homeStringBuilder.toString();
    }

}
