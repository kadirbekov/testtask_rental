package com.dkadirbekov.calculation.impl;

import com.dkadirbekov.calculation.PriceCalculation;
import com.dkadirbekov.model.Price;

import java.math.BigDecimal;

/**
 * {@inheritDoc}
 * realization for new releases films
 */
public class NewReleasesPriceCalculation implements PriceCalculation {

    /**
     * {@inheritDoc}
     */
    @Override
    public Price getPrice(int days) {
        return new Price(BigDecimal.valueOf(40).multiply(BigDecimal.valueOf(days)), "EUR");
    }

}
