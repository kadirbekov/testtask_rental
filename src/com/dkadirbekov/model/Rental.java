package com.dkadirbekov.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Rental of {@link Film}
 */
public class Rental implements Serializable {

    private Long id;
    private Film film;
    private int days;
    private LocalDateTime created;
    private LocalDateTime returned;
    private RentalStatus rentalStatus;
    private Customer customer;
    private Price price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getReturned() {
        return returned;
    }

    public void setReturned(LocalDateTime returned) {
        this.returned = returned;
    }

    public RentalStatus getRentalStatus() {
        return rentalStatus;
    }

    public void setRentalStatus(RentalStatus rentalStatus) {
        this.rentalStatus = rentalStatus;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public enum RentalStatus {
        ACTIVE,
        ARCHIVED,
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rental rental = (Rental) o;
        return days == rental.days &&
                Objects.equals(id, rental.id) &&
                Objects.equals(film, rental.film) &&
                Objects.equals(created, rental.created) &&
                Objects.equals(returned, rental.returned) &&
                rentalStatus == rental.rentalStatus &&
                Objects.equals(customer, rental.customer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, film, days, created, returned, rentalStatus, customer);
    }

    @Override
    public String toString() {
        if (price.isPaidWithBonusPoints()) {
            return String.format("%s %d days (Paid with 25 bonus points)", film.toString(), days);
        } else {
            return String.format("%s %d days (Paid with %s)", film.toString(), days, price.toString());
        }
    }
}
