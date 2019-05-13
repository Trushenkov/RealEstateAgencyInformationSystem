package ru.tds.realestateagency.entities;

/**
 * Класс для создания сущности "Дом"
 *
 * @author Трушенков Дмитрий
 */
public class Home {

    private final String city;
    private final String street;
    private final String homeNumber;
    private final String flatNumber;
    private final double latitude;
    private final double longitude;
    private final int numberOfFloors;
    private final int numberOfRooms;
    private final double square;

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

    public String getStreet() {
        return street;
    }

    public String getHomeNumber() {
        return homeNumber;
    }

    public String getFlatNumber() {
        return flatNumber;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public int getNumberOfFloors() {
        return numberOfFloors;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public double getSquare() {
        return square;
    }

    @Override
    public String toString() {
        return "Home{" +
                "city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", homeNumber='" + homeNumber + '\'' +
                ", flatNumber='" + flatNumber + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", numberOfFloors=" + numberOfFloors +
                ", numberOfRooms=" + numberOfRooms +
                ", square=" + square +
                '}';
    }
}
