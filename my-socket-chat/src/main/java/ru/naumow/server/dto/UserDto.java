package ru.naumow.server.dto;

import ru.naumow.server.models.User;

public class UserDto implements Dto {
    private Long id;
    private String login;
    private String role;

    // TODO: вместо конструктора Builder
    private UserDto(Long id, String login, String role) {
        this.id = id;
        this.login = login;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", login='" + login + '\'' +
                '}';
    }

    public static UserDto from(User user) {
        return new UserDto(user.getId(), user.getLogin(), user.getRole());
    }

}
