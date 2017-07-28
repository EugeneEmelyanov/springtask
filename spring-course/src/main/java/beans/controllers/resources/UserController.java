package beans.controllers.resources;

import com.epam.models.User;
import beans.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

/**
 * Created by yauhen_yemelyanau on 7/26/17.
 */
@RestController("userResourceController")
@RequestMapping("api/v1/users")
public class UserController {

    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET, produces = {"application/json", "application/pdf"})
    public List<User> getAllUsers() {
        return userService.getUsersByName(null);
    }

    @RequestMapping(method = RequestMethod.POST, produces = {"application/json", "application/pdf"})
    public User createUser(@RequestBody User user) {
        Objects.requireNonNull(user, "User cannot be blank");
        Objects.requireNonNull(user.getBirthday(), "Birthday cannot be blank");
        Objects.requireNonNull(user.getEmail(), "Email cannot be blank");
        Objects.requireNonNull(user.getName(), "User name cannot be blank");
        Objects.requireNonNull(user.getPassword(), "Password cannot be blank");

        return userService.register(user);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET, produces = {"application/json", "application/pdf"})
    public User getUserById(@PathVariable("id") long userId) {
        return userService.getById(userId);
    }

    @RequestMapping(path="/{id}", method = RequestMethod.DELETE, produces = {"application/json", "application/pdf"})
    public void deleteUserById(@PathVariable("id") long userId, HttpServletResponse response) {
        userService.remove(userService.getById(userId));
        response.setStatus(HttpStatus.OK.value());
    }
}
