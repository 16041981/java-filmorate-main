package ru.yandex.practicum.filmorate.model;


import lombok.*;
import ru.yandex.practicum.filmorate.validator.ValidatorReleaseDate;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

@Data
public class Film {

    private Integer id;
    @NotBlank(message = "Передана пустая строка или пробелы")
    private final String name;
    @NotBlank(message = "Передано пустое значение описания фильма")
    @Size(max = 200, message = "Количество символов в описании не валидно")
    private final String description;
    @NotNull(message = "Передано пустое значение даты релиза фильма")
    @ValidatorReleaseDate
    private final LocalDate releaseDate;
    @NotNull(message = "Передано пустое значение длительности фильма")
    @Positive(message = "Передано отрицательное значение длительности фильма")
    private final Integer duration;
    private Mpa mpa;
    private Set<Genre> genres = new TreeSet<>(Comparator.comparingInt(Genre::getId));
}