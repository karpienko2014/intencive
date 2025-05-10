package ru.aston.karpenko_ds.task1.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Credit {

    private int months;
    private BigDecimal annualInterestRate;

    public Credit(int months, BigDecimal annualInterestRate) {
        this.months = months;
        this.annualInterestRate = annualInterestRate;
    }

    public int getMonths() {
        return months;
    }

    public BigDecimal getAnnualInterestRate() {
        return annualInterestRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Credit)) return false;
        Credit credit = (Credit) o;
        return months == credit.months && annualInterestRate.equals(credit.annualInterestRate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(months, annualInterestRate);
    }
}
