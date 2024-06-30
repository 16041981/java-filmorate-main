package ru.yandex.practicum.filmorate.validator;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
public class ValidatorGenre {

    public static boolean isGenreNotFound(Collection<Genre> genres, Genre genre) {
        return Objects.isNull(genre) || Objects.isNull(genres.stream()
                .collect(Collectors.toMap(Genre::getId, g -> g)).get(genre.getId()));
    }
}
