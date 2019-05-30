package com.dkadirbekov.dao.impl;

import com.dkadirbekov.dao.FilmTypeDao;
import com.dkadirbekov.model.FilmType;
import com.dkadirbekov.model.FilmType.FilmTypeCode;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * Created by dkadirbekov on 28.05.2019.
 */
public class FilmTypeDaoMemoryImpl implements FilmTypeDao {

    private static volatile FilmTypeDaoMemoryImpl instance;

    private volatile Set<FilmType> types;

    /**
     * Sequence for {@link FilmType#getId()}
     */
    private volatile AtomicLong seq;

    private FilmTypeDaoMemoryImpl() {
        types = new LinkedHashSet<>();
        seq = new AtomicLong(0);

        migrateTypes();
    }

    private void migrateTypes() {
        types.addAll(Arrays.asList(
                new FilmType(seq.incrementAndGet(), FilmTypeCode.NEW_RELEASES.name(), "New releases"),
                new FilmType(seq.incrementAndGet(), FilmTypeCode.REGULAR_FILMS.name(), "Regular films"),
                new FilmType(seq.incrementAndGet(), FilmTypeCode.OLD_FILMS.name(), "Old films")));
    }

    public static FilmTypeDaoMemoryImpl getInstance() {
        if (instance == null) {
            synchronized (FilmTypeDaoMemoryImpl.class) {
                if (instance == null) {
                    instance = new FilmTypeDaoMemoryImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public List<FilmType> getAll() {
        return types.stream().collect(Collectors.toList());
    }

    @Override
    public FilmType getByCode(String code) {
        return types.stream().filter(filmType -> filmType.getCode().equals(code)).findFirst().orElse(null);
    }

    @Override
    public FilmType getById(Long id) {
        return types.stream().filter(filmType -> filmType.getId().equals(id)).findFirst().orElse(null);
    }

}
