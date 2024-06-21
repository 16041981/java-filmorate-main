package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dal.storage.genre.GenreStorage;
import ru.yandex.practicum.filmorate.dal.storage.like.LikeStorage;
import ru.yandex.practicum.filmorate.dal.storage.mpa.MpaStorage;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.dal.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FilmService {

    private final FilmStorage filmStorage;
    private final MpaStorage mpaStorage;
    private final LikeStorage likeStorage;
    private final GenreStorage genreStorage;


    public FilmService(FilmStorage filmStorage, MpaStorage mpaStorage,
                       LikeStorage likeStorage, GenreStorage genreStorage) {
        this.filmStorage = filmStorage;
        this.mpaStorage = mpaStorage;
        this.likeStorage = likeStorage;
        this.genreStorage = genreStorage;
    }

    public Film createFilm(Film film) {
        if (film.getMpa() != null) {
            film.setMpa(
                    mpaStorage.getRatingById(film.getMpa().getId()).orElseThrow(() ->
                            new ValidationException("Рейтинга нет в базе"))
            );
        }

        if (!film.getGenres().isEmpty()) {
            Set<Genre> genres =
                    Set.copyOf(genreStorage.getGenresById(
                            film.getGenres().stream().map(Genre::getId).collect(Collectors.toList())));
            if (genres.size() != film.getGenres().size()) {
                throw new ValidationException("Одного из жанров нет в базе");
            }

            film.setGenres(genres);
        }


        return filmStorage.save(film);
    }

    public Collection<Film> getFilms() {
        return filmStorage.getAll();
    }

    public void addLikeToFilm(Integer filmId, Integer userId) {
        likeStorage.addLike(filmId, userId);
    }

    public void deleteLikeFromFilm(Integer filmId, Integer userId) {
        likeStorage.removeLike(filmId, userId);
    }

    public Collection<Film> getPopularFilms(Integer count) {
        return filmStorage.getTopPopular(count);
    }

    public Film put(Film film) {
        filmStorage.getById(film.getId())
                .orElseThrow(
                        () -> new ObjectNotFoundException(String.format("Фильма с id %s нет в базе", film.getId()))
                );

        if (film.getMpa() != null) {
            film.setMpa(
                    mpaStorage.getRatingById(film.getMpa().getId()).orElseThrow(() -> new ValidationException("Рейтинга нет в базе"))
            );
        }

        if (!film.getGenres().isEmpty()) {
            Set<Genre> genres = Set.copyOf(genreStorage.getGenresById(
                    film.getGenres().stream().map(Genre::getId).collect(Collectors.toList())));
            if (genres.size() != film.getGenres().size()) {
                throw new ValidationException("Одного из жанров нет в базе");
            }
            film.setGenres(genres);
        }
        filmStorage.update(film);
        return film;
    }

    public Film getFilmById(Film film) {
        filmStorage.getById(film.getId())
                .orElseThrow(
                        () -> new ObjectNotFoundException(String.format("Фильма с id %s нет в базе", film.getId()))
                );

        if (film.getMpa() != null) {
            film.setMpa(
                    mpaStorage.getRatingById(film.getMpa().getId()).orElseThrow(() ->
                            new ValidationException("Рейтинга нет в базе"))
            );
        }

        if (!film.getGenres().isEmpty()) {
            Set<Genre> genres =
                    Set.copyOf(genreStorage.getGenresById(film.getGenres().stream().map(Genre::getId).collect(Collectors.toList())));
            if (genres.size() != film.getGenres().size()) {
                throw new ValidationException("Одного из жанров нет в базе");
            }
            film.setGenres(genres);
        }
        filmStorage.update(film);
        return film;
    }

    public Film get(int id) {
        return filmStorage.getById(id)
                .orElseThrow(
                        () -> new ObjectNotFoundException(String.format("Фильма с id %s нет в базе", id))
                );
    }
}
