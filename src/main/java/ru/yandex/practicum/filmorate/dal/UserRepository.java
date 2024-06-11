package ru.yandex.practicum.filmorate.dal;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    User createUser(User user);

    List<User> getUsers();

    User updateUser(User user);

    void deleteUsers();

    Optional<User> getUserById(long id);

    List<User> getUserFriends(Integer userId);

    List<User> getCommonFriends(Integer user1Id, Integer user2Id);

    boolean deleteUserById(Integer id);
}
