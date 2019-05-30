package com.dkadirbekov.service.impl;

import com.dkadirbekov.calculation.PriceCalculation;
import com.dkadirbekov.dao.FilmDao;
import com.dkadirbekov.dao.impl.FilmDaoMemoryImpl;
import com.dkadirbekov.factory.PriceCalculationFactory;
import com.dkadirbekov.model.Film;
import com.dkadirbekov.model.Price;
import com.dkadirbekov.model.Rental;
import com.dkadirbekov.service.RentService;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Service class for making rent
 */
public class RentServiceImpl implements RentService {

    private static volatile RentServiceImpl instance;

    private FilmDao filmDao;
    private PriceCalculationFactory priceCalculationFactory;

    private RentServiceImpl() {
        filmDao = FilmDaoMemoryImpl.getInstance();
        priceCalculationFactory = PriceCalculationFactory.getInstance();
    }

    public static RentServiceImpl getInstance() {
        if (instance == null) {
            synchronized (RentServiceImpl.class) {
                if (instance == null) {
                    instance = new RentServiceImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public Price calculatePrice(Film film, int days) {
        PriceCalculation priceCalculation = priceCalculationFactory.newInstance(film.getType().getCode());
        return priceCalculation.getPrice(days);
    }

    @Override
    public Price calculatePrice(long filmId, int days) {
        return calculatePrice(filmDao.getById(filmId), days);
    }

}
