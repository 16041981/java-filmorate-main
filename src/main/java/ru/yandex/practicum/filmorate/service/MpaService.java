package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dal.storage.mpa.MpaStorage;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.validator.ValidatorMpa;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class MpaService {

    private static final String NOT_FOUND_MESSAGE = "MPA рейтинга с id %s нет";
    private final MpaStorage mpaStorage;

    public Mpa getMapById(Long id) {

        Mpa mpa = mpaStorage.getMpaById(id);

        checkMpaIsNotNull(mpa, id);

        return mpa;
    }

    public Collection<Mpa> getAllMpa() {
        return mpaStorage.getAllMpa();
    }

    private void checkMpaIsNotNull(Mpa mpa, Long id) {
        if (ValidatorMpa.isMpaNotFound(getAllMpa(), mpa)) {
            throw new ObjectNotFoundException(String.format(NOT_FOUND_MESSAGE, id));
        }
    }
}
