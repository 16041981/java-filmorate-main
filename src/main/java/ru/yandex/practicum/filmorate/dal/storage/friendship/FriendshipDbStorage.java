package ru.yandex.practicum.filmorate.dal.storage.friendship;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@Qualifier("FriendshipDbStorage")
public class FriendshipDbStorage implements FriendshipStorage {

    private final JdbcTemplate jdbc;

    public FriendshipDbStorage(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public void addFriend(Integer userId, Integer friendId) {
        final String sql = "insert into friendships (user_id, friend_id) values (?, ?)";

        jdbc.update(sql, userId, friendId);
    }

    @Override
    public void deleteFriend(Integer userId, Integer friendId) {
        final String sql = "delete from friendships where user_id = ? and friend_id = ?";

        jdbc.update(sql, userId, friendId);
    }
}