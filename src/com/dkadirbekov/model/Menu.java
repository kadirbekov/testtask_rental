package com.dkadirbekov.model;

import java.io.Serializable;

/**
 * Created by dkadirbekov on 29.05.2019.
 */
public class Menu implements Serializable {

    private static volatile Menu instance;
    private Customer currentCustomer;
    private Film film;
    private Integer daysForRental;
    private Price price;

    private Menu() {

    }

    public static Menu getInstance() {
        if (instance == null) {
            synchronized (Menu.class) {
                if (instance == null) {
                    instance = new Menu();
                }
            }
        }
        return instance;
    }

    private State state;

    public Customer getCurrentCustomer() {
        return currentCustomer;
    }

    public void setCurrentCustomer(Customer currentCustomer) {
        this.currentCustomer = currentCustomer;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public Integer getDaysForRental() {
        return daysForRental;
    }

    public void setDaysForRental(Integer daysForRental) {
        this.daysForRental = daysForRental;
    }

    public enum State {
        CHOOSE_REGISTER_OR_SIGN_IN,
        REGISTER,
        SIGN_IN,
        CHOOSE_FILM,
        ENTER_DAYS,
        CONFIRM_RENTAL,
        EXIT,
        ADMIN_MENU,
        ADD_FILM,
        REMOVE_FILM,
        CHANGE_TYPE_OF_FILM,
        LIST_ALL_FILMS,
        LIST_ALL_AVAILABLE_FILMS,
        RENTALS_BY_CUSTOMER,
        ALL_RENTALS,
        RETURN_FILM,
    }

}
