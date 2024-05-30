MERGE INTO film_mpa (mpa_id, mpa_rating)
VALUES (1, 'G');
MERGE INTO film_mpa (mpa_id, mpa_rating)
VALUES (2, 'PG');
MERGE INTO film_mpa (mpa_id, mpa_rating)
VALUES (3, 'PG-13');
MERGE INTO film_mpa (mpa_id, mpa_rating)
VALUES (4, 'R');
MERGE INTO film_mpa (mpa_id, mpa_rating)
VALUES (5, 'NC-17');

MERGE INTO genre (genre_type) VALUES ('Комедия');
MERGE INTO genre (genre_type) VALUES ('Драма');
MERGE INTO genre (genre_type) VALUES ('Мультфильм');
MERGE INTO genre (genre_type) VALUES ('Триллер');
MERGE INTO genre (genre_type) VALUES ('Документальный');
MERGE INTO genre (genre_type) VALUES ('Боевик');
