package ru.yandex.practicum.filmorate.dal.storage.genre;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreStorage {
    Optional<Genre> getGenreById(int genreId);

    List<Genre> getGenresById(List<Integer> genreIds);

    List<Genre> getGenreByIdDesc(List<Integer> genreIds);

    List<Genre> getGenres();
}