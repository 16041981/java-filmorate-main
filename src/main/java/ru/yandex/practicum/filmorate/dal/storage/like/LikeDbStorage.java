package ru.yandex.practicum.filmorate.dal.storage.like;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Qualifier("LikeDbStorage")
public class LikeDbStorage implements LikeStorage {

    private final NamedParameterJdbcOperations jdbc;

    public LikeDbStorage(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public void addLike(int filmId, int userId) {
        Map<String, Object> param = Map.of("film_id", filmId, "user_id", userId);
        String sql = "MERGE INTO LIKES(film_id, user_id) VALUES ( :film_id, :user_id );";
        jdbc.update(sql, param);
    }

    @Override
    public void removeLike(int filmId, int userId) {
        Map<String, Object> param = Map.of("film_id", filmId, "user_id", userId);
        String sql = "DELETE FROM LIKES WHERE FILM_ID = :film_id AND USER_ID = :user_id;";
        jdbc.update(sql, param);
    }
}