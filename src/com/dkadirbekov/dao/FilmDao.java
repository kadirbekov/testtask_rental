package com.dkadirbekov.dao;

import com.dkadirbekov.model.Film;

import java.util.List;

/**
 * DAO class for {@link Film} entity
 */
public interface FilmDao {

    /**
     * Add film to store
     *
     * @param film {@link Film}
     */
    void add(Film film);

    /**
     * Remove {@link Film} with {@link Film#getId()} from store
     *
     * @param id {@link Film#getId()}
     */
    void remove(long id);

    /**
     * Change the {@link Film#getType()}
     *
     * @param id
     * @param filmTypeCode
     */
    void changeType(long id, String filmTypeCode);

    /**
     * @return all {@link Film films} from store
     */
    List<Film> getAll();

    /**
     * @return all {@link Film films} from store, that is not rented
     */
    List<Film> getAllAvailable();

    /**
     * @param id {@link Film#getId()}
     * @return {@link Film} by {@link Film#getId()}
     */
    Film getById(Long id);

}
