package ru.aston.karpenko_ds.task1.model;

import java.util.Objects;

public abstract class CarSale {

    private String model;
    private double price;
    private Client client;

    public CarSale(String model, double price, Client client) {
        this.model = model;
        this.price = price;
        this.client = client;
    }

    public String getModel() {
        return model;
    }

    public double getPrice() {
        return price;
    }

    public Client getClient() {
        return client;
    }

    public abstract double discountCalculation();

    public abstract double getDiscountedPrice();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CarSale)) return false;
        CarSale carSale = (CarSale) o;
        return Double.compare(carSale.price, price) == 0 && model.equals(carSale.model) && client.equals(carSale.client);
    }

    @Override
    public int hashCode() {
        return Objects.hash(model, price, client);
    }
}
