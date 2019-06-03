package ru.tds.realestateagency.entities;

/**
 * Класс для создания сущности "Сделка"
 *
 * @author Трушеков Дмитрий
 */
public class Deal {

    private String demand;
    private String offer;

    public Deal(String demand, String offer) {
        this.demand = demand;
        this.offer = offer;
    }

    public Deal() {
    }

    public String getDemand() {
        return demand;
    }

    public void setDemand(String demand) {
        this.demand = demand;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    @Override
    public String toString() {
        final StringBuilder dealStringBuilder = new StringBuilder();
        dealStringBuilder.append("Сделка [");
        if (demand != null) {
            dealStringBuilder.append("Потребность: ").append(demand).append("; ");
        }
        if (offer != null) {
            dealStringBuilder.append("Предложение: ").append(offer);
        }
        dealStringBuilder.append("]");

        return dealStringBuilder.toString();
    }
}
