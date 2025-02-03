package ru.aston.karpenko_ds.task1.model;

import java.util.Objects;

public class CarCreditSale extends CarSale {

    private Credit credit;
    private final static double DISCOUNT_PERCENTAGE = 10.0;

    public CarCreditSale(String model, double price, Credit credit, Client client) {
        super(model, price, client);
        this.credit = credit;
    }

    @Override
    public double discountCalculation() {
        return (DISCOUNT_PERCENTAGE / 100.0) * calculateTotalCreditPrice();
    }

    @Override
    public double getDiscountedPrice() {
        double discountedPrice = calculateTotalCreditPrice() - discountCalculation();
        return Math.round(discountedPrice * 100.0) / 100.0;
    }

    private double calculateTotalCreditPrice() {
        return calculateMonthlyPayment() * credit.getMonths();
    }

    private double calculateMonthlyPayment() {
        double monthlyInterestRate = credit.getAnnualInterestRate() / 100 / 12;
        return getPrice() *
                (monthlyInterestRate * Math.pow(1 + monthlyInterestRate, credit.getMonths())) /
                (Math.pow(1 + monthlyInterestRate, credit.getMonths()) - 1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CarCreditSale)) return false;
        if (!super.equals(o)) return false;
        CarCreditSale that = (CarCreditSale) o;
        return credit.equals(that.credit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), credit);
    }
}
