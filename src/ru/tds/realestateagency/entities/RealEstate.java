package ru.tds.realestateagency.entities;

/**
 * Project name: RealEstateAgencyInformationSystem
 * Date: 24.05.2019 (пятница)
 * Package name: ru.tds.realestateagency.entities
 *
 * @author Trushenkov Dmitry
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
        if (home != null) {
            return String.format("Дом [%s]", home);
        }
        if (flat != null) {
            return String.format("Квартира [%s]", flat);
        }
        if (land != null) {
            return String.format("Земля [%s]", land);
        }
        return null;
    }
}
