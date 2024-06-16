package ru.yandex.practicum.filmorate.dal.storage.friendship;

public interface FriendshipStorage {
    void addFriend(Integer userId, Integer friendId);

    void deleteFriend(Integer userId, Integer friendId);
}
