package ru.aston.karpenko_ds.task1.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.aston.karpenko_ds.task1.model.*;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

class SalesServiceTest {

    private Sales<CarSale> sales;

    @BeforeEach
    void setUp() {
        Client johnMitchell = new Client("John", "Mitchell", 38);
        Credit johnMitchellCredit = new Credit(48, 15);
        CarSale johnMitchellSale = new CarCreditSale("Ford Mondeo", 12_500, johnMitchellCredit, johnMitchell);

        Client nickSantens = new Client("Nick", "Santens", 27);
        CarSale nickSantensSale = new CarFullPaymentSale("BMW F10", 17_800, nickSantens);

        Client thomasPayne = new Client("Thomas", "Payne", 44);
        Credit thomasPayneCredit = new Credit(36, 7.5);
        CarSale thomasPayneSale = new CarCreditSale("Chrysler 200", 22_450, thomasPayneCredit, thomasPayne);

        sales = new SalesService();
        sales.add(johnMitchellSale)
                .add(nickSantensSale)
                .add(thomasPayneSale);
    }

    @Test
    void totalPrice() {
        double totalPrice = sales.totalPrice(CarSale::getDiscountedPrice);
        double expected = 51004.64;
        assertEquals(expected, totalPrice);
    }

    @Test
    void sortSalesList() {
        Comparator<CarSale> comparator = Comparator.comparing((carSale) -> carSale.getClient().getLastName());
        sales.sortSalesList(comparator);
        assertEquals("Mitchell", sales.getSale(0).getClient().getLastName());
        assertEquals("Payne", sales.getSale(1).getClient().getLastName());
        assertEquals("Santens", sales.getSale(2).getClient().getLastName());
    }

    @Test
    void getSalesNumber() {
        int salesNumber = sales.getSalesNumber();
        int expected = 3;
        assertEquals(expected, salesNumber);
    }
}