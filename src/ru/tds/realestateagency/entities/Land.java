package ru.tds.realestateagency.entities;

/**
 * Класс для создания сущности "Земля"
 *
 * @author Трушенков Дмитрий
 */
public class Land {

    private final String city;
    private final String street;
    private final String homeNumber;
    private final String flatNumber;
    private final double latitude;
    private final double longitude;
    private final double square;

    public Land(final String city,
                final String street,
                final String homeNumber,
                final String flatNumber,
                final double latitude,
                final double longitude,
                final double square) {
        this.city = city;
        this.street = street;
        this.homeNumber = homeNumber;
        this.flatNumber = flatNumber;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public double getSquare() {
        return square;
    }

    @Override
    public String toString() {
        return "Land{" +
                "city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", homeNumber='" + homeNumber + '\'' +
                ", flatNumber='" + flatNumber + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", square=" + square +
                '}';
    }
}
