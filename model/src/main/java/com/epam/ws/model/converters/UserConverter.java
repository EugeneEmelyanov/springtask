package com.epam.ws.model.converters;

import com.epam.models.User;
import com.epam.ws.model.UserDTO;

import java.util.Objects;

public class UserConverter {

    public static UserDTO convertFromModelToWS(User sourceUser) {
        Objects.requireNonNull(sourceUser);

        UserDTO targetUserDTO = new UserDTO();
        targetUserDTO.setBirthday(sourceUser.getBirthday());
        targetUserDTO.setEmail(sourceUser.getEmail());
        targetUserDTO.setId(sourceUser.getId());
        targetUserDTO.setName(sourceUser.getName());
        targetUserDTO.setPassword(sourceUser.getPassword());
        targetUserDTO.setUserRoles(sourceUser.getUserRoles());

        return targetUserDTO;
    }
}
