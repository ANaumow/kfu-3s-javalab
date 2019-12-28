package ru.naumow.models;

public class User {
    private Long id;
    private String login;
    private String password;
    private String role;

    private User(Builder builder) {
        this.id = builder.id;
        this.login = builder.login;
        this.password = builder.password;
        this.role = builder.role;
    }

    public String getLogin() {
        return login;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public static class Builder {
        private Long id;
        private String login;
        private String password;
        private String role;

        public Long getId() {
            return id;
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public String getLogin() {
            return login;
        }

        public Builder login(String login) {
            this.login = login;
            return this;
        }

        public String getPassword() {
            return password;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public String getRole() {
            return role;
        }

        public Builder role(String role) {
            this.role = role;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

}
