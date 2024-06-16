package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.Collection;


@RestController
@Slf4j
@RequestMapping("/films")
public class FilmController {

    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @PostMapping
    public Film createFilm(@Valid @RequestBody Film film) {
        log.info("Запрос создания нового фильма.");
        return filmService.createFilm(film);
    }

    @GetMapping
    public Collection<Film> getFilms() {
        log.info("Запрос списка всех фильмов.");
        return filmService.getFilms();
    }


    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable("id") Long id) {
        return filmService.getFilmById(id);
    }

    @GetMapping("/popular")
    public Collection<Film> getPopularFilms(@RequestParam(name = "count",
            defaultValue = "10", required = false) Integer count) {
        log.info("Запрос списка популярных фильмов.");
        return filmService.getPopularMovies(count);
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLikeToFilm(@PathVariable("id") Integer id, @PathVariable("userId") Integer userId) {
        log.info("Запрос на добовление фильма в понравившиеся.");
        filmService.like(id, userId);
    }


    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        log.info("Запрос на обновление фильма.");
        return filmService.updateFilm(film);
    }


    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLikeFromFilm(@PathVariable("id") Integer id, @PathVariable("userId") Integer userId) {
        log.info("Запрос на удаление фильма из понравившихся.");
        filmService.dislike(id, userId);
    }
}
//@RestController
//@Slf4j
//@RequestMapping
//public class FilmController {
//
//    FilmService filmService;
//
//    @Autowired
//    public FilmController(FilmService filmService) {
//        this.filmService = filmService;
//    }
//
//    @PostMapping(value = "/films")
//    public Film create(@Valid @RequestBody Film film) {
//        log.info("Запрос создания нового фильма.");
//        return filmService.createFilm(film);
//    }
//
//    @GetMapping("/films")
//    public Collection<Film> getFilms() {
//        log.info("Запрос списка всех фильмов.");
//        return filmService.getFilms();
//    }
//
//    @PutMapping("/films")
//    public Film update(@Valid @RequestBody Film film) {
//        log.info("Запрос на обновление фильма.");
//        return filmService.updateFilm(film);
//    }
//
//    @GetMapping("/films/popular")
//    public Collection<Film> getPopularMovies(@RequestParam(defaultValue = "10") Integer count) {
//        log.info("Запрос списка популярных фильмов.");
//        return filmService.getPopularMovies(count);
//    }
//
//    @PutMapping("/films/{id}/like/{userId}")
//    public void likeAMovie(@PathVariable Long id, @PathVariable Long userId) {
//        log.info("Запрос на добовление фильма в понравившиеся.");
//        filmService.like(id, userId);
//    }
//
//    @DeleteMapping("/films/{id}/like/{userId}")
//    public void removeLike(@PathVariable Long id, @PathVariable Long userId) {
//        log.info("Запрос на удаление фильма из понравившихся.");
//        filmService.dislike(id, userId);
//    }
//}