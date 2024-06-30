package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dal.storage.genre.GenreStorage;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;

@Service
public class GenreService {

    private static final String NOT_FOUND_MESSAGE = "Жанра с id %s нет";
    private final GenreStorage genreStorage;

    public GenreService(@Qualifier("GenreDbStorage") GenreStorage genreStorage) {
        this.genreStorage = genreStorage;
    }

    public Genre getGenreById(Integer id) {
        return genreStorage.getGenreById(id).orElseThrow(() -> new ObjectNotFoundException(String.format("Жанра с id %s нет в базе", id)));
    }

    public Collection<Genre> getAllGenres() {
        return genreStorage.getGenres();
    }
}