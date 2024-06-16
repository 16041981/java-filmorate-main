package ru.yandex.practicum.filmorate.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dal.storage.friendship.FriendshipStorage;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.dal.storage.user.UserStorage;
import ru.yandex.practicum.filmorate.validator.ValidatorUser;

import java.util.*;

@Getter
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserStorage userStorage;
    private final FriendshipStorage friendshipStorage;

    ValidatorUser validatorUser = new ValidatorUser();


    public void addFriend(Integer userId, Integer friendId) {
        User user = userStorage.getUserById(userId);
        if (user == null || userStorage.getUserById(friendId) == null) {
            throw new ObjectNotFoundException("Пользователя с id: " + userId + " нет.");
        }
        friendshipStorage.addFriend(userId, getUserById(friendId).getId());
        log.info("'{}' добавлен '{}' в список друзей.", userId, friendId);
    }

    public void deleteFriend(Integer userId, Integer friendId) {
        User user = userStorage.getUserById(userId);
        User user2 = userStorage.getUserById(friendId);
        if (user == null || user2 == null) {
            throw new ObjectNotFoundException("Пользователя с id: " + userId + " нет.");
        }
        friendshipStorage.deleteFriend(userId, friendId);
        log.info("'{}' удален '{}' из списка друзей.", userId, friendId);

    }

    public Collection<User> getCommonFriends(Integer userId, Integer friendId) {
        User user = userStorage.getUserById(userId);
        if (user == null) {
            throw new ObjectNotFoundException("Пользователя с id: " + userId + " нет.");
        }
        log.info("'{}' запрошенный список общих друзей у пользователя '{}'.", userId, friendId);
        return userStorage.getCommonFriends(userId, friendId);
    }

    public Collection<User> getFriends(Integer userId) {
        User user = userStorage.getUserById(userId);
        if (user == null) {
            throw new ObjectNotFoundException("Пользователя с id: " + userId + " нет.");
        }
        return userStorage.getUserFriends(userId);
    }

    public User createUser(User user) {
        validatorUser.validate(user);
        return userStorage.createUser(user);
    }

    public Collection<User> getUsers() {
        return userStorage.getUsers();
    }

    public User updateUser(User user) {
        User user1 = userStorage.getUserById((user.getId()));
        if (user1 != null) {
            log.info("Информация о пльзователе {} обновлена.", user.getId());
            validatorUser.validate(user);
            return userStorage.updateUser(user);
        } else {
            throw new ObjectNotFoundException("Пользователя с id: " + user.getId() + " нет.");
        }

    }

    public User getUserById(Integer id) {
        return userStorage.getUserById(id);
    }
}