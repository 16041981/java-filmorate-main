package ru.yandex.practicum.filmorate.dal.storage.mpa;

import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MpaStorage {

    List<Mpa> getRatings();

    Optional<Mpa> getRatingById(int ratingId);
}