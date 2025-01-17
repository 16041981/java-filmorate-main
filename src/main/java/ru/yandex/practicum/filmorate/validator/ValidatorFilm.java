package ru.yandex.practicum.filmorate.validator;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.HashSet;

@Slf4j
public class ValidatorFilm {

    private  Long id;
    private static final LocalDate birthdayFilm = LocalDate.of(1895, 12, 28);

    public ValidatorFilm() {
        id = 0L;
    }

    public void validate(Film film) {
        if (film.getReleaseDate() == null ||
                film.getReleaseDate().isBefore(birthdayFilm)) {
            throw new ValidationException("Дата релиза — не раньше 28 декабря 1895 года.");
        }
        if (film.getId() == null || film.getId() <= 0) {
            film.setId(++id);
            log.info("Присвоен id '{}", film.getId());
        }
        if (film.getLikes() == null) {
            film.setLikes(new HashSet<>());
        }
    }
}
