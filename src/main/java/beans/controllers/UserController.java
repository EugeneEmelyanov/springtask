package beans.controllers;

import beans.models.User;
import beans.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;

/**
 * Created by Yauhen_Yemelyanau on 7/5/2017.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    public static final String USER_LIST_VIEW = "Users";
    public static final String USER_VIEW = "User";

    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;

    @RequestMapping("/list")
    public String showUsers(@RequestParam(required = false) String name,
                            @ModelAttribute("model") ModelMap model) {

        model.addAttribute("users", userService.getUsersByName(name));

        return USER_LIST_VIEW;
    }

    @RequestMapping("/{userId}")
    public String showUser(@PathVariable() Long userId,
                           @ModelAttribute("model") ModelMap model) {

        User user = userService.getById(userId);

        if (Objects.isNull(user)) {
            throw new IllegalArgumentException(String.format("cannot find user for id = %d", userId));
        }

        model.addAttribute("user", user);

        return USER_VIEW;
    }

    @RequestMapping(params = {"email"})
    public String showUser(@RequestParam String email,
                           @ModelAttribute("model") ModelMap model) {
        User user = userService.getUserByEmail(email);

        if (Objects.isNull(user)) {
            throw new IllegalArgumentException(String.format("cannot find user for email = %s", email));
        }

        model.addAttribute("user", user);

        return USER_VIEW;
    }
}
