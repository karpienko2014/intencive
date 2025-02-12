package ru.aston.karpenko_ds.task1.model;

import java.math.BigDecimal;
import java.util.Objects;

public abstract class CarSale {

    private String model;
    private BigDecimal price;
    private Client client;

    public CarSale(String model, BigDecimal price, Client client) {
        this.model = model;
        this.price = price;
        this.client = client;
    }

    public String getModel() {
        return model;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Client getClient() {
        return client;
    }

    public abstract BigDecimal discountCalculation();

    public abstract BigDecimal getDiscountedPrice();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CarSale)) return false;
        CarSale carSale = (CarSale) o;
        return model.equals(carSale.model) && price.equals(carSale.price) && client.equals(carSale.client);
    }

    @Override
    public int hashCode() {
        return Objects.hash(model, price, client);
    }
}
