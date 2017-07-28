package com.epam.restclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
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

    @Autowired
    private RestTemplate restTemplate;

    public List<User> getAllUsers() {
        User[] users = new User[0];
        try {
            users = restTemplate.getForObject(getAlluserURI(), User[].class);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return Arrays.asList(users);
    }

    protected URI getAlluserURI() throws URISyntaxException {
        return URIBuilder.get()
                .withHost("localhost")
                .withPort(8080)
                .withQuery("/api/v1/users")
                .build();


    }
}
