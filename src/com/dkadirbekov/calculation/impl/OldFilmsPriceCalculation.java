package com.dkadirbekov.calculation.impl;

import com.dkadirbekov.calculation.PriceCalculation;
import com.dkadirbekov.model.Price;

import java.math.BigDecimal;

/**
 * {@inheritDoc}
 * realization for old films
 */
public class OldFilmsPriceCalculation implements PriceCalculation {

    /**
     * {@inheritDoc}
     */
    @Override
    public Price getPrice(int days) {
        return new Price(
                BigDecimal.valueOf(30).add(BigDecimal.valueOf(30).multiply(BigDecimal.valueOf(Math.max(days - 5, 0)))),
                "EUR");
    }
}
