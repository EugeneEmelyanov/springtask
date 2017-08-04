package com.epam.ws.model;

import com.epam.ws.model.adapters.LocalDateAdapter;
import com.epam.security.Roles;
import com.epam.serializers.LocalDateDesirializer;
import com.epam.serializers.LocalDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.util.StringUtils;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@XmlType(name = "UserDTO", propOrder = {
        "id",
        "name",
        "email",
        "birthday",
        "password",
        "userRoles"
}, namespace = "http://epam.com/springcourse")
public class UserDTO {

    private long id;
    private String email;
    private String name;

    @JsonDeserialize(using = LocalDateDesirializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate birthday;
    private String password;
    private String userRoles = Roles.REGISTERED_USER.getAuthority();

    @XmlElement(required = true)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    @XmlElement(required = true)
    public String getUserRoles() {
        if (StringUtils.isEmpty(userRoles)) {
            return Roles.REGISTERED_USER.getAuthority();
        }
        return userRoles;
    }


    public void setUserRoles(String userRoles) {
        this.userRoles = userRoles;
    }

    public UserDTO() {

    }

    public UserDTO(long id, String email, String name, LocalDate birthday) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.birthday = birthday;
    }

    public UserDTO(String email, String name, LocalDate birthday) {
        this(-1, email, name, birthday);
    }

    public com.epam.models.User withId(long id) {
        return new com.epam.models.User(id, email, name, birthday);
    }

    @XmlElement(required = true)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @XmlElement(required = true)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @XmlElement(required = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    @XmlSchemaType(name="dateTime")
    @XmlElement(required = true)
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

        UserDTO userDTO = (UserDTO) o;

        if (id != userDTO.id)
            return false;
        if (email != null ? !email.equals(userDTO.email) : userDTO.email != null)
            return false;
        if (name != null ? !name.equals(userDTO.name) : userDTO.name != null)
            return false;
        return birthday != null ? birthday.equals(userDTO.birthday) : userDTO.birthday == null;

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
        return "UserDTO{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", birthday=" + birthday +
                '}';
    }
}
