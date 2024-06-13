package ru.yandex.practicum.filmorate.dal.storage.like;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@Qualifier("LikeDbStorage")
public class LikeDbStorage implements LikeStorage {

    private final JdbcTemplate jdbc;

    public LikeDbStorage(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public void addLikeToFilm(Long filmId, Long userId) {
        final String sql = "insert into likes (film_id, user_id) values (?, ?)";

        jdbc.update(sql, filmId, userId);
    }

    @Override
    public void deleteLikeFromFilm(Long filmId, Long userId) {
        final String sql = "delete from likes where film_id = ? and user_id = ?";

        jdbc.update(sql, filmId, userId);
    }
}