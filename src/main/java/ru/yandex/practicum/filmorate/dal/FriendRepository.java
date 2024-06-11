package ru.yandex.practicum.filmorate.dal;

public interface FriendRepository {

    void add(long id, long friendId);

    void delete(long id, long friendId);
}
