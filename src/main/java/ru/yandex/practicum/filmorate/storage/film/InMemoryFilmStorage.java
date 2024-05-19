package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Long, Film> films;

    public InMemoryFilmStorage() {
        films = new HashMap<>();
    }

    @Override
    public Film createFilm(Film film) {
        films.put(film.getId(), film);
        log.info("Фильм {} добавлен в коллекцию.",film.getName());
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
            log.info("Информация о фильме {} обновлена.", film.getName());
            return film;
        } else {
            throw new ObjectNotFoundException("Такого фильма в коллекции не существует.");
        }
    }

    @Override
    public void deleteFilms() {
        films.clear();
        log.info("Список фильмов очищен.");
    }

    @Override
    public Film getFilmById(Long id) {
        if (!films.containsKey(id)) {
            throw new ObjectNotFoundException("Такого фильма в коллекции не существует.");
        }
        return films.get(id);
    }

    @Override
    public List<Film> getFilms() {
        log.info("Список фильмов получен");
        return new ArrayList<>(films.values());
    }

    @Override
    public List<Film> getPopularMovies(int count) {
        log.info("Получите список понравившихся фильмов");
        return getFilms().stream()
                .sorted(Comparator.comparingInt(Film::getLikesQuantity).reversed())
                .limit(count).collect(Collectors.toList());
    }
}
