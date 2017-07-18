package beans.controllers.advices;

import beans.controllers.UserAccountController;
import beans.controllers.UserController;
import beans.services.SpringAdvancedUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
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
@ControllerAdvice(assignableTypes = {UserController.class})
public class LoggedUserAdvice {


    @ModelAttribute()
    public void addCurrentUser(Model model) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof String) {
            //anonymous user (not logged in)
            model.addAttribute("anonymous", "User is not logged in!");
        } else {
            SpringAdvancedUserDetailsService.UserPrincipal principal = (SpringAdvancedUserDetailsService.UserPrincipal) authentication.getPrincipal();
            model.addAttribute("currentUser", principal.getUser());
        }
    }
}
