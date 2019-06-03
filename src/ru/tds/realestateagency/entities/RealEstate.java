package ru.tds.realestateagency.entities;

/**
 * Класс для создания сущности "Объект недвижимости"
 *
 * @author Трушенков Дмитрий
 */
public class RealEstate {

    private String home;
    private String flat;
    private String land;

    public RealEstate() {
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getFlat() {
        return flat;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }

    public String getLand() {
        return land;
    }

    public void setLand(String land) {
        this.land = land;
    }

    @Override
    public String toString() {

        final StringBuilder realEstateStringBuilder = new StringBuilder();

        if (home != null) {
            realEstateStringBuilder.append("Дом [").append(home).append("]");
        }
        if (flat != null) {
            realEstateStringBuilder.append("Квартира [").append(flat).append("]");
        }
        if (land != null) {
            realEstateStringBuilder.append("Земля [").append(land).append("]");
        }
        return realEstateStringBuilder.toString();
    }
}
