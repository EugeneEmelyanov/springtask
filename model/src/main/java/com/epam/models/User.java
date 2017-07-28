package com.epam.models;

import com.epam.security.Roles;
import com.epam.serializers.LocalDateDesirializer;
import com.epam.serializers.LocalDateSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class User {

    private long id;
    private String email;
    private String name;
    @JsonDeserialize(using = LocalDateDesirializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate birthday;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String userRoles = Roles.REGISTERED_USER.getAuthority();

    private User(Builder builder) {
        setId(builder.id);
        setEmail(builder.email);
        setName(builder.name);
        setBirthday(builder.birthday);
        setPassword(builder.password);
        setUserRoles(builder.userRoles);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserRoles() {
        if (StringUtils.isEmpty(userRoles)) {
            return Roles.REGISTERED_USER.getAuthority();
        }
        return userRoles;
    }

    @JsonIgnore
    public List<GrantedAuthority> getGrantedAuthorities() {

        return Arrays.asList(userRoles.split(","))
                .stream()
                .map(authority ->new UserGrantedAuthority(authority))
                .collect(Collectors.toList());


    }

    private static class UserGrantedAuthority implements GrantedAuthority {

        private final String principal;

        public UserGrantedAuthority(final String principal) {
            this.principal = principal;
        }

        @Override
        public String getAuthority() {
            return principal;
        }
    }

    public void setUserRoles(String userRoles) {
        this.userRoles = userRoles;
    }

    public User() {

    }

    public User(long id, String email, String name, LocalDate birthday) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.birthday = birthday;
    }

    public User(String email, String name, LocalDate birthday) {
        this(-1, email, name, birthday);
    }

    public User withId(long id) {
        return new User(id, email, name, birthday);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        User user = (User) o;

        if (id != user.id)
            return false;
        if (email != null ? !email.equals(user.email) : user.email != null)
            return false;
        if (name != null ? !name.equals(user.name) : user.name != null)
            return false;
        return birthday != null ? birthday.equals(user.birthday) : user.birthday == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", birthday=" + birthday +
                '}';
    }

    public static final class Builder {
        private long id;
        private String email;
        private String name;
        private LocalDate birthday;
        private String password;
        private String userRoles;

        private Builder() {
        }

        public Builder withId(long val) {
            id = val;
            return this;
        }

        public Builder withEmail(String val) {
            email = val;
            return this;
        }

        public Builder withName(String val) {
            name = val;
            return this;
        }

        public Builder withBirthday(LocalDate val) {
            birthday = val;
            return this;
        }

        public Builder withPassword(String val) {
            password = val;
            return this;
        }

        public Builder withUserRoles(String val) {
            userRoles = val;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
