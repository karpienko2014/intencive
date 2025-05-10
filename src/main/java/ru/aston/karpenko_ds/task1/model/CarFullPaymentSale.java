package ru.aston.karpenko_ds.task1.model;

import java.math.BigDecimal;

import static ru.aston.karpenko_ds.task1.model.DiscountPercentage.FULL_PAYMENT;

public class CarFullPaymentSale extends CarSale {

    public CarFullPaymentSale(String model, BigDecimal price, Client client) {
        super(model, price, client);
    }

    @Override
    public BigDecimal discountCalculation() {
        BigDecimal divisionByHundredResult =
                FULL_PAYMENT.getDiscountPercentage()
                        .divide(BigDecimal.valueOf(100));
        return divisionByHundredResult
                .multiply(getPrice());
    }

    @Override
    public BigDecimal getDiscountedPrice() {
        return getPrice().subtract(discountCalculation());
    }
}
