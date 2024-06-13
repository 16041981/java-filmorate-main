package ru.yandex.practicum.filmorate.dal.storage.filmMpa;

import ru.yandex.practicum.filmorate.model.Mpa;

public interface FilmMpaStorage {

    void addFilmMpa(long filmId, long mpaId);

    Mpa getFilmMpaById(long filmId);

    void deleteFilmMpaById(long filmId);
}
