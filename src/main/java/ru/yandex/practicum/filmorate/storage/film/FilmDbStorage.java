package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public class FilmDbStorage implements FilmStorage{
    @Override
    public Film createFilm(Film film) {
        return null;
    }

    @Override
    public Film updateFilm(Film film) {
        return null;
    }

    @Override
    public void deleteFilms() {

    }

    @Override
    public Film getFilmById(Long id) {
        return null;
    }

    @Override
    public List<Film> getFilms() {
        return List.of();
    }

    @Override
    public List<Film> getPopularMovies(int count) {
        return List.of();
    }
}