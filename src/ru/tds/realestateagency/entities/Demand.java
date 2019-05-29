package ru.tds.realestateagency.entities;

/**
 * Класс для создания сущности "Потребность"
 *
 * @author Трушенков Дмитрий
 */
public class Demand {

    private String client;
    private String realtor;
    private String typeRealEstate;
    private String address;
    private int minPrice;
    private int maxPrice;
    private int minSquare;
    private int maxSquare;
    private int minCountRooms;
    private int maxCountRooms;
    private int minFloor;
    private int maxFloor;
    private int minNumberOfFloors;
    private int maxNumberOfFloors;

    public Demand() {
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getRealtor() {
        return realtor;
    }

    public void setRealtor(String realtor) {
        this.realtor = realtor;
    }

    public String getTypeRealEstate() {
        return typeRealEstate;
    }

    public void setTypeRealEstate(String typeRealEstate) {
        this.typeRealEstate = typeRealEstate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(int minPrice) {
        this.minPrice = minPrice;
    }

    public int getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(int maxPrice) {
        this.maxPrice = maxPrice;
    }

    public int getMinSquare() {
        return minSquare;
    }

    public void setMinSquare(int minSquare) {
        this.minSquare = minSquare;
    }

    public int getMaxSquare() {
        return maxSquare;
    }

    public void setMaxSquare(int maxSquare) {
        this.maxSquare = maxSquare;
    }

    public int getMinCountRooms() {
        return minCountRooms;
    }

    public void setMinCountRooms(int minCountRooms) {
        this.minCountRooms = minCountRooms;
    }

    public int getMaxCountRooms() {
        return maxCountRooms;
    }

    public void setMaxCountRooms(int maxCountRooms) {
        this.maxCountRooms = maxCountRooms;
    }

    public int getMinFloor() {
        return minFloor;
    }

    public void setMinFloor(int minFloor) {
        this.minFloor = minFloor;
    }

    public int getMaxFloor() {
        return maxFloor;
    }

    public void setMaxFloor(int maxFloor) {
        this.maxFloor = maxFloor;
    }

    public int getMinNumberOfFloors() {
        return minNumberOfFloors;
    }

    public void setMinNumberOfFloors(int minNumberOfFloors) {
        this.minNumberOfFloors = minNumberOfFloors;
    }

    public int getMaxNumberOfFloors() {
        return maxNumberOfFloors;
    }

    public void setMaxNumberOfFloors(int maxNumberOfFloors) {
        this.maxNumberOfFloors = maxNumberOfFloors;
    }

    @Override
    public String toString() {
        final StringBuilder demandStringBuilder = new StringBuilder("Потребность [");
        if (client != null) {
            demandStringBuilder.append("Клиент:").append(client);
        }
        if (realtor!= null) {
            demandStringBuilder.append(", Риэлтор:").append(realtor);
        }
        if (typeRealEstate != null) {
            demandStringBuilder.append(", Тип объекта недвижимости:").append(typeRealEstate);
        }
        if (address != null) {
            demandStringBuilder.append(", Адрес:").append(address);
        }
        if (minPrice != 0) {
            demandStringBuilder.append(", Мин. цена:").append(minPrice);
        }
        if (maxPrice != 0) {
            demandStringBuilder.append(", Макс. цена:").append(maxPrice);
        }
        if (minSquare != 0) {
            demandStringBuilder.append(", Мин. площадь:").append(minSquare);
        }
        if (maxSquare != 0) {
            demandStringBuilder.append(", Макс. площадь:").append(maxSquare);
        }
        if (minCountRooms != 0) {
            demandStringBuilder.append(", Мин. кол-во комнат:").append(minCountRooms);
        }
        if (maxCountRooms != 0) {
            demandStringBuilder.append(", Макс. кол-во комнат:").append(maxCountRooms);
        }
        if (minFloor != 0) {
            demandStringBuilder.append(", Мин. этаж:").append(minFloor);
        }
        if (maxFloor != 0) {
            demandStringBuilder.append(", Макс. этаж:").append(maxFloor);
        }
        if (minNumberOfFloors != 0) {
            demandStringBuilder.append(", Мин. этажность дома:").append(minNumberOfFloors);
        }
        if (maxNumberOfFloors != 0) {
            demandStringBuilder.append(", Макс. этажность дома:").append(maxNumberOfFloors);
        }
        demandStringBuilder.append(']');

        return demandStringBuilder.toString();
    }
}
