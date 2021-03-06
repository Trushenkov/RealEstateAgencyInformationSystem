package ru.tds.realestateagency.entities;

/**
 * Класс для создания сущности "Квартира"
 *
 * @author Трушенков Дмитрий
 */
public class Flat {

    private String city;
    private String street;
    private String homeNumber;
    private String flatNumber;
    private double latitude;
    private double longitude;
    private int floor;
    private int numberOfRooms;
    private double square;

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

    public Flat() {
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

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
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

    public void setSquare(double square) {
        this.square = square;
    }

    @Override
    public String toString() {

        final StringBuilder flatStringBuilder = new StringBuilder();

        if (city != null) {
            flatStringBuilder.append("Город=").append(city).append(", ");
        }
        if (street != null) {
            flatStringBuilder.append("Улица=").append(street).append(", ");
        }
        if (homeNumber != null) {
            flatStringBuilder.append("Номер дома=").append(homeNumber).append(", ");
        }
        if (flatNumber != null) {
            flatStringBuilder.append("Номер квартиры=").append(flatNumber).append(", ");
        }
        if (latitude != 0) {
            flatStringBuilder.append("Широта=").append(latitude).append(", ");
        }
        if (longitude != 0) {
            flatStringBuilder.append("Долгота=").append(longitude).append(", ");
        }
        if (floor != 0) {
            flatStringBuilder.append("Этаж=").append(floor).append(", ");
        }
        if (numberOfRooms != 0) {
            flatStringBuilder.append("Кол-во комнат=").append(numberOfRooms).append(", ");
        }
        if (square != 0) {
            flatStringBuilder.append("Площадь=").append(square);
        }

        return flatStringBuilder.toString();

    }
}
