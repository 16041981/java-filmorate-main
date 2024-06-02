package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Optional;

public interface FilmRepository {
    Optional<Film> getById(long id);

    Film save(Film film);

    void update(Film film);

    List<Film> getAll();

    void addLike(long filmId, long userId);

    void deleteLike(long filmId, long userId);

    List<Film> getTopPopular(int count);
}
