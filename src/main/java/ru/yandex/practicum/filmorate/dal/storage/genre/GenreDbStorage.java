package ru.yandex.practicum.filmorate.dal.storage.genre;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dal.mappers.GenreRowMapper;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;

@Component
@Qualifier("GenreDbStorage")
public class GenreDbStorage implements GenreStorage {

    private final JdbcTemplate jdbcTemplate;
    private final String genresSql = "select * from genres";

    public GenreDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Genre getGenreById(Integer genreId) {
        try {
            return jdbcTemplate.queryForObject(genresSql.concat(" where id = ?"), new GenreRowMapper(), genreId);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Collection<Genre> getAllGenres() {
        return jdbcTemplate.query(genresSql, new GenreRowMapper());
    }
}