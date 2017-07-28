package beans.controllers;

import com.epam.models.User;
import com.epam.models.UserAccount;
import beans.services.UserAccountService;
import beans.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    @Autowired
    @Qualifier("userAccountServiceImpl")
    private UserAccountService userAccountService;

    @RequestMapping("/list")
    public String showUsers(@RequestParam(required = false) String name,
                            ModelMap model) {
        final List<User> usersByName = userService.getUsersByName(name);

        final List<Long> userIds = usersByName.stream()
                .map(user -> user.getId())
                .collect(Collectors.toList());

        model.addAttribute("userAccounts", userAccountService.createOrGet(userIds));

        return USER_LIST_VIEW;
    }

    @RequestMapping("/{userId}")
    public String showUser(@PathVariable() Long userId,
                           @RequestParam(required = false, name = "show_error", defaultValue = "false") boolean showError,
                           ModelMap model) {

        User user = userService.getById(userId);

        if (Objects.isNull(user)) {
            throw new IllegalArgumentException(String.format("cannot find user for id = %d", userId));
        }

        UserAccount userAccount = userAccountService.createOrGet(userId);

        model.addAttribute("userAccount", userAccount);

        if (showError) {
            model.addAttribute("msg", "Please, refill your account, you have not enough money to buy ticket.");
        }

        return USER_VIEW;
    }

    @RequestMapping(params = {"email"})
    public String showUser(@RequestParam String email,
                           ModelMap model) {
        User user = userService.getUserByEmail(email);

        if (Objects.isNull(user)) {
            throw new IllegalArgumentException(String.format("cannot find user for email = %s", email));
        }

        model.addAttribute("userAccount", userAccountService.createOrGet(user.getId()));

        return USER_VIEW;
    }
}
