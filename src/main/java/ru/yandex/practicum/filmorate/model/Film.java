package ru.yandex.practicum.filmorate.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@Builder
public class Film {

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
    private Set<Long> likes;

    public void addLike(Long userId) {
        likes.add(userId);
    }

    public void removeLike(Long userId) {
        likes.remove(userId);
    }

    public int getLikesQuantity() {
        return likes.size();
    }
//    @PositiveOrZero
//    private long id;
//    @NotBlank
//    private String name;
//    @NotNull
//    @Size(min = 1, max = 200)
//    private String description;
//    private LocalDate releaseDate;
//    @Positive
//    private long duration;
//    private Set<Long> likes;
//
//    public void addLike(Long userId) {
//        likes.add(userId);
//    }
//
//    public void removeLike(Long userId) {
//        likes.remove(userId);
//    }
//
//    public int getLikesQuantity() {
//        return likes.size();
//    }
}