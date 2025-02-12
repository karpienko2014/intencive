package ru.aston.karpenko_ds.task1.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

class CarFullPaymentSaleTest {

    private CarSale sale;

    @BeforeEach
    void setUp() {
        Client nickSantens = new Client("Nick", "Santens", 27);
        sale = new CarFullPaymentSale("BMW F10", BigDecimal.valueOf(17_800), nickSantens);
    }

    @Test
    void discountCalculation() {
        BigDecimal discount = sale.discountCalculation();
        BigDecimal expected = BigDecimal.valueOf(4450.0);
        assertEquals(expected, discount.setScale(1, RoundingMode.HALF_UP));
    }

    @Test
    void getDiscountedPrice() {
        BigDecimal discountedPrice = sale.getDiscountedPrice();
        BigDecimal expected = BigDecimal.valueOf(13350.0);
        assertEquals(expected, discountedPrice.setScale(1, RoundingMode.HALF_UP));
    }
}