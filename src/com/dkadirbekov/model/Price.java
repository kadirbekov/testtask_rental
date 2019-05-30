package com.dkadirbekov.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Price of any product
 */
public class Price implements Serializable {

    private BigDecimal amount;
    private String currencyCode;
    private boolean isPaidWithBonusPoints;

    public Price(BigDecimal amount, String currencyCode) {
        this.amount = amount;
        this.currencyCode = currencyCode;
    }

    public Price(Integer points) {
        isPaidWithBonusPoints = true;
        amount = BigDecimal.valueOf(points);
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price = (Price) o;
        return Objects.equals(amount, price.amount) &&
                Objects.equals(currencyCode, price.currencyCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, currencyCode);
    }

    @Override
    public String toString() {
        return String.format("%s %s", amount.toString(), currencyCode);
    }

    public boolean isPaidWithBonusPoints() {
        return isPaidWithBonusPoints;
    }

    public void setPaidWithBonusPoints(boolean paidWithBonusPoints) {
        isPaidWithBonusPoints = paidWithBonusPoints;
    }
}
