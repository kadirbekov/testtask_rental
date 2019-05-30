package com.dkadirbekov.dao.impl;

import com.dkadirbekov.dao.RentalDao;
import com.dkadirbekov.model.Rental;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * {@inheritDoc}
 */
public class RentalDaoMemoryImpl implements RentalDao {

    private static volatile RentalDaoMemoryImpl instance;

    private Set<Rental> rentals;
    private volatile AtomicLong seq;

    private RentalDaoMemoryImpl() {
        rentals = new LinkedHashSet<>();
        seq = new AtomicLong(0);
    }

    public static RentalDaoMemoryImpl getInstance() {
        if (instance == null) {
            synchronized (RentalDaoMemoryImpl.class) {
                if (instance == null) {
                    instance = new RentalDaoMemoryImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public void add(Rental rental) {
        rental.setId(seq.incrementAndGet());
        rental.setCreated(LocalDateTime.now());
        rental.setRentalStatus(Rental.RentalStatus.ACTIVE);
        rentals.add(rental);
    }

    @Override
    public List<Rental> listByStatus(Rental.RentalStatus rentalStatus) {
        return rentals.stream().filter(rental -> rental.getRentalStatus().equals(rentalStatus)).collect(Collectors.toList());
    }

    @Override
    public List<Rental> getActiveByCustomer(Long idOfCustomer) {
        return rentals.stream()
                .filter(rental -> rental.getCustomer().getId().equals(idOfCustomer))
                .filter(rental -> rental.getRentalStatus().equals(Rental.RentalStatus.ACTIVE))
                .collect(Collectors.toList());

    }

    @Override
    public Rental getById(Long id) {
        return rentals.stream().filter(rental -> rental.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public Rental getByFilmId(Long filmId) {
        return rentals.stream()
                .filter(rental -> rental.getRentalStatus().equals(Rental.RentalStatus.ACTIVE))
                .filter(rental -> rental.getFilm().getId().equals(filmId)).findFirst().orElse(null);
    }
}
