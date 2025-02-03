package ru.aston.karpenko_ds.task1.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarCreditSaleTest {

    private CarSale creditSale;

    @BeforeEach
    void setUp() {
        Client patrickFrank = new Client("Patrick", "Frank", 34);
        Credit patrickFrankCredit = new Credit(24, 12);
        creditSale = new CarCreditSale("Chevrolet Impala", 15_500, patrickFrankCredit, patrickFrank);
    }

    @Test
    void discountCalculation() {
        double discount = creditSale.discountCalculation();
        double expected = 1751.13;
        assertEquals(expected, Math.round(discount * 100.0) / 100.0);
    }

    @Test
    void getDiscountedPrice() {
        double discountedPrice = creditSale.getDiscountedPrice();
        double expected = 15760.20;
        assertEquals(expected, discountedPrice);
    }
}