package ru.yandex.practicum.filmorate.dal.storage.user;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

@Component
@Qualifier("FilmDbStorage")
public class UserDbStorage implements UserStorage {
    @Override
    public User createUser(User user) {
        return null;
    }

    @Override
    public List<User> getUsers() {
        return List.of();
    }

    @Override
    public User updateUser(User user) {
        return null;
    }

    @Override
    public void deleteUsers() {

    }

    @Override
    public User getUserById(Long id) {
        return null;
    }
}
