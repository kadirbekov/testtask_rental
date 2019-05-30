package com.dkadirbekov.calculation;

import com.dkadirbekov.model.Price;

/**
 * Interface for calculation of price for each type
 */
public interface PriceCalculation {

    /**
     * Calculates price of film by type
     *
     * @param days days for which film is going to be rented
     * @return {@link Price} for rent
     */
    Price getPrice(int days);
}
