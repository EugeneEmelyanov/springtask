package beans.controllers.soap;

import beans.services.UserService;
import com.epam.ws.model.UserDTO;
import com.epam.ws.model.converters.EventConverter;
import com.epam.ws.model.EventDTO;
import com.epam.ws.model.converters.UserConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;
import java.util.stream.Collectors;

@Endpoint
public class UsersEndpoint {
    private static final String NAMESPACE_URI = "http://epam.com/springcourse/";

    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getUsersRequest")
    @ResponsePayload()
    public List<UserDTO> getUsers() {
        return userService.getUsersByName("").stream()
                .map(user -> UserConverter.convertFromModelToWS(user))
                .collect(Collectors.toList());
    }
}
