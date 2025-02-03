package ru.aston.karpenko_ds.task1.service;

import java.util.Comparator;
import java.util.List;
import java.util.function.ToDoubleFunction;

public interface Sales<T> {
    double totalPrice(ToDoubleFunction<T> function);
    List<T> sortSalesList(Comparator<T> comparator);
    Sales<T> add(T sale);
    T getSale(int index);
    int getSalesNumber();
}
