package beans.controllers.advices;

import beans.controllers.UserAccountController;
import beans.controllers.UserController;
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


    @ModelAttribute("model")
    public void addCurrentUser(ModelMap model) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("currentUser", authentication.getPrincipal());
        System.out.println(authentication);
    }
}
