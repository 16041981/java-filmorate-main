package ru.yandex.practicum.filmorate.dal.storage.mpa;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dal.mappers.MpaRowMapper;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.Collection;

@Component
@Qualifier("MpaDbStorage")
public class MpaDbStorage implements MpaStorage {

    private final JdbcTemplate jdbcTemplate;
    private final MpaRowMapper mapper;
    private final String mpasSql = "select * from mpas";

    public MpaDbStorage(JdbcTemplate jdbcTemplate, MpaRowMapper mapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.mapper = mapper;
    }

    @Override
    public Mpa getMpaById(Long mpaId) {
        try {
            return jdbcTemplate.queryForObject(mpasSql.concat(" where id = ?"), mapper, mpaId);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Collection<Mpa> getAllMpa() {
        return jdbcTemplate.query(mpasSql, mapper);
    }
}