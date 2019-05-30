package com.dkadirbekov.service;

import com.dkadirbekov.model.Film;
import com.dkadirbekov.model.Price;
import com.dkadirbekov.model.Rental;

/**
 * Service class, for making rent
 */
public interface RentService {

    /**
     * Calculate a price for {@link Film}
     *
     * @param film {@link Film}
     * @param days for which the rent will be
     * @return {@link Price} for rent
     */
    Price calculatePrice(Film film, int days);

    /**
     * Calculate a price for {@link Film}
     *
     * @param filmId {@link Film#getId()}
     * @param days   for which the rent will be
     * @return {@link Price} for rent
     */
    Price calculatePrice(long filmId, int days);

}
