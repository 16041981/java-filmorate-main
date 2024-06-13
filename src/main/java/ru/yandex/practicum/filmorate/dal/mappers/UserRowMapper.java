package ru.yandex.practicum.filmorate.dal.mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new  User();
        user.setId(rs.getLong("id"));
        user.setName(rs.getString("name"));
        user.setLogin(rs.getString("login"));
        user.setBirthday(Objects.requireNonNull(rs.getDate("birthday")).toLocalDate());
        user.setEmail(rs.getString("email"));
        return user;
    }
}
