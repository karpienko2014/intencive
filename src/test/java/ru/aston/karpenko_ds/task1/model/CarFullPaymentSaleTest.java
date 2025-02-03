package ru.aston.karpenko_ds.task1.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarFullPaymentSaleTest {

    private CarSale sale;

    @BeforeEach
    void setUp() {
        Client nickSantens = new Client("Nick", "Santens", 27);
        sale = new CarFullPaymentSale("BMW F10", 17_800, nickSantens);
    }

    @Test
    void discountCalculation() {
        double discount = sale.discountCalculation();
        double expected = 4450.0;
        assertEquals(expected, discount);
    }

    @Test
    void getDiscountedPrice() {
        double discountedPrice = sale.getDiscountedPrice();
        double expected = 13350.0;
        assertEquals(expected, discountedPrice);
    }
}