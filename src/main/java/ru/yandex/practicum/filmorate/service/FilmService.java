package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.dal.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validator.ValidatorFilm;

import java.util.Collection;

@Slf4j
@Service
public class FilmService {

    private static final String NOT_FOUND_FILM = "фильма с id %s нет";
    private final FilmStorage filmStorage;
    private final UserService userService;
    private final LikeService likeService;
    ValidatorFilm validatorFilm = new ValidatorFilm();


    public FilmService(FilmStorage filmStorage, UserService userService, LikeService likeService) {
        this.filmStorage = filmStorage;
        this.userService = userService;
        this.likeService = likeService;
    }

    public Film createFilm(Film film) {
        validatorFilm.validate(film);
        return filmStorage.createFilm(film);
    }

    public Film updateFilm(Film film) {
        if (filmStorage.getFilmById(film.getId()) == null) {
            throw new ObjectNotFoundException("Такого фильма в коллекции не существует.");
        }
        return filmStorage.updateFilm(film);
    }

    public Collection<Film> getFilms() {
        return filmStorage.getAllFilms();
    }

    public void like(Long filmId, Long userId) {
        likeService.addLikeToFilm(filmId, userId);
    }

    public void dislike(Long filmId, Long userId) {
        User user = userService.getUserById(userId);

        likeService.deleteLikeFromFilm(filmId, user.getId());
    }

    public Collection<Film> getPopularMovies(Integer count) {
        return filmStorage.getPopularMovies(count);
    }

    public Film getFilmById(Long id) {
        Film film = filmStorage.getFilmById(id);

        //checkFilmIsNotFound(film, id);

        return film;
    }

}