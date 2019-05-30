package com.dkadirbekov.dao;

import com.dkadirbekov.model.FilmType;

import java.util.List;

/**
 * DAO class for {@link FilmType} entity
 */
public interface FilmTypeDao {

    /**
     * @return {@link List} of {@link FilmType filmTypes}
     */
    List<FilmType> getAll();

    /**
     * @param code {@link FilmType#getCode()}
     * @return {@link FilmType} by {@link FilmType#getCode()}
     */
    FilmType getByCode(String code);

    FilmType getById(Long id);
}
