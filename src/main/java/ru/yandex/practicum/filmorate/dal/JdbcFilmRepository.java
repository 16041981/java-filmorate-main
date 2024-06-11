package ru.yandex.practicum.filmorate.dal;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dal.mappers.FilmRowMapper;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class JdbcFilmRepository implements FilmRepository {

    private final JdbcOperations jdbc;
    private final FilmRowMapper mapper;
    //https://app.pachca.com/chats/7554140?thread_message_id=255974297
    //https://github.com/Chernosmaga/java-filmorate/blob/add-database/src/main/java/ru/yandex/practicum/filmorate/storage/mapper/FriendshipMapper.java
    //https://github.com/kirpinev/java-filmorate/blob/database-update/src/main/java/ru/yandex/practicum/filmorate/storage/film/FilmDbStorage.java

    //private final NamedParameterJdbcOperations jdbcOperations;

    @Override
    public Optional<Film> getById(long id) {
        String filmsSql =
                "select f.*, m.id as mpa_id, m.name as mpa_name from films f left join film_mpas fm on f.id = fm.film_id " +
                "left join mpas m on fm.mpa_id = m.id";
        List<Film> films = jdbc.query(filmsSql.concat(" where f.id = ?"), mapper, id);

        if (!films.isEmpty()) {
//            Collection<Genre> filmGenres = filmGenreStorage.getAllFilmGenresById(filmId);
//
//            return films.get(0).toBuilder().genres(filmGenres).build();
        }

        return null;
    }

    @Override
    public Film save(Film film) {
//        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
//        Map<String, Object> params = new HashMap<>();
//        jdbcOperations.update("INSERT INTO FILMS () VALUES (:, :,)",
//                params, generatedKeyHolder, new String[]{"film_id"});
//        film.setId(generatedKeyHolder.getKeyAs(Long.class));
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
