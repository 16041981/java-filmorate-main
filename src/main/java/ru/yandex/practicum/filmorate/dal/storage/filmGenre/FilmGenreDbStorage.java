package ru.yandex.practicum.filmorate.dal.storage.filmGenre;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dal.mappers.GenreRowMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Qualifier("FilmGenreDbStorage")
public class FilmGenreDbStorage implements FilmGenreStorage {

    private final JdbcTemplate jdbc;
    private final GenreRowMapper mapper;

    public FilmGenreDbStorage(JdbcTemplate jdbc, GenreRowMapper mapper) {
        this.jdbc = jdbc;
        this.mapper = mapper;
    }

    @Override
    public void addFilmGenre(long filmId, long genreId) {
        final String sql = "insert into film_genres (film_id, genre_id) values (?, ?)";

        jdbc.update(sql, filmId, genreId);
    }

    @Override
    public Collection<Genre> getAllFilmGenresById(long filmId) {
        final String sql = "select g.id as id, name from film_genres fg left join genres g on " +
                "fg.genre_id = g.id where film_id = ?";

        return jdbc.query(sql, mapper, filmId);
    }

    @Override
    public void deleteAllFilmGenresById(long filmId) {
        final String sql = "delete from film_genres where film_id = ?";

        jdbc.update(sql, filmId);
    }

    @Override
    public Map<Integer, Collection<Genre>> getAllFilmGenres(Collection<Film> films) {
        final String sql = "select fg.film_id as film_id, g.id as genre_id, g.name as name from film_genres fg " +
                "left join genres g on fg.genre_id = g.id where fg.film_id in (%s)";

        Map<Integer, Collection<Genre>> filmGenresMap = new HashMap<>();
        Collection<String> ids = films.stream().map(film -> String.valueOf(film.getId())).collect(Collectors.toList());

        jdbc.query(String.format(sql, String.join(",", ids)), (rs) -> {
            Genre genre = new Genre(rs.getLong("genre_id"), rs.getString("name"));

            if (!filmGenresMap.containsKey(rs.getInt("film_id"))) {
                filmGenresMap.put(rs.getInt("film_id"), new ArrayList<>());
            }

            filmGenresMap.get(rs.getInt("film_id")).add(genre);
        });

        return filmGenresMap;
    }
}
