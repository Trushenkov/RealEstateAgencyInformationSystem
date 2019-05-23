package ru.tds.realestateagency.entities;

/**
 * Класс для создания сущности "Дом"
 *
 * @author Трушенков Дмитрий
 */
public class Home {

    private  String city;
    private  String street;
    private  String homeNumber;
    private  String flatNumber;
    private  double latitude;
    private  double longitude;
    private  int numberOfFloors;
    private  int numberOfRooms;
    private  double square;

    public Home() {
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setHomeNumber(String homeNumber) {
        this.homeNumber = homeNumber;
    }

    public void setFlatNumber(String flatNumber) {
        this.flatNumber = flatNumber;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setNumberOfFloors(int numberOfFloors) {
        this.numberOfFloors = numberOfFloors;
    }

    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public void setSquare(Double square) {
        this.square = square;
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
