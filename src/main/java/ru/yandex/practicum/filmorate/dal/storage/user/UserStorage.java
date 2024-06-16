package ru.yandex.practicum.filmorate.dal.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserStorage {

    User createUser(User user);

    Collection<User> getUsers();

    User updateUser(User user);


    User getUserById(Integer id);

    Collection<User> getUserFriends(Integer userId);

    Collection<User> getCommonFriends(Integer user1Id, Integer user2Id);
}
