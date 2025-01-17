package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {

    Film createFilm(Film film);

    Film updateFilm(Film film);

    void deleteFilms();

    Film getFilmById(Long id);

    List<Film> getFilms();

    List<Film> getPopularMovies(int count);
}
