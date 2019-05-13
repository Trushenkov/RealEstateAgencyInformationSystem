package ru.tds.realestateagency.entities;

/**
 * Класс для создания сущности "Квартира"
 *
 * @author Трушенков Дмитрий
 */
public class Flat {

    private final String city;
    private final String street;
    private final String homeNumber;
    private final String flatNumber;
    private final double latitude;
    private final double longitude;
    private final int floor;
    private final int numberOfRooms;
    private final double square;

    public Flat(final String city,
                final String street,
                final String homeNumber,
                final String flatNumber,
                final double latitude,
                final double longitude,
                final int floor,
                final int numberOfRooms,
                final double square) {
        this.city = city;
        this.street = street;
        this.homeNumber = homeNumber;
        this.flatNumber = flatNumber;
        this.latitude = latitude;
        this.longitude = longitude;
        this.floor = floor;
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

    public int getFloor() {
        return floor;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public double getSquare() {
        return square;
    }

    @Override
    public String toString() {
        return "Flat{" +
                "city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", homeNumber='" + homeNumber + '\'' +
                ", flatNumber='" + flatNumber + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", floor=" + floor +
                ", numberOfRooms=" + numberOfRooms +
                ", square=" + square +
                '}';
    }
}
