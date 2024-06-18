package ru.yandex.practicum.filmorate.dal.storage.film;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import ru.yandex.practicum.filmorate.dal.mappers.FilmRowMapper;
import ru.yandex.practicum.filmorate.dal.storage.filmGenre.FilmGenreStorage;
import ru.yandex.practicum.filmorate.dal.storage.filmMpa.FilmMpaStorage;
import ru.yandex.practicum.filmorate.dal.storage.mpa.MpaStorage;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class FilmDbStorage implements FilmStorage {

    private final FilmRowMapper mapper;
    private final JdbcTemplate jdbc;
    private final FilmMpaStorage filmMpaStorage;
    private final MpaStorage mpaStorage;
    private final FilmGenreStorage filmGenreStorage;
    private final String filmsSql = "select f.*, m.id as mpa_id, m.name as mpa_name from films f left join film_mpas fm on f.id = fm.film_id " +
            "left join mpas m on fm.mpa_id = m.id";

    public FilmDbStorage(FilmRowMapper mapper, JdbcTemplate jdbc, FilmMpaStorage filmMpaStorage, MpaStorage mpaStorage,
                         FilmGenreStorage filmGenreStorage) {
        this.mapper = mapper;
        this.jdbc = jdbc;
        this.filmMpaStorage = filmMpaStorage;
        this.mpaStorage = mpaStorage;
        this.filmGenreStorage = filmGenreStorage;
    }

    @Override
    public Film createFilm(Film film) {

        final String sql = "insert into films (name, release_date, description, duration, rate) " +
                "values (?, ?, ?, ?, ?)";

        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        jdbc.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql,
                    new String[]{"id"});
            preparedStatement.setString(1, film.getName());
            preparedStatement.setObject(2, film.getReleaseDate());
            preparedStatement.setString(3, film.getDescription());
            preparedStatement.setLong(4, film.getDuration());
            preparedStatement.setInt(5, film.getRate());

            return preparedStatement;
        }, generatedKeyHolder);

        Integer filmId = Objects.requireNonNull(generatedKeyHolder.getKey()).intValue();

        film.setId(filmId);

        return addMpaAndGenres(film);
    }

    @Override
    public Film getFilmById(Integer filmId) {
        List<Film> films = jdbc.query(filmsSql.concat(" where f.id = ?"), mapper, filmId);

        if (!films.isEmpty()) {
            Collection<Genre> filmGenres = filmGenreStorage.getAllFilmGenresById(filmId);

            return films.get(0).toBuilder().genres(filmGenres).build();
        }

        return null;
    }

    @Override
    public Collection<Film> getAllFilms() {
        Collection<Film> films = jdbc.query(filmsSql, mapper);

        return setFilmGenres(films);
    }

    @Override
    public Film updateFilm(Film film) {
        final String sql = "update films set name = ?, release_date = ?, description = ?, duration = ?, " +
                "rate = ? where id = ?";

        jdbc.update(sql, film.getName(), film.getReleaseDate(), film.getDescription(),
                film.getDuration(), film.getRate(), film.getId());

        filmMpaStorage.deleteFilmMpaById(film.getId());
        filmGenreStorage.deleteAllFilmGenresById(film.getId());

        return addMpaAndGenres(film);
    }

    @Override
    public Collection<Film> getPopularMovies(Integer count) {
        final String sql = "select f.*, m.id as mpa_id, m.name as mpa_name from films f left join likes l on f.id = l.film_id " +
                "left join film_mpas fm on f.id = fm.film_id " +
                "left join mpas m on fm.mpa_id = m.id group by f.name, f.id " +
                "order by count(l.film_id) desc limit ?";
        Collection<Film> films = jdbc.query(sql, mapper, count);

        return setFilmGenres(films);
    }

    private Collection<Film> setFilmGenres(Collection<Film> films) {
        Map<Integer, Collection<Genre>> filmGenresMap = filmGenreStorage.getAllFilmGenres(films);

        return films.stream().peek(film -> {
            if (Objects.nonNull(filmGenresMap.get(film.getId()))) {
                film.setGenres(filmGenresMap.get(film.getId()));
            }
        }).collect(Collectors.toList());
    }

    private Film addMpaAndGenres(Film film) {
        Integer filmId = film.getId();
        Integer mpaId = film.getMpa().getId();

        filmMpaStorage.addFilmMpa(filmId, mpaId);
        List<Genre> genres = (List<Genre>) film.getGenres();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        jdbc.batchUpdate("insert into film_genres (film_id, genre_id) values (?, ?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                        preparedStatement.setInt(1, film.getId());
                        preparedStatement.setInt(2, genres.get(i).getId());
                    }

                    @Override
                    public int getBatchSize() {
                        return genres.size();
                    }
                });
        stopWatch.stop();

        Mpa filmMpa = mpaStorage.getMpaById(mpaId);
        Collection<Genre> filmGenres = filmGenreStorage.getAllFilmGenresById(filmId);

        return film.toBuilder().id(filmId).mpa(filmMpa).genres(filmGenres).build();
    }
}