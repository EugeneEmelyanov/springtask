package beans.controllers.advices;

import beans.controllers.*;
import beans.services.SpringAdvancedUserDetailsService;
import beans.services.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * Created by yauhen_yemelyanau on 7/17/17.
 */
@ControllerAdvice(assignableTypes = {UserController.class, AuditoriumController.class, BookingController.class, EventController.class, TicketController.class})
public class LoggedUserAdvice {

    @Autowired
    @Qualifier("userAccountServiceImpl")
    private UserAccountService userAccountService;

    @ModelAttribute()
    public void addCurrentUser(Model model) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof String) {
            //anonymous user (not logged in)
            model.addAttribute("anonymous", "User is not logged in!");
        } else {
            SpringAdvancedUserDetailsService.UserPrincipal principal = (SpringAdvancedUserDetailsService.UserPrincipal) authentication.getPrincipal();
            model.addAttribute("currentUser", principal.getUser());
            model.addAttribute("currentAccount", userAccountService.createOrGet(principal.getUser().getId()));
        }
    }
}
