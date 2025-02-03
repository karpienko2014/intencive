package ru.aston.karpenko_ds.task1.model;

import java.util.Objects;

public class Credit {

    private int months;
    private double annualInterestRate;

    public Credit(int months, double annualInterestRate) {
        this.months = months;
        this.annualInterestRate = annualInterestRate;
    }

    public int getMonths() {
        return months;
    }

    public double getAnnualInterestRate() {
        return annualInterestRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Credit)) return false;
        Credit credit = (Credit) o;
        return months == credit.months && Double.compare(credit.annualInterestRate, annualInterestRate) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(months, annualInterestRate);
    }
}
