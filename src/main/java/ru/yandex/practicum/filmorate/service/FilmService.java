package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.validator.ValidatorFilm;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserService userService;
    ValidatorFilm validatorFilm = new ValidatorFilm();

    public void like(Long filmId, Long userId) {
        Film film = filmStorage.getFilmById(filmId);
        userService.getUserStorage().getUserById(userId);
        if (film == null) {
            throw new ObjectNotFoundException("Попытка доступа к несуществующему фильму с идентификатором '" + filmId + "'.");
        }
        film.addLike(userId);
        log.info("'{}' понравился фильм '{}'.", userId, filmId);
    }

    public void dislike(Long filmId, Long userId) {
        Film film = filmStorage.getFilmById(filmId);
        userService.getUserStorage().getUserById(userId);
        if (film == null) {
            throw new ObjectNotFoundException("Попытка доступа к несуществующему фильму с идентификатором '" + filmId + "'.");
        }
        film.removeLike(userId);
        log.info("'{}' не понравился фильм '{}'.", userId, filmId);
    }

    public List<Film> getPopularMovies(int count) {
        return filmStorage.getPopularMovies(count);
    }

    public Film createFilm(Film film) {
        validatorFilm.validate(film);
        return filmStorage.createFilm(film);
    }

    public List<Film> getFilms() {
        return filmStorage.getFilms();
    }

    public Film updateFilm(Film film) {
        validatorFilm.validate(film);
        return filmStorage.updateFilm(film);
    }
}