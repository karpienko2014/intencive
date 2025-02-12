package ru.aston.karpenko_ds.task1.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

import static ru.aston.karpenko_ds.task1.model.DiscountPercentage.CREDIT;

public class CarCreditSale extends CarSale {

    private Credit credit;

    public CarCreditSale(String model, BigDecimal price, Credit credit, Client client) {
        super(model, price, client);
        this.credit = credit;
    }

    @Override
    public BigDecimal discountCalculation() {
        BigDecimal divisionByHundredResult =
                CREDIT.getDiscountPercentage()
                        .divide(BigDecimal.valueOf(100));
        return divisionByHundredResult
                .multiply(calculateTotalCreditPrice());
    }

    @Override
    public BigDecimal getDiscountedPrice() {
        BigDecimal discountedPrice = calculateTotalCreditPrice().subtract(discountCalculation());
        return discountedPrice
                .multiply(BigDecimal.valueOf(100))
                .divide(BigDecimal.valueOf(100));
    }

    private BigDecimal calculateTotalCreditPrice() {
        return calculateMonthlyPayment()
                .multiply(BigDecimal.valueOf(credit.getMonths()));
    }

    private BigDecimal calculateMonthlyPayment() {
        BigDecimal monthlyInterestRate =
                (
                        credit.getAnnualInterestRate()
                                .divide(BigDecimal.valueOf(100))
                )
                        .divide(BigDecimal.valueOf(12));
        return getPrice()
                .multiply(
                        monthlyInterestRate
                                .multiply(
                                        BigDecimal.valueOf(
                                                Math.pow(1 + monthlyInterestRate.doubleValue(), credit.getMonths())
                                        )
                                )
                )
                .divide(
                        BigDecimal.valueOf(
                                Math.pow(1 + monthlyInterestRate.doubleValue(), credit.getMonths()) - 1
                        ),
                        RoundingMode.HALF_UP
                );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CarCreditSale)) return false;
        if (!super.equals(o)) return false;
        CarCreditSale that = (CarCreditSale) o;
        return credit.equals(that.credit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), credit);
    }
}
