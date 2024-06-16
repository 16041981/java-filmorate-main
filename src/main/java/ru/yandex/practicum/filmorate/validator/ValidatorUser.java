package ru.yandex.practicum.filmorate.validator;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashSet;

@Slf4j
public class ValidatorUser {

    private Integer id;

    public ValidatorUser() {
        this.id = 0;
    }

    public void validate(User user) {
        if (user.getId() == null || user.getId() <= 0) {
            user.setId(++id);
            log.info("Некорректно указан id.");
        }
        if (user.getName() == null) {
            user.setName(user.getLogin());
            log.info("Имя для отображения может быть пустым — в таком случае будет использован логин.");
        }
        if (user.getFriends() == null) {
            user.setFriends(new HashSet<>());
        }
    }
}
