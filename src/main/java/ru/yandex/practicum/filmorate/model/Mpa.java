package ru.yandex.practicum.filmorate.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mpa {
    @NonNull
    private Integer id;
    @NonNull
    private String name;

}