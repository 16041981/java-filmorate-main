package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.dal.storage.user.UserStorage;

import java.util.*;

@Slf4j
@Service
public class UserService {

    private final UserStorage userStorage;

    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User createUser(User user) {
        if (user.getName() == null) {
            user.setName(user.getLogin());
        }
        return userStorage.save(user);
    }

    public Collection<User> getAllUsers() {
        return userStorage.getAll();
    }

    public User getUserById(Integer id) {
        try {
            return userStorage.getById(id).orElseThrow();
        } catch (EmptyResultDataAccessException e) {
            throw new ObjectNotFoundException(String.format("Пользователя с id %s нет в базе", id));
        }
    }

    public Collection<User> getUserFriends(Integer userId) {
        userStorage.getById(userId).orElseThrow(() ->
                new ObjectNotFoundException(String.format("Пользователя с id %s нет в базе", userId)));
        return userStorage.getFriends(userId);
    }

    public Collection<User> getCommonFriends(Integer userId, Integer friendId) {
        userStorage.getById(userId).orElseThrow(() ->
                new ObjectNotFoundException(String.format("Пользователя с id %s нет в базе", userId)));
        userStorage.getById(friendId).orElseThrow(() ->
                new ObjectNotFoundException(String.format("Пользователя с id %s нет в базе", friendId)));

        return userStorage.getMutualFriends(userId, friendId);
    }

    public User updateUser(User user) {
        if (user.getName() == null) {
            user.setName(user.getLogin());
        }
        userStorage.getById(user.getId()).orElseThrow(() ->
                new ObjectNotFoundException(String.format("Пользователя id %s нет в базе", user.getId())));
        userStorage.update(user);
        return user;

    }

    public User addFriend(Integer userId, Integer friendId) {
        User user = userStorage.getById(userId).orElseThrow(() ->
                new ObjectNotFoundException(String.format("Пользователя с id %s нет в базе", userId)));
        userStorage.getById(friendId).orElseThrow(() ->
                new ObjectNotFoundException(String.format("Пользователя с id %s нет в базе", friendId)));

        userStorage.addFriend(userId, friendId);
        return userStorage.getById(userId).orElseThrow();
    }


    public void deleteFriend(Integer userId, Integer friendId) {
        userStorage.getById(userId).orElseThrow(() ->
                new ObjectNotFoundException(String.format("Пользователя с id %s нет в базе", userId)));
        userStorage.getById(friendId).orElseThrow(() ->
                new ObjectNotFoundException(String.format("Пользователя с id %s нет в базе", friendId)));
        userStorage.deleteFriend(userId, friendId);
    }

}





