package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Long, Film> films;
    private Long id;
    private final LocalDate birthdayFilm = LocalDate.of(1895, 12, 28);

    public InMemoryFilmStorage() {
        films = new HashMap<>();
        id = 0L;
    }

    @Override
    public Film createFilm(Film film) {
        validate(film);
        films.put(film.getId(), film);
        log.info("Фильм {} добавлен в коллекцию.",film.getName());
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        if (films.containsKey(film.getId())) {
            validate(film);
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

    private void validate(Film film) {
        if (film.getReleaseDate() == null ||
                film.getReleaseDate().isBefore(birthdayFilm)) {
            throw new ValidationException("Дата релиза — не раньше 28 декабря 1895 года.");
        }
        if (film.getId() == null || film.getId() <= 0) {
            film.setId(++id);
            log.info("Присвоен id '{}", film.getId());
        }
        if (film.getLikes() == null) {
            film.setLikes(new HashSet<>());
        }
    }

}
