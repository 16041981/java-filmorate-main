package ru.yandex.practicum.filmorate.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Mpa {
    private Long id;
    private String name;

}
