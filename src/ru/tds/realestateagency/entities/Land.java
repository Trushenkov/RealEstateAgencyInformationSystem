package ru.tds.realestateagency.entities;

/**
 * Класс для создания сущности "Земля"
 *
 * @author Трушенков Дмитрий
 */
public class Land {

    private String city;
    private String street;
    private String homeNumber;
    private String flatNumber;
    private double latitude;
    private double longitude;
    private double square;

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

    public Land() {
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

    public void setSquare(double square) {
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

        final StringBuilder landStringBuilder = new StringBuilder();

        if (city != null) {
            landStringBuilder.append("Город=").append(city).append(", ");
        }
        if (street != null) {
            landStringBuilder.append("Улица=").append(street).append(", ");
        }
        if (homeNumber != null) {
            landStringBuilder.append("Номер дома=").append(homeNumber).append(", ");
        }
        if (flatNumber != null) {
            landStringBuilder.append("Номер квартиры=").append(flatNumber).append(", ");
        }
        if (latitude != 0) {
            landStringBuilder.append("Широта=").append(latitude).append(", ");
        }
        if (longitude != 0) {
            landStringBuilder.append("Долгота=").append(longitude).append(", ");
        }
        if (square != 0) {
            landStringBuilder.append("Площадь=").append(square);
        }

        return landStringBuilder.toString();
    }
}
