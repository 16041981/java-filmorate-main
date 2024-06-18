package ru.yandex.practicum.filmorate.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Genre {
    @NonNull
    private Integer id;
    @NonNull
    private String name;
}