package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dal.storage.friendship.FriendshipStorage;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.dal.storage.user.UserStorage;
import ru.yandex.practicum.filmorate.validator.ValidatorUser;

import java.util.*;

@Slf4j
@Service
public class UserService {

    private static final String NOT_FOUND_MESSAGE = "пользователя с id %s нет";
    private final UserStorage userStorage;
    private final FriendshipStorage friendshipStorage;

    public UserService(@Qualifier("UserDbStorage") UserStorage userStorage,
                       @Qualifier("FriendshipDbStorage") FriendshipStorage friendshipStorage) {
        this.userStorage = userStorage;
        this.friendshipStorage = friendshipStorage;
    }

    public User createUser(User user) {
        setUserName(user);
        return userStorage.createUser(user);
    }

    public Collection<User> getAllUsers() {
        return userStorage.getAllUsers();
    }

    public User getUserById(Integer id) {
        User user = userStorage.getUserById(id);

        checkUserIsNotFound(user, id);

        return user;
    }

    public Collection<User> getUserFriends(Integer userId) {
        User user = userStorage.getUserById(userId);
        if (user == null) {
            throw new ObjectNotFoundException("Пользователя с id: " + userId + " нет.");
        }
        return userStorage.getUserFriends(userId);
    }

    public Collection<User> getCommonFriends(Integer userId, Integer friendId) {
        User user = userStorage.getUserById(userId);
        if (user == null) {
            throw new ObjectNotFoundException("Пользователя с id: " + userId + " нет.");
        }
        log.info("'{}' запрошенный список общих друзей у пользователя '{}'.", userId, friendId);
        return userStorage.getCommonFriends(userId, friendId);
    }

    public User updateUser(User user) {
        User user1 = userStorage.getUserById((user.getId()));
        if (user1 != null) {
            log.info("Информация о пльзователе {} обновлена.", user.getId());
            setUserName(user);
            return userStorage.updateUser(user);
        } else {
            throw new ObjectNotFoundException("Пользователя с id: " + user.getId() + " нет.");
        }

    }

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

    private void checkUserIsNotFound(User user, Integer id) {
        if (ValidatorUser.isUserNotFound(user)) {
            throw new ObjectNotFoundException(String.format(NOT_FOUND_MESSAGE, id));
        }
    }

    private void setUserName(User user) {
        if (!ValidatorUser.isUserNameValid(user.getName())) {
            user.setName(user.getLogin());
        }
    }
}





