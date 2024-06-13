package ru.yandex.practicum.filmorate.model;


import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;


@EqualsAndHashCode(of = "id")
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Film {

    private Collection<Genre> genres = new ArrayList<>();
    private Mpa mpa;
    @PositiveOrZero
    private Long id;
    @NotBlank
    private String name;
    @NotNull
    @Size(min = 1, max = 200)
    private String description;
    private LocalDate releaseDate;
    @Positive
    private long duration;
    private int rate;
    private Set<Long> likes;
}