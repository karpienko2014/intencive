package ru.aston.karpenko_ds.task1.service;

import ru.aston.karpenko_ds.task1.model.CarSale;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.ToDoubleFunction;

public class SalesService implements Sales<CarSale> {

    private List<CarSale> salesList;

    public SalesService() {
        salesList = new ArrayList<>();
    }

    public BigDecimal totalPrice(ToDoubleFunction<CarSale> function) {
        double totalPrice =
                salesList
                        .stream()
                        .mapToDouble(function)
                        .sum();
        return BigDecimal.valueOf(totalPrice);
    }

    public List<CarSale> sortSalesList(Comparator<CarSale> comparator) {
        salesList.sort(comparator);
        return salesList;
    }

    public SalesService add(CarSale sale) {
        salesList.add(sale);
        return this;
    }

    public CarSale getSale(int index) {
        return salesList.get(index);
    }

    public int getSalesNumber() {
        return salesList.size();
    }
}
