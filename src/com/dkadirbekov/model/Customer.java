package com.dkadirbekov.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Customer entity
 */
public class Customer implements Serializable {

    private Long id;
    private String code;
    private Integer bonusPoints;

    public Customer(String code) {
        this.code = code;
    }

    public Customer() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getBonusPoints() {
        return bonusPoints;
    }

    public void setBonusPoints(Integer bonusPoints) {
        this.bonusPoints = bonusPoints;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id) &&
                Objects.equals(code, customer.code) &&
                Objects.equals(bonusPoints, customer.bonusPoints);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, bonusPoints);
    }

    @Override
    public String toString() {
        return String.format("%d. %s", id, code);
    }
}
