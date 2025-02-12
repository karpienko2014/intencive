package ru.aston.karpenko_ds.task1.model;

import java.math.BigDecimal;

public enum DiscountPercentage {
    CREDIT(BigDecimal.valueOf(10)),
    FULL_PAYMENT(BigDecimal.valueOf(25));

    private final BigDecimal discountPercentage;

    DiscountPercentage(BigDecimal discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public BigDecimal getDiscountPercentage() {
        return discountPercentage;
    }
}
