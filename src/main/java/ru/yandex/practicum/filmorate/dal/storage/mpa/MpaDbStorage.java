package ru.yandex.practicum.filmorate.dal.storage.mpa;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dal.extractor.MpaExtractor;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MpaDbStorage implements MpaStorage {

    private final NamedParameterJdbcOperations jdbc;

    @Override
    public List<Mpa> getRatings() {
        String sql = "SELECT RATING_ID AS ID, NAME FROM RATINGS;";
        return jdbc.query(sql, new MpaRowMapper());
    }

    @Override
    public Optional<Mpa> getRatingById(int ratingId) {
        String sql = "SELECT RATING_ID, NAME FROM RATINGS WHERE RATING_ID = :rating_id";
        Map<String, Object> param = Map.of("rating_id", ratingId);
        return Optional.ofNullable(jdbc.query(sql, param, new MpaExtractor()));
    }
}