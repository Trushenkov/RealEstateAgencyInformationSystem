package ru.tds.realestateagency.entities;

/**
 * Класс для создания сущности "Предложение"
 *
 * @author Трушенков Дмитрий
 */
public class Offer {

    private String client;
    private String realtor;
    private String realEstate;
    private int price;

    public Offer(String client, String realtor, String realEstate, int price) {
        this.client = client;
        this.realtor = realtor;
        this.realEstate = realEstate;
        this.price = price;
    }

    public Offer() {
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

    public String getRealEstate() {
        return realEstate;
    }

    public void setRealEstate(String realEstate) {
        this.realEstate = realEstate;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {

        StringBuilder offerStringBuilder = new StringBuilder();

        if (client != null) {
            offerStringBuilder.append("Клиент=").append(client).append(", ");
        }
        if (realtor != null) {
            offerStringBuilder.append("Риэлтор=").append(realtor).append(", ");
        }
        if (realEstate != null) {
            offerStringBuilder.append("Объект недвижимости=").append(realEstate).append(", ");
        }
        if (price != 0) {
            offerStringBuilder.append("Цена=").append(price).append(", ");
        }

        return offerStringBuilder.toString();
//        return "Предложение {" +
//                "client=" + client +
//                ", realtor=" + realtor +
//                ", realEstate=" + realEstate +
//                ", price=" + price +
//                '}';
    }
}
