package ru.yandex.practicum.filmorate.dal.storage.genre;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dal.extractor.GenreExtractor;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@Qualifier("GenreDbStorage")
public class GenreDbStorage implements GenreStorage {

    private final NamedParameterJdbcOperations jdbc;

    public GenreDbStorage(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }


    @Override
    public Optional<Genre> getGenreById(int genreId) {
        String sql = "SELECT GENRE_ID, NAME FROM GENRES WHERE GENRE_ID = :genre_id ORDER BY ENRE_ID DESC";
        Map<String, Object> param = Map.of("genre_id", genreId);
        return Optional.ofNullable(jdbc.query(sql, param, new GenreExtractor()));
    }

    @Override
    public List<Genre> getGenresById(List<Integer> genresId) {
        String sql = "SELECT GENRE_ID, NAME FROM GENRES WHERE GENRE_ID IN ( :genres_id )";
        Map<String, Object> param = Map.of("genres_id", genresId);
        return jdbc.query(sql, param, new GenreRowMapper());
    }

    @Override
    public List<Genre> getGenres() {
        String sql = "SELECT GENRE_ID AS ID, NAME FROM GENRES ;";
        return jdbc.query(sql, new GenreRowMapper());
    }
}