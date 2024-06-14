package ru.yandex.practicum.filmorate.dal.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserStorage {

    User createUser(User user);

    Collection<User> getUsers();

    User updateUser(User user);

    //void deleteUsers();

    User getUserById(Long id);

    Collection<User> getUserFriends(Long userId);

    Collection<User> getCommonFriends(Long user1Id, Long user2Id);
}
