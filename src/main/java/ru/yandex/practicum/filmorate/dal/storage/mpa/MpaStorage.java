package ru.yandex.practicum.filmorate.dal.storage.mpa;

import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.Collection;

public interface MpaStorage {
    Mpa getMpaById(Long mpaId);

    Collection<Mpa> getAllMpa();
}