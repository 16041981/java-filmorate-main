package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.*;


@Data
@AllArgsConstructor(onConstructor = @__(@JsonCreator))
public class Genre {
    private final int id;
    private String name;
}