package ru.yandex.practicum.filmorate.dal.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Optional;

public interface FilmStorage {

    Optional<Film> getById(int id);

    Film save(Film film);

    void update(Film film);

    void delete(int id);

    List<Film> getAll();

    List<Film> getTopPopular(int count);

}