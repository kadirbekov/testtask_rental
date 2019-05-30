package com.dkadirbekov.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Type of {@link Film}
 */
public class FilmType implements Serializable {

    private Long id;
    private String code;
    private String name;

    public FilmType(Long id, String code, String name) {
        this.id = id;
        this.code = code;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public enum FilmTypeCode {
        NEW_RELEASES,
        REGULAR_FILMS,
        OLD_FILMS,
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilmType filmType = (FilmType) o;
        return Objects.equals(id, filmType.id) &&
                Objects.equals(code, filmType.code) &&
                Objects.equals(name, filmType.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, name);
    }

    @Override
    public String toString() {
        return String.format("%d. %s", id, name);
    }

}
