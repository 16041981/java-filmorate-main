package ru.yandex.practicum.filmorate.validator;

import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class ValidatorReleaseDate {

    public boolean isValid(LocalDate date, ConstraintValidatorContext constraintValidatorContext) {
        LocalDate firstFilmDate = LocalDate.parse("1895-12-28");

        return date.isEqual(firstFilmDate) || date.isAfter(firstFilmDate);
    }
}