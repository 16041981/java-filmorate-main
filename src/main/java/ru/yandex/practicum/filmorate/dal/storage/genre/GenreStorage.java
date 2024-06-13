package ru.yandex.practicum.filmorate.dal.storage.genre;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;

public interface GenreStorage {
    Genre getGenreById(Integer genreId);

    Collection<Genre> getAllGenres();
}
