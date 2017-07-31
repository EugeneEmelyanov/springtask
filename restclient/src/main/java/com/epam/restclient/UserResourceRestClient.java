package com.epam.restclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.epam.models.*;

/**
 * Created by yauhen_yemelyanau on 7/27/17.
 */
@Component
public class UserResourceRestClient {


    private RestTemplate restTemplate = new RestTemplate();

    public List<User> getAllUsers() {
        User[] users = new User[0];
        try {
            users = restTemplate.getForObject(getAlluserURI(), User[].class);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return Arrays.asList(users);
    }

    public User createUser(User user) {
        User createdUser = null;
        try {
            createdUser = restTemplate.postForObject(getAlluserURI().toString(), user, User.class);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return createdUser;
    }

    public User getById(final long userId) {

        User user = null;
        try {
            user = restTemplate.getForObject(addUserId(userId), User.class);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return user;
    }

    public boolean removeUser(final long userId) {
        try {
            restTemplate.delete(addUserId(userId));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return true;
    }

    protected URI getAlluserURI() throws URISyntaxException {
        return getBaseUriBuilder()
                .build();
    }

    protected URI addUserId(final long userId) throws URISyntaxException {
        return getBaseUriBuilder()
                .withQuery("/api/v1/users/"+userId+".json")
                .build();
    }

    protected URIBuilder getBaseUriBuilder() {
        return URIBuilder.get()
                .withHost("localhost")
                .withPort(8080)
                .withQuery("/api/v1/users.json");
    }

}
