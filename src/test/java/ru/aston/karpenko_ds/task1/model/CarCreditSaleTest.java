package ru.aston.karpenko_ds.task1.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

class CarCreditSaleTest {

    private CarSale creditSale;

    @BeforeEach
    void setUp() {
        Client patrickFrank = new Client("Patrick", "Frank", 34);
        Credit patrickFrankCredit = new Credit(24, BigDecimal.valueOf(12));
        creditSale = new CarCreditSale("Chevrolet Impala", BigDecimal.valueOf(15_500), patrickFrankCredit, patrickFrank);
    }

    @Test
    void discountCalculation() {
        BigDecimal discount = creditSale.discountCalculation();
        BigDecimal expected = BigDecimal.valueOf(1751.13);
        assertEquals(expected, discount.setScale(2, RoundingMode.HALF_UP));
    }

    @Test
    void getDiscountedPrice() {
        BigDecimal discountedPrice = creditSale.getDiscountedPrice();
        BigDecimal expected = BigDecimal.valueOf(15760.20);
        assertEquals(expected, discountedPrice.setScale(1, RoundingMode.HALF_UP));
    }
}