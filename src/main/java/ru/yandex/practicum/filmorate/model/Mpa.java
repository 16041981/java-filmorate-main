package ru.yandex.practicum.filmorate.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@EqualsAndHashCode(of = "id")
public class Mpa {
    @NonNull
    private Long id;
    @NonNull
    private String name;

}
