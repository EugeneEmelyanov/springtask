package beans.controllers;

import beans.services.SpringAdvancedUserDetailsService;
import beans.services.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by yauhen_yemelyanau on 7/17/17.
 */
@Controller
@RequestMapping("/account")
public class UserAccountController {

    @Autowired
    @Qualifier("userAccountServiceImpl")
    private UserAccountService userAccountService;


    @RequestMapping("/add")
    public void addMoney(@RequestParam("amount") double amount, HttpServletResponse response) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be less than 0");
        }
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (user instanceof SpringAdvancedUserDetailsService.UserPrincipal) {
            final long userId = ((SpringAdvancedUserDetailsService.UserPrincipal) user).getUser().getId();
            userAccountService.depositMoney(userId, amount);
            response.setStatus(HttpServletResponse.SC_ACCEPTED);

        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
