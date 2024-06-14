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

    private final JdbcTemplate jdbc;
    private final GenreRowMapper mapper;
    private final String genresSql = "select * from genres";

    public GenreDbStorage(JdbcTemplate jdbc, GenreRowMapper mapper) {
        this.jdbc = jdbc;
        this.mapper = mapper;
    }

    @Override
    public Genre getGenreById(Integer genreId) {
        try {
            return jdbc.queryForObject(genresSql.concat(" where id = ?"), mapper, genreId);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Collection<Genre> getAllGenres() {
        return jdbc.query(genresSql, mapper);
    }
}
