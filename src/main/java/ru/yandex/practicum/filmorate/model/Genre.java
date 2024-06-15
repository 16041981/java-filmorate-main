package ru.yandex.practicum.filmorate.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Genre {
    @NonNull
    private Long id;
    @NonNull
    private String name;
}
