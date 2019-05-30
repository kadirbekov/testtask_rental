package com.dkadirbekov.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Class for film.
 */
public class Film implements Serializable {

    private Long id;
    private String name;
    private String description;
    private FilmType type;
    private Status status;

    public Film() {
    }

    /**
     * Constructor
     *
     * @param name
     * @param description
     * @param type
     */
    public Film(String name, String description, FilmType type) {
        this.name = name;
        this.description = description;
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FilmType getType() {
        return type;
    }

    public void setType(FilmType type) {
        this.type = type;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public enum Status {
        AVAILABLE,
        ARCHIVED,
        RENTED,
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return Objects.equals(id, film.id) &&
                Objects.equals(name, film.name) &&
                Objects.equals(description, film.description) &&
                Objects.equals(type, film.type) &&
                status == film.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, type, status);
    }

    @Override
    public String toString() {
        return String.format("%d. %s (%s)", id, name, type.getName());
    }
}
