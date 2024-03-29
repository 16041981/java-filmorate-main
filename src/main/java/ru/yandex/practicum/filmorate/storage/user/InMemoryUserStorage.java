package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {

    private final Map<Long, User> users;
    private Long id;

    public InMemoryUserStorage() {
        users = new HashMap<>();
        id = 0L;
    }

    @Override
    public User createUser(User user) {
        validate(user);
        users.put(user.getId(), user);
        log.info("Создан ползователь с id: {}.",  user.getId());
        return user;
    }

    @Override
    public List<User> getUsers() {
        log.info("Список пользователей получен");
        return new ArrayList<>(users.values());
    }

    @Override
    public User updateUser(User user) {
        if (users.containsKey(user.getId())) {
            validate(user);
            users.put(user.getId(), user);
            log.info("Информация о пльзователе {} обновлена.", user.getId());
            return user;
        } else {
            throw new ObjectNotFoundException("Пользователя с id: " + user.getId() + " нет.");
        }
    }

    @Override
    public void deleteUsers() {
        users.clear();
        log.info("Список пользователей очищен.");
    }

    @Override
    public User getUserById(Long id) {
        if (!users.containsKey(id)) {
            throw new ObjectNotFoundException("Пользователя с id: " + id + " нет.");
        }
        return users.get(id);
    }

    private void validate(User user) {
        if (user.getId() == null || user.getId() <= 0) {
            user.setId(++id);
            log.info("Некорректно указан id.");
        }
        if (user.getName() == null) {
            user.setName(user.getLogin());
            log.info("Имя для отображения может быть пустым — в таком случае будет использован логин.");
        }
        if (user.getFriends() == null) {
            user.setFriends(new HashSet<>());
        }
    }
}
