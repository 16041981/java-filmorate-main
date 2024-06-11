package ru.yandex.practicum.filmorate.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


import java.time.LocalDate;

@Data
public class FilmDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long id;
    private String name;
    private String description;
    private long duration;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDate releaseDate;

//    private Set<Genre> genres;
//    private МРА mpa;

//    private Long id;
//    private String name;
//    private String description;
//    private LocalDate releaseDate;
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
