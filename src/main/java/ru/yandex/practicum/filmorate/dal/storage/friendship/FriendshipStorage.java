package ru.yandex.practicum.filmorate.dal.storage.friendship;

public interface FriendshipStorage {
    void addFriend(Long userId, Long friendId);

    void deleteFriend(Long userId, Long friendId);
}
