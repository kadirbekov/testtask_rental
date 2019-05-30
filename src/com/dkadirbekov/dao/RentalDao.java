package com.dkadirbekov.dao;

import com.dkadirbekov.model.Rental;
import com.dkadirbekov.model.Rental.RentalStatus;

import java.util.List;

/**
 * DAO class for {@link Rental} entity
 */
public interface RentalDao {

    void add(Rental rental);

    List<Rental> listByStatus(RentalStatus rentalStatus);

    List<Rental> getActiveByCustomer(Long idOfCustomer);

    Rental getById(Long id);

    Rental getByFilmId(Long idOfFilm);
}
