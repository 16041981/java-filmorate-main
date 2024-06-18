package ru.yandex.practicum.filmorate.dal.storage.like;

public interface LikeStorage {

    void addLikeToFilm(Integer filmId, Integer userId);

    void deleteLikeFromFilm(Integer filmId, Integer userId);
}