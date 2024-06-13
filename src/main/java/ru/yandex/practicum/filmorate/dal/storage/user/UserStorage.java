package ru.yandex.practicum.filmorate.dal.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {

    User createUser(User user);

    List<User> getUsers();

    User updateUser(User user);

    void deleteUsers();

    User getUserById(Long id);
}
