package ru.aston.karpenko_ds.task1.model;

public class CarFullPaymentSale extends CarSale {

    private final static double DISCOUNT_PERCENTAGE = 25.0;

    public CarFullPaymentSale(String model, double price, Client client) {
        super(model, price, client);
    }

    @Override
    public double discountCalculation() {
        return (DISCOUNT_PERCENTAGE / 100.0) * getPrice();
    }

    @Override
    public double getDiscountedPrice() {
        return getPrice() - discountCalculation();
    }
}
