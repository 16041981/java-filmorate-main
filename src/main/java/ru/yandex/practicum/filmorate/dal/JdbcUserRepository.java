package ru.yandex.practicum.filmorate.dal;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dal.mappers.UserRowMapper;
import ru.yandex.practicum.filmorate.mapper.UserMapper;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcUserRepository implements UserRepository{
    private final JdbcOperations jdbc;
    //private final NamedParameterJdbcOperations jdbc;
    private final UserRowMapper mapper;

    @Override
    public User createUser(User user) {
        final String sql = "insert into users (name, login, birthday, email) values (?, ?, ?, ?)";
        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbc.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    sql,
                    new String[]{"id"}
            );
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLogin());
            preparedStatement.setObject(3, user.getBirthday());
            preparedStatement.setString(4, user.getEmail());
            return preparedStatement;
        }, generatedKeyHolder);
        long userId = Objects.requireNonNull(generatedKeyHolder.getKey()).intValue();
        user.setId(userId);
        return user;
    }

    @Override
    public List<User> getUsers() {
        String query = "SELECT * FROM users";
        return jdbc.query(query, mapper);
    }

    @Override
    public User updateUser(User user) {
        final String sql = "update users set name = ?, login = ?, birthday = ?, email = ? where id = ?";
        jdbc.update(
                sql,
                user.getName(), user.getLogin(), user.getBirthday(), user.getEmail(), user.getId()
        );
        return  user;
    }

    @Override
    public void deleteUsers() {

    }

    @Override
    public Optional<User> getUserById(long id) {
        String query = "select * from users";
        try {
            return Optional.ofNullable(jdbc.queryForObject(query.concat(" where id = ?"), mapper, id));
        }
        catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<User> getUserFriends(Integer userId) {
        final String sql = "select * from users where id in (select f.friend_id from users u join friendships f " +
                "on u.id = f.user_id where u.id = ?)";

        return jdbc.query(sql, mapper, userId);
    }

    @Override
    public List<User> getCommonFriends(Integer user1Id, Integer user2Id) {
        final String sql = "select * from users where id in (select friend_id from users u join friendships f on " +
                "u.id = f.user_id where u.id = ?) and id in (select friend_id from users u join friendships f on " +
                "u.id = f.user_id where u.id = ?)";

        return jdbc.query(sql, mapper, user1Id, user2Id);
    }

    @Override
    public boolean deleteUserById(Integer id) {
        final String sql = "delete from users where id = ?";
        int status = jdbc.update(sql, id);
        return status != 0;
    }
}
