package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@Repository
@RequiredArgsConstructor
public class JdbcFilmRepository implements FilmRepository {
    private final NamedParameterJdbcOperations jdbcOperations;
    @Override
    public Optional<Film> getById(long id) {
        return Optional.empty();
    }

    @Override
    public Film save(Film film) {
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        Map<String, Object> parans = new HashMap<>();
        jdbcOperations.update("INSERT INTO FILMS () VALUES (:, :,)",
                parans, generatedKeyHolder, new String[]{"film_id"});
        film.setId(generatedKeyHolder.getKeyAs(Long.class));
        return film;
    }

    @Override
    public void update(Film film) {

    }

    @Override
    public List<Film> getAll() {
        return List.of();
    }

    @Override
    public void addLike(long filmId, long userId) {

    }

    @Override
    public void deleteLike(long filmId, long userId) {

    }

    @Override
    public List<Film> getTopPopular(int count) {
        return List.of();
    }
}
