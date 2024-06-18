package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.dal.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.model.Genre;
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
    private final GenreService genreService;

    ValidatorFilm validatorFilm = new ValidatorFilm();

    public FilmService(FilmStorage filmStorage, UserService userService, LikeService likeService, GenreService genreService) {
        this.filmStorage = filmStorage;
        this.userService = userService;
        this.likeService = likeService;
        this.genreService = genreService;
    }

    public Film createFilm(Film film) {
        validatorFilm.validate(film);
        if (film.getMpa().getId() > 5) {
            throw new ValidationException("Такого Mpa нет.");
        }
        Collection<Genre> genres = film.getGenres();
        if (genres != null) {
            for (Genre genre : genres) {
                if (genre.getId() > genreService.getAllGenres().size()) {
                    throw new ValidationException("Такого Genre нет.");
                }
            }
        }
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

    public void addLikeToFilm(Integer filmId, Integer userId) {
        likeService.addLikeToFilm(filmId, userId);
    }

    public void deleteLikeFromFilm(Integer filmId, Integer userId) {
        User user = userService.getUserById(userId);

        likeService.deleteLikeFromFilm(filmId, user.getId());
    }

    public Collection<Film> getPopularFilms(Integer count) {
        return filmStorage.getPopularFilms(count);
    }

    public Film getFilmById(Integer id) {
        Collection<Film> films = filmStorage.getAllFilms();
        for (Film film : films) {
           if (film.getId() == id) {
               throw new ValidationException("фильм с таким id уже существует");
           }
        }

        Film film = filmStorage.getFilmById(id);

        return film;
    }
}
