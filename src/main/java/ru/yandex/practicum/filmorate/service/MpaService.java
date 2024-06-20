package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dal.storage.mpa.MpaStorage;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.Collection;

@Service
public class MpaService {
    private final MpaStorage mpaStorage;

    public MpaService(MpaStorage mpaStorage) {
        this.mpaStorage = mpaStorage;
    }

    public Mpa getMapById(Integer id) {
        return mpaStorage.getRatingById(id).orElseThrow(() ->
                new ObjectNotFoundException(String.format("Рейтинга с id %s нет в базе", id)));
    }

    public Collection<Mpa> getAllMpa() {
        return mpaStorage.getRatings();
    }


}