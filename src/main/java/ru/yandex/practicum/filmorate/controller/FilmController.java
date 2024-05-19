package ru.yandex.practicum.filmorate.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class FilmController {

    FilmService filmService;

    @Autowired
    public FilmController(FilmStorage filmStorage, FilmService filmService) {
        this.filmService = filmService;
    }

    @PostMapping(value = "/films")
    public Film create(@Valid @RequestBody Film film) {
        log.info("Запрос создания нового фильма.");
        return filmService.createFilm(film);
    }

    @GetMapping("/films")
    public List<Film> getFilms() {
        log.info("Запрос списка всех фильмов.");
        return filmService.getFilms();
    }

    @PutMapping("/films")
    public Film update(@Valid @RequestBody Film film) {
        log.info("Запрос на обновление фильма.");
        return filmService.updateFilm(film);
    }

    @GetMapping("/films/popular")
    public List<Film> getPopularMovies(@RequestParam(defaultValue = "10") Integer count) {
        log.info("Запрос списка популярных фильмов.");
        return filmService.getPopularMovies(count);
    }

    @PutMapping("/films/{id}/like/{userId}")
    public void likeAMovie(@PathVariable Long id, @PathVariable Long userId) {
        log.info("Запрос на добовление фильма в понравившиеся.");
        filmService.like(id, userId);
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    public void removeLike(@PathVariable Long id, @PathVariable Long userId) {
        log.info("Запрос на удаление фильма из понравившихся.");
        filmService.dislike(id, userId);
    }
}
