package com.dkadirbekov.dao.impl;

import com.dkadirbekov.dao.FilmDao;
import com.dkadirbekov.dao.FilmTypeDao;
import com.dkadirbekov.model.Film;
import com.dkadirbekov.model.Film.Status;
import com.dkadirbekov.model.FilmType.FilmTypeCode;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * {@inheritDoc}
 * <p>
 * implementation of {@link FilmDao} with the use of memory without persistence
 */
public class FilmDaoMemoryImpl implements FilmDao {

    private static volatile FilmDaoMemoryImpl instance;

    private FilmTypeDao filmTypeDao;

    private volatile Set<Film> films;

    /**
     * Sequence for {@link Film#getId()}
     */
    private volatile AtomicLong seq;

    private FilmDaoMemoryImpl() {
        films = new LinkedHashSet<>();
        seq = new AtomicLong(0);
        filmTypeDao = FilmTypeDaoMemoryImpl.getInstance();

        migrate();
    }

    private void migrate() {
        add(new Film("Avengers 3", "Avengers 3", filmTypeDao.getByCode(FilmTypeCode.NEW_RELEASES.name())));
        add(new Film("Interstellar", "Interstellar", filmTypeDao.getByCode(FilmTypeCode.REGULAR_FILMS.name())));
        add(new Film("Matrix", "Matrix", filmTypeDao.getByCode(FilmTypeCode.OLD_FILMS.name())));
    }

    public static FilmDaoMemoryImpl getInstance() {
        if (instance == null) {
            synchronized (FilmDaoMemoryImpl.class) {
                if (instance == null) {
                    instance = new FilmDaoMemoryImpl();
                }
            }
        }
        return instance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(Film film) {
        synchronized (films) {
            film.setId(seq.incrementAndGet());
            film.setStatus(Status.AVAILABLE);
            films.add(film);
        }
    }

    /**
     * {@inheritDoc}
     * actually, changes st
     */
    @Override
    public void remove(long id) {
        synchronized (films) {
            films.stream().filter(film -> film.getId().equals(id)).forEach(film -> film.setStatus(Status.ARCHIVED));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void changeType(long id, String filmTypeCode) {
        synchronized (films) {
            films.stream().filter(film -> film.getId().equals(id))
                    .forEach(film -> film.setType(filmTypeDao.getByCode(filmTypeCode)));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Film> getAll() {
        return films.stream().filter(film -> !film.getStatus().equals(Status.ARCHIVED)).collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Film> getAllAvailable() {
        return films.stream().filter(film -> film.getStatus().equals(Status.AVAILABLE)).collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Film getById(Long id) {
        return films.stream().filter(film -> film.getId().equals(id)).findFirst().orElse(null);
    }
}
