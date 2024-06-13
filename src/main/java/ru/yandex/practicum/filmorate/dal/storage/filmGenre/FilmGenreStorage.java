package ru.yandex.practicum.filmorate.dal.storage.filmGenre;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;
import java.util.Map;

public interface FilmGenreStorage {
    void addFilmGenre(long filmId, long genreId);

    Collection<Genre> getAllFilmGenresById(long filmId);

    void deleteAllFilmGenresById(long filmId);

    Map<Integer, Collection<Genre>> getAllFilmGenres(Collection<Film> films);
}
