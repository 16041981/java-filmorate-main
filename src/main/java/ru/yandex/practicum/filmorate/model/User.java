package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Set;


@Data
@AllArgsConstructor
@Builder
public class User {
    @PositiveOrZero
    private Long id;
    @Email
    @NotEmpty
    private String email;
    @NotNull
    @NotBlank
    @Pattern(regexp = "\\S+")
    private String login;
    @NotNull
    @NotBlank
    private String name;
    @PastOrPresent
    @NotNull
    private LocalDate birthday;
    private Set<Long> friends;

    public User() {

    }

    public void addFriend(Long id) {
        friends.add(id);
    }

    public void removeFriend(Long id) {
        friends.remove(id);
    }
}